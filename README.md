# Smart Campus Sensor and Room Management API

JAX-RS RESTful API for managing rooms and sensors.

# Build Instructions
- Open in NetBeans
- Clean and Build
- Deploy to TomEE

# API Endpoints
- GET /api/v1/ - Discovery
- GET /api/v1/rooms - List rooms
- POST /api/v1/rooms - Create room
- GET /api/v1/rooms/{id} - Get room
- DELETE /api/v1/rooms/{id} - Delete room
- GET /api/v1/sensors - List sensors
- POST /api/v1/sensors - Create sensor
- GET /api/v1/sensors/{id}/readings - Get readings
- POST /api/v1/sensors/{id}/readings - Add reading
