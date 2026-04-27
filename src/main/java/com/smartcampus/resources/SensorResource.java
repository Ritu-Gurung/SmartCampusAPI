/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resources;

/**
 *
 * @author oshic
 */
import com.smartcampus.exceptions.ResourceNotFoundException;
import com.smartcampus.models.Room;
import com.smartcampus.models.Sensor;
import com.smartcampus.service.DataStore;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.NotFoundException;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/api/v1/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {
    
    private DataStore store = DataStore.getInstance();
    
    @POST
    public Response createSensor(Sensor sensor) {
        Room room = store.getRooms().get(sensor.getRoomId());
        if(room==null){
            throw new ResourceNotFoundException(
                    "Room with ID '" + sensor.getRoomId() + "' does not exist. " + 
                            "Please create the room first or provide a valid roomId."
            );
        }
        
        String sensorId = UUID.randomUUID().toString();
        sensor.setId(sensorId);
        store.getSensors().put(sensorId, sensor);
        room.getSensorIds().add(sensorId);
        
        return Response.status(Response.Status.CREATED).entity(sensor).build();
    }
    
    @GET
    public Response getSensors(@QueryParam("type") String type) {
        Collection<Sensor> sensors = store.getSensors().values();
        
        if(type != null && !type.isEmpty()) {
            sensors = sensors.stream()
                    .filter(s -> s.getType().equalsIgnoreCase(type))
                    .collect(Collectors.toList());
        }
        return Response.ok(sensors).build();
    }
    
    @Path("/{sensorId}/readings")
    public SensorReadingResource getSensorReadings(@PathParam("sensorId") String sensorId){
        Sensor sensor = store.getSensors().get(sensorId);
        if(sensor == null){
            throw new NotFoundException("Sensor with ID '" + sensorId + "' not found");
        }
        return new SensorReadingResource(sensorId);
        
    }      
}
