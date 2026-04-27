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
import java.util.HashMap;
import java.util.Map;

@Path("/api/v1")
public class DiscoveryResource {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApiInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("version", "1.0.0");
        response.put("api_name", "Smart Campus API");
        response.put("admin_contact", "smartcampus@university.edu");
        
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("rooms", "/api/v1/rooms");
        endpoints.put("sensors", "/api/v1/sensors");
        response.put("collections", endpoints);
        
        return Response.ok(response).build();
    }

}
