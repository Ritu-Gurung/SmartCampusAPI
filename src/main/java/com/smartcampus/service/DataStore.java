/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.service;

/**
 *
 * @author oshic
 */

import com.smartcampus.models.Room;
import com.smartcampus.models.Sensor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataStore {
    private static DataStore instance;
    private Map<String, Room> rooms;
    private Map<String, Sensor> sensors;
    
    private DataStore(){
        rooms = new ConcurrentHashMap<>();
        sensors = new ConcurrentHashMap<>();
    }
    
    public static synchronized DataStore getInstance(){
        if (instance == null){
            instance = new DataStore();
        }
        return instance;
    }
    
    public Map<String, Room> getRooms() {return rooms; }
    public Map<String, Sensor> getSensors() {return sensors; }
}
