/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.mappers;

/**
 *
 * @author oshic
 */

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;
import java.util.logging.Logger;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable>{
    
    private static final Logger logger = Logger.getLogger(GlobalExceptionMapper.class.getName());
    
    @Override
    public Response toResponse(Throwable exception){
        logger.severe("Unhandled exception: " + exception.getMessage());
        exception.printStackTrace();
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of(
        "error", "Internal Server Error",
                "message", "An unexpected error occured. Please try again later.",
                "status",500)).type("application/json").build();
    }
}
