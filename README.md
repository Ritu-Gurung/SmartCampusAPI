# Smart Campus Sensor and Room Management API

## Overview

This is a REST API for managing rooms and sensors in a university campus. 
Built with JAX-RS (Java RESTful Web Services) and Grizzly embedded server.

## What the API does:
- Create, view and delete rooms
- Add sensors to specific rooms
- Record sensor readings (like CO2 levels)
- Filter sensors by type (e.g., show only CO2 sensors)
- Automatically update sensor values when readings are added

## What happens when things go wrong:
- Cannot delete a room if it has sensors -> Returns 409 Conflict
- Cannot add sensor to a room that does not exist -> Returns 422 Unprocessable Entity
- Cannot add reading to a sensor in maintenance mode -> Returns 403 Forbidden
- Any unexpected error -> Returns 500 Internal Server Error
- All requests and response are logged automatically

## Technology Stack
- Java 11
- JAX-RS (Jakarta RESTful Web Services)
- Grizzly (embedded HTTP server)
- In-memory storage (ConcurrentHashMap) - no database required

## How to Build and Run the Project
Before you start, make sure you have:
- Java 11 or higher installed
- Maven installed (or use NetBeans which includes Maven)
- Git installed (to clone the repository)

## Step 1: Get the code
 
**Option A: Clone from GitHub (Recommended)**
```bash
git clone https://github.com/Ritu-Gurung/SmartCampusAPI.git
cd SmartCampusAPI```

**Option B: Download as ZIP**
1. Go to https://github.com/Ritu-Gurung/SmartCampusAPI
2. Click the green "Code" button
3. Select "Download ZIP"
4. Extract the ZIP file

## Step 2: Open the project in NetBeans
1. Launch NetBeans IDE
2. Click File -> Open Project
3. Navigate to the SmartCampusAPI folder
4. Click Open Project

## Step 3: Build the project
In NetBeans:
    Right-click on SmartCampusAPI -> Clean and Build

Or using terminal:
    mvn clean compile

## Step 4: Run the server

In NetBeans:

1. Open Main.java (located in com.smartcampus package)
2. Right-click inside the file -> Run File

Or using terminal:
mvn exec:java -Dexec.mainClass="com.smartcampus.Main"

## Step 5: Verify the server is running
You should see this in the terminal output:
==================================================
GRIZZLY - Smart Campus API is running!
Test: http://localhost:8080/api/v1/
==================================================

## Step 6: Test the API
Open your browser or use curl(see the commands below).

API Endpoints
Method	URL                                 What it does
GET	/api/v1/                            Get API information (discovery)
GET	/api/v1/rooms                       List all rooms
POST	/api/v1/rooms                       Create a new room
GET	/api/v1/rooms/{id}                  Get details of one room
DELETE	/api/v1/rooms/{id}                  Delete a room (only if empty)
GET	/api/v1/sensors                     List all sensors
GET	/api/v1/sensors?type=CO2            List sensors filtered by type
POST	/api/v1/sensors                     Create a new sensor
GET	/api/v1/sensors/{id}/readings       Get reading history of a sensor
POST	/api/v1/sensors/{id}/readings       Add a new reading to a sensor

## Sample curl Commands
Run these in a new terminal window while the server is running.

1. Discovery - Get API Information
curl -X GET http://localhost:8080/api/v1/

Expected response:
{
  "version": "1.0.0",
  "api_name": "Smart Campus API",
  "admin_contact": "smartcampus@university.edu",
  "collections": {
    "rooms": "/api/v1/rooms",
    "sensors": "/api/v1/sensors"
  }
}

2. Create a New Room
curl -X POST http://localhost:8080/api/v1/rooms \
  -H "Content-Type: application/json" \
  -d '{"name":"Computer Lab 101","capacity":30}'

Expected response: HTTP 201 Created with room details including a unique ID

3. Get All Rooms
curl -X GET http://localhost:8080/api/v1/rooms

Expected response: List of all rooms (starts with initial sample data)

4. Create a Sensor (Replace {room-id} with actual room ID)
curl -X POST http://localhost:8080/api/v1/sensors \
  -H "Content-Type: application/json" \
  -d '{"type":"CO2","roomId":"room-001","status":"ACTIVE"}'

Expected response: HTTP 201 Created with sensor details including a unique ID

5. Add a Reading to a Sensor (Replace {sensor-id} with actual sensor ID)
curl -X POST http://localhost:8080/api/v1/sensors/sensor-001/readings \
  -H "Content-Type: application/json" \
  -d '{"value":450.0}'

Expected response: HTTP 201 Created with reading details

6. Filter Sensors by Type (show only CO2 sensors)
curl -X GET "http://localhost:8080/api/v1/sensors?type=CO2"

Expected response: Only CO2 sensors in the list

7. Get Reading History of a Sensor (Replace {sensor-id})
curl -X GET http://localhost:8080/api/v1/sensors/sensor-001/readings

Expected response: List of all readings for that sensor

## Error Response Examples

### Attempting to Delete a Room That Has Sensors (409 Conflict)
curl -X DELETE http://localhost:8080/api/v1/rooms/room-001

Response:
{
  "error": "Room Not Empty",
  "message": "Room 'room-001' has 1 active sensor(s). Remove all sensors before deleting this room.",
  "status": 409
}

### Creating a Sensor with Invalid Room ID (422 Unprocessable Entity)
curl -X POST http://localhost:8080/api/v1/sensors \
  -H "Content-Type: application/json" \
  -d '{"type":"CO2","roomId":"invalid-room","status":"ACTIVE"}'

Response: 
{
  "error": "Invalid Reference",
  "message": "Room with ID 'invalid-room' does not exist. Please create the room first or provide a valid roomId.",
  "status": 422
}

## How to Stop the Server
In the terminal where the server is running, press:
Ctrl + C

## Report Answers

### Part 1 - Service Architecture and Setup

**Q1: JAX-RS Resource Class Lifecycle**

JAX-RS creates a new copy of your resource class for each request that comes in. It does not reuse the same copy for all requests (not a singleton).

This matters for our DataStore because many requests can come at the same time. To prevent data loss or mixing up data, we used ConcurrentHashMap. It handles multiple threads safely. 
If we reused the same object for all requests, we would have to add extra code to prevent problems.

**Q2: HATEOAS Benefits**

HATEOAS means sending links inside your API responses (like a website sends links to other pages).

Benefits for developers:
- No need to remember or hardcode URLs - just follow the links
- If the API changes, clients can still find their way by following new links
- The API explains itself - one response tells you where to go next
- Less time reading static documentation

### Part 2 - Room Management

**Q1: Returning IDs vs Full Room Objects**

Returning only IDs:
- Less data sent over network (saves bandwidth)
- Client needs to make extra requests to get details (slower)

Returning full room objects:
- More data sent (uses more bandwidth)
- Client gets everything at once (faster, fewer requests)

Best practice: For small lists, return full objects. For large lists (thousands of rooms), return IDs with pagination.

**Q2: DELETE Idempotence**

YES, DELETE is idempotent. This means sending the same request multiple times has the same final result.

Example:
- First DELETE request: Room is deleted (success)
- Second DELETE request: Room is already gone, so returns 404 Not Found
- Final state after both requests: Room is deleted (same result)

The server doesn't behave differently no matter how many times you send the request.

### Part 3 - Sensors and Filtering

**Q1: @Consumes Mismatch**

@Consumes tells the server "I only accept JSON data".

If a client sends XML or plain text instead:
- JAX-RS automatically returns error HTTP 415 Unsupported Media Type
- Your business code never runs
- Client knows immediately what went wrong

**Q2: QueryParam vs PathParam**

QueryParam (our approach): /sensors?type=CO2
PathParam (alternative): /sensors/type/CO2

Why QueryParam is better for filtering:
- Optional - you don't have to include it
- Order doesn't matter - ?type=CO2&status=ACTIVE is same as ?status=ACTIVE&type=CO2
- Easy to add more filters - just add &status=OFFLINE
- PathParam would need a new URL for every filter combination (endless URLs!)

### Part 4 - Deep Nesting with Sub-Resources

**Q1: Sub-Resource Locator Pattern Benefits**

Instead of putting ALL code in one giant file, we split it into smaller, focused classes.

Benefits:
- Cleaner code: Each file does one thing (Room stuff in RoomResource, Reading stuff in SensorReadingResource)
- Reusable: The same sub-resource can be used in different places
- Easier to fix bugs: You know exactly which file to look in
- Easier to test: Small classes are easier to test than giant ones
- Easier teamwork: Multiple people can work on different files without conflicts

### Part 5 - Advanced Error Handling

**Q1: HTTP 422 vs 404**

404 Not Found: The entire web address is wrong. Example: /api/v1/wrongpage
422 Unprocessable Entity: The address is correct, but the data you sent doesn't make sense.

Example:
- You send valid JSON format (good syntax)
- But the roomId you provided doesn't exist (bad meaning)

422 tells the client: "Your JSON format is correct, but the room ID is invalid." This is more helpful than 404.

**Q2: Risks of Exposing Stack Traces**

Never show stack traces to users since hackers can learn:

- File paths: Where files are stored on your server
- Line numbers: Exactly where your code failed
- Library versions: Known vulnerabilities they can exploit
- Method names: How your code is structured
- Database info: How to inject malicious code

Always return a generic error message like "Something went wrong" and log the details internally.

**Q3: JAX-RS Filters Advantage**

Without filters: You write logger.info() in every method (10+ times). To change logging, update 10+ files.

With filters: You write logging code once in a filter class. It automatically applies to all requests.

Benefits:
- Write once, use everywhere
- Consistent logs (same format for all requests)
- Easy to update (change one file, all endpoints benefit)
- Your resource classes stay clean and focused on business logic