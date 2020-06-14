package com.flipkart.service;

import com.flipkart.dao.*;
import com.flipkart.model.*;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class AdminServiceOperation implements AdminServiceInterface{
    private final static Logger logger = Logger.getLogger(AdminServiceOperation.class);
    private final UserDaoImpl userDao = new UserDaoImpl();
    private final StudentDaoImpl studentDao = new StudentDaoImpl();
    private final ProfessorDaoImpl professorDao = new ProfessorDaoImpl();
    private final CourseCatalogDaoImpl courseCatalogDao = new CourseCatalogDaoImpl();
    private final RegisterStudentDaoImpl registerStudentDao = new RegisterStudentDaoImpl();

    public void createUser(User user){
        if(!userDao.createUser(user)){
            logger.error("Could not create the user");
        }
    }

    public void createStudent(Student student){
        if(studentDao.insertStudent(student)){
            logger.info("Student created!");
        }else{
            logger.error("Could not create student");
        }
    }

    public void createProfessor(Professor professor){
        if(professorDao.insertProfessor(professor)){
            logger.info("Professor created!");
        }else{
            logger.error("Could not create professor");
        }
    }

    public void createCourse(Course course){
        if(courseCatalogDao.addCourseToCatalog(course)){
            logger.info("Course created");
        }else{
            logger.error("Could not create course");
        }
    }

    public void viewAllStudents(){
        List<Student> studentList = studentDao.viewAllStudents();
        List<Student> newList = studentList.stream().filter(student -> {
            if(student.getGender().equalsIgnoreCase("m")){
                student.setName("Mr " + student.getName());
            }else{
                student.setName("Ms " + student.getName());
            }
            return true;
        }).collect(Collectors.toList());
        logger.info(String.format("%15s %15s %15s %20s %15s %15s %25s", "Student ID", "Username", "Name", "Email ID", "Gender", "Registered", "Scholarship Amount (%)"));
        newList.forEach(student -> logger.info(String.format("%15s %15s %15s %20s %15s %15s %25s", student.getId(), student.getUsername(), student.getName(), student.getEmail(),
                student.getGender(), student.getRegistrationStatus(), student.getScholarshipAmount())));
    }

    public void viewAllProfessors(){
        List<Professor> professorList = professorDao.viewAllProfessors();
        logger.info(String.format("%15s %15s %15s %20s", "Professor ID", "Username", "Name", "Email ID"));
        professorList.forEach(professor -> logger.info(String.format("%15s%15s%15s%20s", professor.getId(), professor.getUsername(), professor.getName(), professor.getEmail())));
    }

    public void viewPaymentDetails(){
        List<Registration> paymentList = registerStudentDao.getRegisteredStudents();
        System.out.println(paymentList.size());
        List<Registration> newPaymentList = paymentList.stream().filter(payment -> {
            if(payment.getMode().equals("1")){
                payment.setMode("Cash");
            }else if(payment.getMode().equals("2")){
                payment.setMode("Card");
            }else{
                payment.setMode("Wallet");
            }
            return true;
        }).collect(Collectors.toList());
        logger.info(String.format("%15s %15s %15s %20s", "Student ID", "Amount", "Mode", "Date"));
        newPaymentList.forEach(payment -> logger.info(String.format("%15s %15s %15s %20s", payment.getStudentId(), payment.getAmount(), payment.getMode(), payment.getDate())));
    }

    public void deleteCourse(int courseId){
        if(courseCatalogDao.deleteCourse(courseId)){
            logger.info("Course " + courseId + " deleted successfully");
        }else{
            logger.info("Please enter a valid courseId");
        }
    }

    public void deleteUser(int userId){
        String role = userDao.getRoleById(userId);
        switch(role){
            case "student":
                deleteStudent(userId);
                break;
            case "professor":
                deleteProfessor(userId);
                break;
            case "admin":
                deleteAdmin(userId);
                break;
        }
    }

    public void deleteStudent(int userId){
        if(!userDao.deleteUser(userId)){
            logger.error("Could not delete user " + userId);
        }

        if(studentDao.deleteStudent(userId)){
            logger.info("Student " + userId + " deleted successfully");
        }
    }

    public void deleteProfessor(int userId){
        if(!userDao.deleteUser(userId)){
            logger.error("Could not delete user " + userId);
        }

        if(professorDao.deleteProfessor(userId)){
            logger.info("Professor " + userId + " deleted successfully");
        }
    }

    public void deleteAdmin(int userId){
        if(!userDao.deleteUser(userId)){
            logger.error("Could not delete user " + userId);
        }
    }
}
