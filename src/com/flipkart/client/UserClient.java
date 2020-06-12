package com.flipkart.client;

import com.flipkart.exception.UserNotFoundException;
import com.flipkart.service.*;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class UserClient {
    static Scanner scn = new Scanner(System.in);

    private final static Logger logger = Logger.getLogger(UserClient.class);
    private final static UserOperation userOperation = new UserOperation();
    private final static LocalDate localDate = LocalDate.now();
    private final static LocalTime localTime = LocalTime.now();

    public static void main(String[] args){
        logger.info("Enter your username");
        String username = scn.nextLine();
        logger.info("Enter your password");
        String password = scn.nextLine();

        int userId = -1;
        String role = "";
        try{
            userId = checkIdentity(username, password);
            role = checkRole(username, password);
        }catch (UserNotFoundException e){
            logger.error(e.getUser());
        }

        if(userId != -1){
            logger.info("******** Welcome to Student Management System *********");
            logger.info("***************** " + role.toUpperCase() + " SECTION ***************");
            logger.info("You have successfully logged in as " + username + " on " + localDate + " " + localTime.getHour() + ":" + localTime.getMinute() + " " + localDate.getDayOfWeek());
        }else{
            logger.error("Login Failed");
            return;
        }

        switch(role){
            case "student":
                StudentClient.studentHandler(userId);
                break;
            case "admin":
                AdminClient.adminHandler(userId);
                break;
            case "professor":
                ProfessorClient.professorHandler(userId);
                break;
        }
    }

    public static int checkIdentity(String username, String password) throws UserNotFoundException {
        return userOperation.checkIdentity(username, password);
    }

    public static String checkRole(String username, String password){
        return userOperation.getRole(username, password);
    }
}