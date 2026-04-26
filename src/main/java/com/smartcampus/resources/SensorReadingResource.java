/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resources;

/**
 *
 * @author oshic
 */
import com.smartcampus.exceptions.SensorUnavailableException;
import com.smartcampus.models.Reading;
import com.smartcampus.models.Sensor;
import com.smartcampus.service.DataStore;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;


public class SensorReadingResource {
    private String sensorId;
    private DataStore store = DataStore.getInstance();
    
    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReadings() {
        Sensor sensor = store.getSensors().get(sensorId);
        if(sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Sensor nor found"))
                    .build();
        }
        return Response.ok(sensor.getReadingHistory()).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReading(Reading reading){
        Sensor sensor = store.getSensors().get(sensorId);
        
        if(sensor==null){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "sensor not found")).build();
        }
        
        if ("MAINTENANCE".equals(sensor.getStatus())){
            throw new SensorUnavailableException(
            "Sensor '" + sensorId + "' is currently in MAINTENANCE mode. " + 
                    "Please change status to ACTIVE before adding readings.");
    }
        
        String readingId = UUID.randomUUID().toString();
        reading.setId(readingId);
        reading.setTimestamp(Instant.now().toString());
        reading.setSensorId(sensorId);
        
        sensor.getReadingHistory().add(reading);
        sensor.setCurrentValue(reading.getValue());
        
        return Response.status(Response.Status.CREATED).entity(reading).build();
    }
}
