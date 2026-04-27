/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.mappers;

/**
 *
 * @author oshic
 */

import com.smartcampus.exceptions.ResourceNotFoundException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;

@Provider
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException>{
    
    @Override
    public Response toResponse(ResourceNotFoundException exception){
        return Response.status(422).entity(Map.of(
        "error","Invalid Reference",
                "message",exception.getMessage(),
                "status",422)).type("application/json").build();
    }
    
}
