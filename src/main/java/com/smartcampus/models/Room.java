/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.models;

/**
 *
 * @author oshic
 */

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String id;
    private String name;
    private String building;
    private int capacity;
    private List<String> sensorIds;
    
    public Room(){
        this.sensorIds = new ArrayList<>();
    }
    
    public String getId(){
        return id;
    }
    
    public void setId(String id){
        this.id = id;
    }
    
     public String getName(){
         return name;
     }
     
     public void setName(String name){
         this.name = name;
     }
     
     public String getBuilding(){
         return building;
     }
     
     public void setBuilding(String building){
         this.building = building;
     }
     
     public int getCapacity(){
         return capacity;
     }
     
     public void setCapacity(int floor){
         this.capacity = floor;
     }
     
     public List<String> getSensorIds() {
         return sensorIds;
     }
     
     public void setSensorIds(List<String> sensorIds) {
         this.sensorIds = sensorIds;
     }
}

