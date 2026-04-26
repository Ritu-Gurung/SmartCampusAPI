/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resources;

/**
 *
 * @author oshic
 */

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Map;

@Path("/")
public class DiscoveryResource {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApiInfo(){
        Map<String, Object> apiInfo = Map.of(
                "version","1.0.0",
                "api_name", "Smart Campus Sensor and Room Management API",
                "admin_contact", "smartcampus@university.edu",
                "collections",Map.of(
                     "rooms", "/api/v1/rooms",
                        "sensors", "/api/v1/sensors"
                )
        );
        
        return Response.ok(apiInfo).build();
    }
    
}
