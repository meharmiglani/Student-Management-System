package com.flipkart.client;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.flipkart.exception.NoCourseFoundException;
import com.flipkart.service.ProfessorServiceOperation;

public class ProfessorClient {
    private final static Logger logger = Logger.getLogger(ProfessorClient.class);
    private final static Scanner scn = new Scanner(System.in);
    private final static ProfessorServiceOperation professorServiceOperation = new ProfessorServiceOperation();

    //Handles all professor client operations
    public static void professorHandler(int professorId){

        while (true){
            displayProfessorMenu();
            System.out.println("Enter your choice");
            int choice = Integer.parseInt(scn.nextLine());

            switch (choice){
                case 1:
                    viewAllStudentList(professorId); //Displays all students
                    break;
                case 2:
                    viewStudentListByCourseId(professorId); //Displays students in a course
                    break;
                case 3:
                    viewCoursesTaughtByProfessor(professorId); //View all courses taught by a professor
                    break;
                case 4:
                    viewCoursesToTeach(); //Available courses
                    break;
                case 5:
                    selectCourses(professorId); //Choose a course to teach
                    break;
                case 6:
                    updateMarks(professorId); //Update marks of a student
                    break;
                default:
                    break;
            }

            if(choice > 6){
            	logger.info("Logging Out...");
                break;
            }
        }
    }

    //Displays professor menu
    public static void displayProfessorMenu(){
        logger.info("1 - View student List");
        logger.info("2 - View Student List by course ID");
        logger.info("3 - View subjects taught");
        logger.info("4 - View available courses to teach");
        logger.info("5 - Select a course to teach");
        logger.info("6 - Upload marks");
        logger.info("7 - Logout");
    }
    
    //View all students that a professor teaches in all courses
    public static void viewAllStudentList(int professorrId){
        professorServiceOperation.viewStudentList(professorrId);
    }

    //View all students in a particular course taught by the professor
    public static void viewStudentListByCourseId(int professorId){
        logger.info("Enter the courseID");
        int courseId = Integer.parseInt(scn.nextLine());
        professorServiceOperation.viewStudentListByCourseId(professorId, courseId);
    }

    //View all courses that a professor teaches
    public static void viewCoursesTaughtByProfessor(int professorId){
        professorServiceOperation.viewCoursesTaught(professorId);
    }
    
    //Displays a list of available courses in the university
    public static void viewCoursesToTeach(){
        professorServiceOperation.viewCoursesToTeach();
    }
    
    //Helps professor choose courses to teach
    public static void selectCourses(int professorId){
        logger.info("Enter the courseID you want to teach");
        int courseId = Integer.parseInt(scn.nextLine());
        try{
        	professorServiceOperation.selectCourseToTeach(professorId, courseId);
        }catch(NoCourseFoundException e){
        	logger.info(e.getMessage());
        }
    }
    
    //Update marks of a student in a particular course
    public static void updateMarks(int professorId){
        logger.info("Enter student ID");
        int studentId = Integer.parseInt(scn.nextLine());
        if(!professorServiceOperation.checkForRegistration(studentId)){
            logger.info("Student isn't registered yet");
            return;
        }
        
        logger.info("Enter course ID");
        int courseId = Integer.parseInt(scn.nextLine());
        logger.info("Enter marks");
        int marks = Integer.parseInt(scn.nextLine());
        professorServiceOperation.updateStudentMarks(studentId, courseId, marks);
    }
}
