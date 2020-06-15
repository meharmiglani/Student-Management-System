package com.flipkart.client;

import com.flipkart.exception.UserNotFoundException;
import com.flipkart.service.*;
import org.apache.log4j.Logger;
import java.util.Scanner;

public class StudentClient {
    private final static Logger logger = Logger.getLogger(StudentClient.class);
    private final static UserOperation userOperation = new UserOperation();
    private final static Scanner scn = new Scanner(System.in);
    private final static StudentServiceOperation studentServiceOperation = new StudentServiceOperation();

    public static void studentHandler(int studentId){
        try{
            String studentName = getStudentName(studentId);
            if(studentId != -1){
                while(true){
                    displayStudentMenu();
                    System.out.println("Enter your choice");
                    int choice = Integer.parseInt(scn.nextLine());

                    switch (choice){
                        case 1:
                            viewCourseCatalog();
                            break;
                        case 2:
                            addCourse(studentId, studentName);
                            break;
                        case 3:
                            deleteCourse(studentId);
                            break;
                        case 4:
                            viewMarksByCourse(studentId);
                            break;
                        case 5:
                            viewReportCard(studentId);
                            break;
                        case 6:
                            viewRegisteredCourses(studentId);
                            break;
                        case 7:
                            submitRegistration(studentId);
                            break;
                        default:
                            break;
                    }
                    if(choice > 7){
                        logger.info("Logging Out....");
                        break;
                    }
                }
            }else{
                logger.error("Check your username or password");
            }
        }catch (UserNotFoundException e){
            logger.error(e.getUser());
        }
    }

    public static void displayStudentMenu(){
        logger.info("1 - View Course List");
        logger.info("2 - Add a course");
        logger.info("3 - Delete a course");
        logger.info("4 - View marks by course");
        logger.info("5 - View report card");
        logger.info("6 - View your courses");
        logger.info("7 - Submit Registration");
        logger.info("8 - Logout");
    }

    public static String getStudentName(int studentId) throws UserNotFoundException{
        return userOperation.getStudentName(studentId);
    }

    public static void viewCourseCatalog(){
        studentServiceOperation.viewCourseCatalog();
    }

    public static void addCourse(int studentId, String studentName){

        if(studentServiceOperation.checkForRegistration(studentId)){
            logger.info("Cannot add a course after final registration");
            return;
        }

        if(!studentServiceOperation.canAddCourse(studentId)){
            return;
        }

        logger.info("Enter a course ID you want to register for");
        int courseId = scn.nextInt();
        scn.nextLine();

        if(studentServiceOperation.addCourse(studentId, studentName, courseId)) {
            logger.info("Course successfully added!");
        }
    }

    public static void deleteCourse(int studentId){

        if(studentServiceOperation.checkForRegistration(studentId)){
            logger.info("Cannot delete a course after final registration");
            return;
        }

        logger.info("Enter a course ID you want to delete");
        int courseId = Integer.parseInt(scn.nextLine());

        if(studentServiceOperation.deleteCourse(studentId, courseId)){
            logger.info("Course deleted successfully");
        }else{
            //Throw Exception
            logger.error("Could not delete course");
        }
    }

    public static void viewRegisteredCourses(int studentId){
        studentServiceOperation.viewRegisteredCourses(studentId);
    }

    public static void viewMarksByCourse(int studentId){
        studentServiceOperation.viewMarksByCourse(studentId);
    }

    public static void viewReportCard(int studentId){
        studentServiceOperation.viewReportCard(studentId);
    }

    public static void submitRegistration(int studentId){
        studentServiceOperation.submitRegistration(studentId);
    }

}
