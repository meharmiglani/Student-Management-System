package com.flipkart.client;

import com.flipkart.exception.UserNotFoundException;
import com.flipkart.model.Admin;
import com.flipkart.model.Student;
import com.flipkart.model.User;
import com.flipkart.service.*;
import org.apache.log4j.Logger;

import java.util.Scanner;

public class UserClient {
    static Scanner scn = new Scanner(System.in);

    private static UserOperation userOperation = new UserOperation();
    private static StudentTaskOperation studentTasks = new StudentTaskOperation();
    private static ProfessorTaskOperation professorTasks = new ProfessorTaskOperation();
    private static CourseListOperation courseListOperation = new CourseListOperation();
    private static RegisterCourseOperation registerCourseOperation = new RegisterCourseOperation();
    private static ViewStudentListOperation viewStudentListOperation = new ViewStudentListOperation();
    private static Logger logger = Logger.getLogger(UserClient.class);
    private static ViewRegisteredCoursesOperation registeredCoursesOperation = new ViewRegisteredCoursesOperation();
    public static void main(String[] args){
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
            int studentId = checkIdentity(username, password);

            if(studentId != -1){
                logger.info("You have successfully logged in!");
            }

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
//                        case 4:
//                            viewReportCard(student.getStudentId());
//                            break;
                        case 5:
                            viewRegisteredCourses(studentId);
                            break;
//                        case 6:
//                            payFee(student.getStudentId());
//                            break;
                        default:
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
            logger.error(e.getUser());
        }
    }

    public static void adminHandler(){
        logger.info("Enter your username");
        String username = scn.nextLine();
        logger.info("Enter your password");
        String password = scn.nextLine();


    }

    public static void professorHandler(){
        logger.info("Enter your username");
        String username = scn.nextLine();
        logger.info("Enter your password");
        String password = scn.nextLine();

        try{
            int professorId = checkIdentity(username, password);

            if(professorId != -1){
                logger.info("You have successfully logged in!");
            }else{
                logger.error("Couldn't log in");
                return;
            }

            while (true){
                displayProfessorMenu();
                System.out.println("Enter your choice");
                int choice = Integer.parseInt(scn.nextLine());

                switch (choice){
                    case 1:
                        viewStudentList(professorId);
                        break;
//                    case 2:
//                        updateMarks(professorId);
//                        break;
                    default:
                        break;
                }

                if(choice == 3){
                    break;
                }
            }
        }catch (UserNotFoundException e){
            logger.error(e.getUser());
        }
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

    public static int checkIdentity(String username, String password) throws UserNotFoundException {
        return userOperation.checkIdentity(username, password);
    }

    public static String getStudentName(int studentId) throws UserNotFoundException{
        return userOperation.getStudentName(studentId);
    }

    public static void viewCourseCatalog(){
        courseListOperation.viewCourseCatalog();
    }

    public static void addCourse(int studentId, String studentName){
        logger.info("Enter a course ID you want to register for");
        int courseId = scn.nextInt();
        scn.nextLine();
        logger.info("Is this an alternate course?");
        String alternate = scn.nextLine();
        if(registerCourseOperation.addCourse(studentId, studentName, courseId, alternate)){
            logger.info("Course successfully added!");
        }else{
            //Throw Exception
            logger.error("Could not add course");
        }
    }

    public static void deleteCourse(int studentId){
        logger.info("Enter a course ID you want to delete");
        int courseId = Integer.parseInt(scn.nextLine());

        if(registerCourseOperation.deleteCourse(studentId, courseId)){
            logger.info("Course deleted successfully");
        }else{
            //Throw Exception
            logger.error("Could not delete course");
        }
    }

    public static void viewStudentList(int professorrId){
        viewStudentListOperation.viewStudentList(professorrId);
    }

    public static void viewRegisteredCourses(int studentId){
        registeredCoursesOperation.viewRegisteredCourses(studentId);
    }
}