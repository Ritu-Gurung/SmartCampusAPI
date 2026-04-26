/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus;

import java.util.logging.Logger;

/**
 * Main class - For documentation only.
 * With TomEE, this class is NOT used to start the server.
 * The application runs automatically when deployed to TomEE.
 * 
 * @author oshic
 */
public class Main {
    
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args) {
        LOGGER.info("==================================================");
        LOGGER.info("Smart Campus API - Deployment Instructions");
        LOGGER.info("==================================================");
        LOGGER.info("");
        LOGGER.info("This application is designed to run on TomEE server.");
        LOGGER.info("");
        LOGGER.info("To run this application:");
        LOGGER.info("1. Make sure TomEE is installed and configured in NetBeans");
        LOGGER.info("2. Right-click on the SmartCampusAPI project");
        LOGGER.info("3. Select 'Run'");
        LOGGER.info("4. NetBeans will deploy to TomEE automatically");
        LOGGER.info("");
        LOGGER.info("Once deployed, access the API at:");
        LOGGER.info("http://localhost:8080/SmartCampusAPI/api/v1/");
        LOGGER.info("");
        LOGGER.info("To stop: Right-click project → Undeploy or Stop Server");
        LOGGER.info("==================================================");
    }
}