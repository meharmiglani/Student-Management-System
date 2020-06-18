package com.flipkart.client;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.flipkart.exception.CannotAddMoreCourseException;
import com.flipkart.exception.CourseLimitExceededException;
import com.flipkart.exception.UserNotFoundException;
import com.flipkart.service.StudentServiceOperation;
import com.flipkart.service.UserOperation;

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
                            viewCourseCatalog(); //Displays all courses for which professors have been allotted
                            break;
                        case 2:
                            addCourse(studentId, studentName); //Add a course to student's list
                            break;
                        case 3:
                            deleteCourse(studentId); //Delete a course from student's list
                            break;
                        case 4:
                            viewMarksByCourse(studentId); //View marks for registered students
                            break;
                        case 5:
                            viewReportCard(studentId); //View report card for registered students
                            break;
                        case 6:
                            viewRegisteredCourses(studentId); //View courses chosen for registration
                            break;
                        case 7:
                            submitRegistration(studentId); //Register after choosing 4 courses and pay fee
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
                logger.error("Check your username or password"); //Authentication failed
            }
        }catch (UserNotFoundException e){
            logger.error(e.getUser());
        }
    }
    
    //Display student Menu
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
    
    //Fetches student's name
    public static String getStudentName(int studentId) throws UserNotFoundException{
        return userOperation.getStudentName(studentId);
    }
    
    //Fetches details of the course catalog
    public static void viewCourseCatalog(){
        logger.info("Enter catalog ID:");
        logger.info(String.format("%15s %15s %15s %15s","1 - Fresher", "2 - Sophomore", "3 - Junior", "4 - Senior"));
        int catalogId = Integer.parseInt(scn.nextLine());
        studentServiceOperation.viewCourseCatalog(catalogId);
    }
    
    //To add a course in the student's courses
    public static void addCourse(int studentId, String studentName){

        	if(studentServiceOperation.checkForRegistration(studentId)){ //Checks if the student is already registered
                logger.info("Cannot add a course after final registration"); //Doesn't allow adding a course if already registered
                return;
            }

            if(!studentServiceOperation.canAddCourse(studentId)){ //Checks the number of courses the student is already enrolled in
                return;
            }

            logger.info("Enter a course ID you want to register for"); //Asks for course ID
            int courseId = scn.nextInt();
            scn.nextLine();
            
            try{ //check if the course has exceeded its limit
	            if(studentServiceOperation.addCourse(studentId, studentName, courseId)) { //Addition operation
	                logger.info("Course successfully added!");
	            }
            
            }catch(CannotAddMoreCourseException e){
            	logger.info(e.receiveMessage());
            }catch(CourseLimitExceededException e){ 
            	logger.info(e.getCourse());
            }
    }
    
    //To delete a course from the student's list
    public static void deleteCourse(int studentId){

        if(studentServiceOperation.checkForRegistration(studentId)){ //Checks if the student is already registered
            logger.info("Cannot delete a course after final registration"); //Doesn't allow deleting a course if already registered
            return;
        }

        logger.info("Enter a course ID you want to delete"); //Asks for course ID to delete
        int courseId = Integer.parseInt(scn.nextLine());

        if(studentServiceOperation.deleteCourse(studentId, courseId)){ //Delete operation
            logger.info("Course deleted successfully");
        }else{
            //Throw Exception
            logger.error("Could not delete course");
        }
    }

    //Fetches a list of courses the student has currently selected
    public static void viewRegisteredCourses(int studentId){
        studentServiceOperation.viewRegisteredCourses(studentId);
    }
    
    //Displays a list of marks for all registered courses
    public static void viewMarksByCourse(int studentId){
        studentServiceOperation.viewMarksByCourse(studentId); //Available to the student only when he has registered and paid the fee
    }
    
    //Shows the final report card along with aggregate % and pass/fail status
    public static void viewReportCard(int studentId){ 
        studentServiceOperation.viewReportCard(studentId); //Available to the student only when he has registered and paid the fee
    }
    
    //To submit the final list of chosen courses
    public static void submitRegistration(int studentId){ //A student can register only when he chooses 4 courses
        studentServiceOperation.submitRegistration(studentId); 
    }

}
