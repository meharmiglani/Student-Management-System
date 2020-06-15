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
        logger.info(String.format("%15s %15s %15s %25s", "Professor ID", "Username", "Name", "Email ID"));
        professorList.forEach(professor -> logger.info(String.format("%15s %15s %15s %25s", professor.getId(), professor.getUsername(), professor.getName(), professor.getEmail())));
    }

    public void viewPaymentDetails(){
        List<Registration> paymentList = registerStudentDao.getRegisteredStudents();
        System.out.println(paymentList.size());
        logger.info(String.format("%15s %15s %20s %15s %15s %15s", "Student Name", "Student ID", "Registration ID", "Mode", "Date", "Amount"));
        paymentList.forEach(payment -> logger.info(String.format("%15s %15s %20s %15s %15s %15s", payment.getStudentName(), payment.getStudentId(), payment.getRegistrationId(), payment.getMode(), payment.getDate(), payment.getAmount())));
    }

    public void deleteCourse(int courseId){
        if(courseCatalogDao.deleteCourse(courseId)){
            logger.info("Course " + courseId + " deleted successfully");
        }else{
            logger.info("Please enter a valid courseId");
        }
    }

    public void deleteUser(int userId){
        int role = userDao.getRoleById(userId);
        switch(role){
            case 3:
                deleteStudent(userId);
                break;
            case 2:
                deleteProfessor(userId);
                break;
            case 1:
                deleteAdmin(userId);
                break;
            default:
                logger.error("Enter a valid userId");
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

    public void editUser(int userId, User user){
        if(!userDao.editUser(userId, user)){
            logger.error("Enter a valid user ID");
        }
    }

    public void editStudent(int userId, Student student){
        if(studentDao.updateStudent(userId, student)){
            logger.info("Student " + userId + " updated successfully");
        }else{
            logger.error("Could not update student " + userId);
        }
    }

    public void editProfessor(int userId, Professor professor){
        if(professorDao.updateProfessor(userId, professor)){
            logger.info("Professor " + userId + " updated successfully");
        }else{
            logger.error("Could not update professor " + userId);
        }
    }

    public void editAdmin(int userId, Admin admin){

    }

    public int getRole(int userId){
        if(userDao.getRoleById(userId) != -1){
            return userDao.getRoleById(userId);
        }
        return -1;
    }

    public void viewAllCourses(){
        List<Course> courseList = courseCatalogDao.viewAllCourses();
        logger.info(String.format("%15s %20s %25s %20s %20s %20s", "COURSE ID", "NAME", "PROFESSOR ID", "COUNT OF STUDENTS", "CREDITS", "FEE"));
        courseList.forEach(course -> {
            String professorId = Integer.toString(course.getProfessorId());
            if(professorId.equals("0")){
                professorId = "Professor not allotted";
            }
            logger.info(String.format("%15s %20s %25s %20s %20s %20s", course.getCourseId(), course.getCourseName(), professorId, course.getCountOfStudents(), course.getCredits(), course.getFee()));
        });
    }
}
