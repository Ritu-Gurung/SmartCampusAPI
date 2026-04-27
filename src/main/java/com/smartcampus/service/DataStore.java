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
        addInitialData();
    }
    
    public static synchronized DataStore getInstance(){
        if (instance == null){
            instance = new DataStore();
        }
        return instance;
    }
    
    private void addInitialData(){
        Room room1 = new Room();
        room1.setId("room-001");
        room1.setName("Computer Lab 101");
        room1.setBuilding("Engineering Building");
        room1.setCapacity(30);
        rooms.put(room1.getId(), room1);
        
        Sensor sensor1 = new Sensor();
        sensor1.setId("sensor-001");
        sensor1.setType("CO2");
        sensor1.setRoomId("room-001");
        sensor1.setCurrentValue(420.0);
        sensors.put(sensor1.getId(), sensor1);
        
        room1.getSensorIds().add(sensor1.getId());
    }
    
    public Map<String, Room> getRooms() {return rooms; }
    public Map<String, Sensor> getSensors() {return sensors; }
}
