/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import com.smartcampus.resources.DiscoveryResource;
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
        // Manually registering the resource
        final ResourceConfig config = new ResourceConfig();
        config.register(DiscoveryResource.class);  
        
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);
        
        LOGGER.info("==================================================");
        LOGGER.info("GRIZZLY - Smart Campus API is running!");
        LOGGER.info("Test: http://localhost:8080/api/v1/");
        LOGGER.info("==================================================");
        
        Thread.currentThread().join();
    }
}