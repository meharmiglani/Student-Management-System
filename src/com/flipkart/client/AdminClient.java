package com.flipkart.client;

import com.flipkart.model.Course;
import com.flipkart.model.Professor;
import com.flipkart.model.Student;
import com.flipkart.model.User;
import com.flipkart.service.AdminServiceOperation;
import org.apache.log4j.Logger;
import java.util.Scanner;

public class AdminClient {
    private final static Logger logger = Logger.getLogger(AdminClient.class);
    private final static Scanner scn = new Scanner(System.in);
    private final static AdminServiceOperation adminServiceOperation = new AdminServiceOperation();

    public static void adminHandler(int adminId){
        if(adminId != -1){
            while(true){
                displayAdminMenu();
                System.out.println("Enter your choice");
                int choice = Integer.parseInt(scn.nextLine());

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
                        createCourse();
                        break;
                    case 5:
                        deleteCourse();
                        break;
                    case 6:
                        viewAllStudents();
                        break;
                    case 7:
                        viewAllProfessors();
                        break;
                    case 8:
                        viewAllCourses();
                        break;
                    case 9:
                        viewPaymentDetails();
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
            logger.error("Check your username or password");
        }
    }

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
        switch (role){
            case "student":
                user.setRoleId(3);
                adminServiceOperation.createUser(user);
                createStudent(userId);
                break;
            case "professor":
                user.setRoleId(2);
                adminServiceOperation.createUser(user);
                createProfessor(userId);
            case "admin":
                user.setRoleId(1);
                adminServiceOperation.createUser(user);
                createAdmin(userId);
                break;
        }
    }

    public static void createStudent(int studentId){
        Student student = getStudentDetails(studentId);
        adminServiceOperation.createStudent(student);
    }

    public static void createProfessor(int professorId){
        Professor professor = getProfessorDetails(professorId);
        adminServiceOperation.createProfessor(professor);
    }

    public static void createAdmin(int adminId){

    }

    public static void createCourse(){
        Course course = new Course();
        logger.info("Enter courseID");
        course.setCourseId(Integer.parseInt(scn.nextLine()));
        logger.info("Enter course name");
        course.setCourseName(scn.nextLine());
        logger.info("Enter credits");
        course.setCredits(Integer.parseInt(scn.nextLine()));
        logger.info("Enter course fee");
        course.setFee(Integer.parseInt(scn.nextLine()));
        adminServiceOperation.createCourse(course);
    }

    public static void viewAllStudents(){
        adminServiceOperation.viewAllStudents();
    }

    public static void viewAllProfessors(){
        adminServiceOperation.viewAllProfessors();
    }

    public static void viewPaymentDetails(){
        adminServiceOperation.viewPaymentDetails();
    }

    public static void deleteCourse(){
        logger.info("Enter a course ID to delete");
        int courseId = Integer.parseInt(scn.nextLine());
        adminServiceOperation.deleteCourse(courseId);
    }

    public static void deleteUser(){
        logger.info("Enter the userId");
        int userId = Integer.parseInt(scn.nextLine());
        adminServiceOperation.deleteUser(userId);
    }

    public static void editUser(){
        User user = new User();
        logger.info("Enter the userId");
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

    public static void editStudent(int userId){
        Student student = getStudentDetails(userId);
        adminServiceOperation.editStudent(userId, student);
    }

    public static void editProfessor(int userId){
        Professor professor = getProfessorDetails(userId);
        adminServiceOperation.editProfessor(userId, professor);
    }

    public static void editAdmin(int userId){

    }

    public static Student getStudentDetails(int studentId){
        Student student = new Student();
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

    public static Professor getProfessorDetails(int professorId){
        Professor professor = new Professor();
        professor.setId(professorId);
        logger.info("Enter name");
        professor.setName(scn.nextLine());
        logger.info("Enter email");
        professor.setEmail(scn.nextLine());
        return professor;
    }

    public static void viewAllCourses(){
        adminServiceOperation.viewAllCourses();
    }
}
