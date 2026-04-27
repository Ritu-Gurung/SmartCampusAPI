/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.models;

/**
 *
 * @author oshic
 */
public class Reading {
    private String id;
    private long timestamp;
    private double value;
    private String sensorId;
    
    public Reading(){}
    
    public String getId(){
        return id;
    }
    
    public void setId(String id){
        this.id=id;
    }
    
    public long getTimestamp(){
        return timestamp;
    }
    
    public void setTimestamp(long timestamp){
        this.timestamp = timestamp;
    }
    
    public double getValue(){
        return value;
    }
    
    public void setValue(double value){
        this.value = value;
    }
    
    public String getSensorId(){
        return sensorId;
    }
    
    public void setSensorId(String sensorId){
        this.sensorId = sensorId;
    }
}
