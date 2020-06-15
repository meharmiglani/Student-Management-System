package com.flipkart.client;

import com.flipkart.service.ProfessorServiceOperation;
import org.apache.log4j.Logger;
import java.util.Scanner;

public class ProfessorClient {
    private final static Logger logger = Logger.getLogger(ProfessorClient.class);
    private final static Scanner scn = new Scanner(System.in);
    private final static ProfessorServiceOperation professorServiceOperation = new ProfessorServiceOperation();

    public static void professorHandler(int professorId){

        while (true){
            displayProfessorMenu();
            System.out.println("Enter your choice");
            int choice = Integer.parseInt(scn.nextLine());

            switch (choice){
                case 1:
                    viewAllStudentList(professorId);
                    break;
                case 2:
                    viewStudentListByCourseId(professorId);
                    break;
                case 3:
                    viewCoursesTaughtByProfessor(professorId);
                    break;
                case 4:
                    viewCoursesToTeach();
                    break;
                case 5:
                    selectCourses(professorId);
                    break;
                case 6:
                    updateMarks(professorId);
                    break;
                default:
                    break;
            }

            if(choice > 6){
                break;
            }
        }
    }

    public static void displayProfessorMenu(){
        logger.info("1 - View student List");
        logger.info("2 - View Student List by course ID");
        logger.info("3 - View subjects taught");
        logger.info("4 - View available courses to teach");
        logger.info("5 - Select a course to teach");
        logger.info("6 - Upload marks");
        logger.info("7 - Logout");
    }

    public static void viewAllStudentList(int professorrId){
        professorServiceOperation.viewStudentList(professorrId);
    }

    public static void viewStudentListByCourseId(int professorId){
        logger.info("Enter the courseID");
        int courseId = Integer.parseInt(scn.nextLine());
        professorServiceOperation.viewStudentListByCourseId(professorId, courseId);
    }

    public static void viewCoursesTaughtByProfessor(int professorId){
        professorServiceOperation.viewCoursesTaught(professorId);
    }

    public static void viewCoursesToTeach(){
        professorServiceOperation.viewCoursesToTeach();
    }

    public static void selectCourses(int professorId){
        logger.info("Enter the courseID you want to teach");
        int courseId = Integer.parseInt(scn.nextLine());
        professorServiceOperation.selectCourseToTeach(professorId, courseId);
    }

    public static void updateMarks(int professorId){
        logger.info("Enter student ID");
        int studentId = Integer.parseInt(scn.nextLine());
//        if(!professorServiceOperation.checkForRegistration(studentId)){
//            logger.info("Student isn't registered yet");
//        }
        logger.info("Enter course ID");
        int courseId = Integer.parseInt(scn.nextLine());
        logger.info("Enter marks");
        int marks = Integer.parseInt(scn.nextLine());
        professorServiceOperation.updateStudentMarks(studentId, courseId, marks);
    }
}
