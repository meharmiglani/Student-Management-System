package com.flipkart.client;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.flipkart.model.Course;
import com.flipkart.model.Professor;
import com.flipkart.model.Student;
import com.flipkart.model.User;
import com.flipkart.service.AdminServiceOperation;

public class AdminClient {
    private final static Logger logger = Logger.getLogger(AdminClient.class);
    private final static Scanner scn = new Scanner(System.in);
    private final static AdminServiceOperation adminServiceOperation = new AdminServiceOperation();

    public static void adminHandler(int adminId){
        if(adminId != -1){
            while(true){
                displayAdminMenu();
                System.out.println("Enter your choice");
                int choice;
                try{
                	choice = Integer.parseInt(scn.nextLine());
                }catch(InputMismatchException e){ 
                	logger.info(e.getMessage());
                	return;
                }

                switch (choice){
                    case 1:
                        createUser();
                        break;
                    case 2:
                        deleteUser(); 
                        break;
                    case 3:
                        editUser(); 
                        break;
                    case 4:
                        createCourse(); //Add a course to the catalog
                        break;
                    case 5:
                        deleteCourse(); //Delete a course from the catalog
                        break;
                    case 6:
                        viewAllStudents(); //View registered as well as unregistered students
                        break;
                    case 7:
                        viewAllProfessors(); //View all professors in the university
                        break;
                    case 8:
                        viewAllCourses(); //View all courses in the university
                        break;
                    case 9:
                        viewPaymentDetails(); //Payment details of the registered students
                        break;
                    default:
                        break;
                }
                if(choice > 9){
                    logger.info("Logging Out....");
                    break;
                }
            }
        }else{
            logger.error("Check your username or password"); //Authentication check
        }
    }

    //Displays the admin menu
    public static void displayAdminMenu(){
        logger.info("1 - Create a user");
        logger.info("2 - Delete a user");
        logger.info("3 - Edit a user");
        logger.info("4 - Add a course to the catalog");
        logger.info("5 - Delete a course from the catalog");
        logger.info("6 - View all students");
        logger.info("7 - View all professors");
        logger.info("8 - View all courses");
        logger.info("9 - View payment details of registered students");
        logger.info("10 - Logout");
    }
    
    //Creates a new user in the university
    public static void createUser(){
        User user = new User();
        logger.info("Enter userId");
        int userId = Integer.parseInt(scn.nextLine());
        user.setId(userId);
        logger.info("Enter username");
        user.setUsername(scn.nextLine());
        logger.info("Enter password");
        user.setPassword(scn.nextLine());
        logger.info("Enter role");
        String role = scn.nextLine();
        
        switch (role){ //Check role
            case "student":
            	user.setRoleId(3);
            	if(!adminServiceOperation.createUser(user)){
            		return;
            	}
                createStudent(userId); //Create a new student if role == student
                break;
            case "professor":
            	user.setRoleId(2);
            	if(!adminServiceOperation.createUser(user)){
            		return;
            	}
                createProfessor(userId); //Create a new professor if role == professor
                break;
            case "admin":
            	user.setRoleId(1);
            	if(!adminServiceOperation.createUser(user)){
            		return;
            	}
                createAdmin(userId); //Else create an admin
                break;
        }
    }

    //Create a student using studentId/userId
    public static void createStudent(int studentId){
        Student student = getStudentDetails(studentId);
        adminServiceOperation.createStudent(student);
    }

    //Create a professor using professorId/userId
    public static void createProfessor(int professorId){
        Professor professor = getProfessorDetails(professorId);
        adminServiceOperation.createProfessor(professor);
    }

    
    public static void createAdmin(int adminId){
    	logger.info("Admin created");
    }
    
    //Create a new course for the catalog
    public static void createCourse(){
        Course course = new Course();
        logger.info("Enter courseID");
        course.setCourseId(Integer.parseInt(scn.nextLine())); //Enter course ID
        logger.info("Enter course name");
        course.setCourseName(scn.nextLine()); //Enter course name
        logger.info("Enter credits");
        course.setCredits(Integer.parseInt(scn.nextLine())); //Enter credits
        logger.info("Enter course fee");
        course.setFee(Integer.parseInt(scn.nextLine())); //Enter course fee
        logger.info("Enter catalog ID:");
        logger.info(String.format("%15s %15s %15s %15s","1 - Fresher", "2 - Sophomore", "3 - Junior", "4 - Senior")); //Catalog Type
        course.setCatalogId(Integer.parseInt(scn.nextLine()));
        adminServiceOperation.createCourse(course);
    }
    
    //View all students
    public static void viewAllStudents(){
        adminServiceOperation.viewAllStudents();
    }
    
    //View all professors
    public static void viewAllProfessors(){
        adminServiceOperation.viewAllProfessors();
    }
    
    //View payment details of the registered students
    public static void viewPaymentDetails(){
        adminServiceOperation.viewPaymentDetails();
    }
    
    //Delete a course from the catalog
    public static void deleteCourse(){
        logger.info("Enter a course ID to delete");
        int courseId = Integer.parseInt(scn.nextLine());
        adminServiceOperation.deleteCourse(courseId);
    }
    
    //Delete a user from the university
    public static void deleteUser(){
        logger.info("Enter the userId");
        int userId = Integer.parseInt(scn.nextLine());
        adminServiceOperation.deleteUser(userId);
    }
    
    //Edit user details
    public static void editUser(){
        User user = new User();
        logger.info("Enter the userId");  //Editing by userId
        int userId = Integer.parseInt(scn.nextLine()); 
        logger.info("Enter new username");
        user.setUsername(scn.nextLine());
        logger.info("Enter new password");
        user.setPassword(scn.nextLine());
        int roleId = adminServiceOperation.getRole(userId);
        if(roleId == -1){
            logger.error("Enter a valid userId");
            return;
        }
        adminServiceOperation.editUser(userId, user);

        switch(roleId){
            case 1:
                editAdmin(userId);
                break;
            case 2:
                editProfessor(userId);
                break;
            case 3:
                editStudent(userId);
                break;
        }
    }
    
    //Editing student by userId
    public static void editStudent(int userId){
        Student student = getStudentDetails(userId);
        adminServiceOperation.editStudent(userId, student);
    }
    
    //Editing professor by userId
    public static void editProfessor(int userId){
        Professor professor = getProfessorDetails(userId);
        adminServiceOperation.editProfessor(userId, professor);
    }

    public static void editAdmin(int userId){

    }
    
    //Fetches all student details from the user while creating a new student
    public static Student getStudentDetails(int studentId){
        Student student = new Student();
        student.setRoleId(3);
        student.setId(studentId);
        logger.info("Enter name");
        student.setName(scn.nextLine());
        logger.info("Enter email");
        student.setEmail(scn.nextLine());
        logger.info("Enter gender (m/f)");
        student.setGender(scn.nextLine());
        logger.info("Enter scholarship amount");
        student.setScholarshipAmount(Double.parseDouble(scn.nextLine()));
        return student;
    }
    
    //Fetches all professor details from the user while creating a new professor
    public static Professor getProfessorDetails(int professorId){
        Professor professor = new Professor();
        professor.setRoleId(2);
        professor.setId(professorId);
        logger.info("Enter name");
        professor.setName(scn.nextLine());
        logger.info("Enter email");
        professor.setEmail(scn.nextLine());
        return professor;
    }
    
    //Provides a detailed list of all courses along with professor IDs, # of students enrolled and fee.
    public static void viewAllCourses(){
        adminServiceOperation.viewAllCourses();
    }
}
