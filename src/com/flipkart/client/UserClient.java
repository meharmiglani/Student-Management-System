package com.flipkart.client;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.flipkart.exception.UserNotFoundException;
import com.flipkart.service.UserOperation;

public class UserClient {
    static Scanner scn = new Scanner(System.in);

    private final static Logger logger = Logger.getLogger(UserClient.class);
    private final static UserOperation userOperation = new UserOperation();
    private final static LocalDate localDate = LocalDate.now();
    private final static LocalTime localTime = LocalTime.now();

    public static void main(String[] args){
        logger.info("Enter your username"); //Log in system - asks for the username and password
        String username = scn.nextLine();
        logger.info("Enter your password");
        String password = scn.nextLine();

        int userId = -1;
        String roleType = "";
        int role;
        try{
            userId = checkIdentity(username, password); //Checks the identity of the user for logging in
            role = checkRole(username, password);  //Fetches the role
            if(role == 1){
                roleType = "admin";
            }else if(role == 2){
                roleType = "professor";
            }else{
                roleType = "student";
            }
        }catch (UserNotFoundException e){ 
            logger.error(e.getUser());
        }
        
        //Authorization passed
        if(userId != -1){
            logger.info("******** Welcome to Student Management System *********");
            logger.info("***************** " + roleType.toUpperCase() + " SECTION ***************");
            logger.info("You have successfully logged in as " + username + " on " + localDate + " " + localTime.getHour() + ":" + localTime.getMinute() + " " + localDate.getDayOfWeek());
        }else{
            logger.error("Login Failed"); //Authorization failed
            return;
        }

        switch(roleType){ //Goes to corresponding clients depending upon role
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

    public static int checkRole(String username, String password){
        return userOperation.getRole(username, password);
    }
}