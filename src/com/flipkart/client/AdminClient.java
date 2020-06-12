package com.flipkart.client;

import org.apache.log4j.Logger;
import java.util.Scanner;

public class AdminClient {
    private final static Logger logger = Logger.getLogger(StudentClient.class);
    private final static Scanner scn = new Scanner(System.in);

    public static void adminHandler(int adminId){

    }

    public static void displayAdminMenu(){
        logger.info("1 - Create a user");
        logger.info("2 - Edit a user");
        logger.info("3 - Delete a user");
        logger.info("4 - Add course to catalog");
        logger.info("5 - Delete course from the catalog");
        logger.info("7 - Logout");
    }
}
