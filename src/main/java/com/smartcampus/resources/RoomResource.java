/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resources;

/**
 *
 * @author oshic
 */
import com.smartcampus.exceptions.RoomNotEmptyException;
import com.smartcampus.models.Room;
import com.smartcampus.service.DataStore;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;
import java.util.UUID;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)      
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {
    
    private DataStore store = DataStore.getInstance();
    
    @GET
    public Response getAllRooms(){
        return Response.ok(store.getRooms().values()).build();
    }
    
    @POST
    public Response createRoom(Room room){
        String roomId = UUID.randomUUID().toString();
        room.setId(roomId);
        store.getRooms().put(roomId, room);
        return Response.status(Response.Status.CREATED).entity(room).build();
    }
    
    @GET
    @Path("/{roomId}")
    public Response getRoom(@PathParam("roomId") String roomId) {
        Room room = store.getRooms().get(roomId);
        if(room==null){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Room not found with ID: " + roomId))
                    .build();
        }
        return Response.ok(room).build();
    }
    
    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String roomId){
        Room room = store.getRooms().get(roomId);
        
        if(room==null){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Room not found with ID: " + roomId))
                    .build();
        }
        
        if(!room.getSensorIds().isEmpty()){
            throw new RoomNotEmptyException(
            "Room '" + roomId + "' has " + room.getSensorIds().size() +
                    "active sensor(s). Remove all sensors before deleting this room.");
        }
        
        store.getRooms().remove(roomId);
        return Response.noContent().build();
    }
}
