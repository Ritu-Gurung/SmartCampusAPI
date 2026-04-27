/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import com.smartcampus.resources.DiscoveryResource;
import com.smartcampus.resources.RoomResource;
import com.smartcampus.resources.SensorResource;
import com.smartcampus.resources.SensorReadingResource;
import com.smartcampus.filters.LoggingFilter;
import com.smartcampus.mappers.*;
import java.net.URI;
import java.util.logging.Logger;

/**
 * 
 * @author oshic
 */
public class Main {
    
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    public static final String BASE_URI = "http://localhost:8080/";
    
    public static void main(String[] args) throws Exception {
        // Register ALL resources
        final ResourceConfig config = new ResourceConfig()
            .register(DiscoveryResource.class)
            .register(RoomResource.class)
            .register(SensorResource.class)
            .register(SensorReadingResource.class)
            .register(LoggingFilter.class)
            .register(RoomNotEmptyExceptionMapper.class)
            .register(ResourceNotFoundExceptionMapper.class)
            .register(SensorUnavailableExceptionMapper.class)
            .register(GlobalExceptionMapper.class);
        
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);
        
        LOGGER.info("==================================================");
        LOGGER.info("GRIZZLY - Smart Campus API is running!");
        LOGGER.info("Hello: http://localhost:8080/hello");
        LOGGER.info("Discovery: http://localhost:8080/api/v1/");
        LOGGER.info("Rooms: http://localhost:8080/api/v1/rooms");
        LOGGER.info("==================================================");
        
        Thread.currentThread().join();
    }
}