package com.flipkart.client;

import com.flipkart.exception.UserNotFoundException;
import com.flipkart.model.Admin;
import com.flipkart.model.Student;
import com.flipkart.service.ProfessorCRUD;
import com.flipkart.service.ProfessorTasks;
import com.flipkart.service.StudentCRUD;
import com.flipkart.service.StudentTasks;
import com.sun.imageio.plugins.gif.GIFImageReaderSpi;
import org.apache.log4j.Logger;

import java.util.Scanner;

public class UserClient {
    static Scanner scn = new Scanner(System.in);
    ProfessorCRUD professorCRUD = new ProfessorCRUD();
    StudentCRUD studentCRUD = new StudentCRUD();
    StudentTasks studentTasks = new StudentTasks();
    ProfessorTasks professorTasks = new ProfessorTasks();

    private static Logger logger = Logger.getLogger(UserClient.class);
    public static void main(String[] args) {
        logger.info("Enter your role");
        String role = scn.nextLine();

        switch(role){
            case "student":
                studentHandler();
                break;
            case "admin":
                adminHandler();
                break;
            case "professor":
                professorHandler();
                break;
        }
    }

    public static void studentHandler(){

        logger.info("Enter your username");
        String username = scn.nextLine();
        logger.info("Enter your password");
        String password = scn.nextLine();

        try{
            Student student = checkIdentityStudent(username, password);
            if(student != null){
                logger.info("You have successfully logged in!");
            }
            if(student != null){
                while(true){
                    displayStudentMenu();
                    System.out.println("Enter your choice");
                    int choice = Integer.parseInt(scn.nextLine());

                    switch (choice){
                        case 1:
                            viewCourseList();
                        case 2:
                            addCourse(student.getStudentId());
                            break;
                        case 3:
                            deleteCourse(student.getStudentId());
                            break;
                        case 4:
                            viewGrades(student.getStudentId());
                            break;
                        case 5:
                            viewCourses(student.getStudentId());
                            break;
                        case 6:
                            payFee(student.getStudentId());
                            break;
                        case 7:
                            logout();
                            break;
                    }

                    if(choice == 7){
                        logger.info("Logging Out....");
                        break;
                    }
                }
            }else{
                logger.error("Check your username or password");
            }
        }catch (UserNotFoundException e){
            logger.error(e.getUser() + " does not exist");
        }
    }

    public static void adminHandler(){
        logger.info("Enter your username");
        String username = scn.nextLine();
        logger.info("Enter your password");
        String password = scn.nextLine();

        Admin admin = checkIdentityAdmin(username, password);
        if(admin != null){

        }
    }

    public static void professorHandler(){

    }

    public static void displayStudentMenu(){
        logger.info("1 - View Course List");
        logger.info("2 - Add a course");
        logger.info("3 - Delete a course");
        logger.info("4 - View grades");
        logger.info("5 - View your courses");
        logger.info("6 - Pay fee");
        logger.info("7 - Logout");
    }

    public static void displayAdminMenu(){
        logger.info("1 - Create a student");
        logger.info("2 - Edit a student");
        logger.info("3 - Delete a student");
        logger.info("4 - Create a Professor");
        logger.info("5 - Edit a Professor");
        logger.info("6 - Delete a Professor");
        logger.info("7 - Logout");
    }

    public static void displayProfessorMenu(){
        logger.info("1 - View student list");
        logger.info("2 - Upload marks");
        logger.info("3 - Logout");
    }

    public static Student checkIdentityStudent(String username, String password){

    }
}
