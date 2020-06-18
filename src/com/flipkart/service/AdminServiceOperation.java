package com.flipkart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.flipkart.dao.CourseCatalogDaoImpl;
import com.flipkart.dao.ProfessorDaoImpl;
import com.flipkart.dao.RegisterStudentDaoImpl;
import com.flipkart.dao.StudentDaoImpl;
import com.flipkart.dao.UserDaoImpl;
import com.flipkart.model.Course;
import com.flipkart.model.Professor;
import com.flipkart.model.Registration;
import com.flipkart.model.Student;
import com.flipkart.model.User;


public class AdminServiceOperation implements AdminServiceInterface{
    private final static Logger logger = Logger.getLogger(AdminServiceOperation.class);
    private final UserDaoImpl userDao = new UserDaoImpl();
    private final StudentDaoImpl studentDao = new StudentDaoImpl();
    private final ProfessorDaoImpl professorDao = new ProfessorDaoImpl();
    private final CourseCatalogDaoImpl courseCatalogDao = new CourseCatalogDaoImpl();
    private final RegisterStudentDaoImpl registerStudentDao = new RegisterStudentDaoImpl();
    
    //Creates a new user in the university
    @Override
    public boolean createUser(User user){
        if(!userDao.createUser(user)){
            logger.error("Could not create the user");
            return false;
        }
        return true;
    }
    
    //Create a student using studentId/userId
    @Override
    public void createStudent(Student student){
        if(studentDao.insertStudent(student)){
            logger.info("Student created!"); //Student created
        }else{
            logger.error("Could not create student"); //Error
        }
    }
    
    //Create a professor using professorId/userId
    @Override
    public void createProfessor(Professor professor){
        if(professorDao.insertProfessor(professor)){ //Professor created successfully
            logger.info("Professor created!");
        }else{
            logger.error("Could not create professor"); //Error
        }
    }
    
    //Create a new course for the catalog
    @Override
    public void createCourse(Course course){
        if(courseCatalogDao.addCourseToCatalog(course)){
            logger.info("Course created"); //Course created
        }else{
            logger.error("Could not create course"); //Error
        }
    }
    
    //Displays a list of all students
    @Override
    public void viewAllStudents(){
        List<Student> studentList = studentDao.viewAllStudents();
        List<Student> newList = studentList.stream().filter(student -> { //Append Mr / Ms according to student's gender
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

    //Displays a list of all professors
    @Override
    public void viewAllProfessors(){
        List<Professor> professorList = professorDao.viewAllProfessors();
        logger.info(String.format("%15s %15s %15s %25s", "Professor ID", "Username", "Name", "Email ID"));
        professorList.forEach(professor -> logger.info(String.format("%15s %15s %15s %25s", professor.getId(), professor.getUsername(), professor.getName(), professor.getEmail())));
    }
    
    //View payment details of the registered students
    @Override
    public void viewPaymentDetails(){
        List<Registration> paymentList = registerStudentDao.getRegisteredStudents();
        System.out.println(paymentList.size());
        logger.info(String.format("%15s %15s %20s %15s %15s %15s", "Student Name", "Student ID", "Registration ID", "Mode", "Date", "Amount"));
        paymentList.forEach(payment -> logger.info(String.format("%15s %15s %20s %15s %15s %15s", payment.getStudentName(), payment.getStudentId(), payment.getRegistrationId(), payment.getMode(), payment.getDate(), payment.getAmount())));
    }

    //Delete a course from the catalog
    @Override
    public void deleteCourse(int courseId){
        if(courseCatalogDao.deleteCourse(courseId)){
            logger.info("Course " + courseId + " deleted successfully");
        }else{
            logger.info("Please enter a valid courseId");
        }
    }
    
    //Delete a user from the university using userId
    @Override
    public void deleteUser(int userId){
        int role = userDao.getRoleById(userId); //Fetch the role
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
    
    //Delete a student using userId
    @Override
    public void deleteStudent(int userId){
        if(!userDao.deleteUser(userId)){
            logger.error("Could not delete user " + userId);
        }

        if(studentDao.deleteStudent(userId)){
            logger.info("Student " + userId + " deleted successfully");
        }
    }
    
    //Delete a professor using userId
    @Override
    public void deleteProfessor(int userId){
        if(!userDao.deleteUser(userId)){
            logger.error("Could not delete user " + userId);
        }

        if(professorDao.deleteProfessor(userId)){
            logger.info("Professor " + userId + " deleted successfully");
        }
    }
    
    //Deletes an admin using userId
    @Override
    public void deleteAdmin(int userId){
        if(!userDao.deleteUser(userId)){
            logger.error("Could not delete user " + userId);
        }
    }
    
    //Edit user details
    @Override
    public void editUser(int userId, User user){
        if(!userDao.editUser(userId, user)){
            logger.error("Enter a valid user ID");
        }
    }
    
    //Editing student by userId
    @Override
    public void editStudent(int userId, Student student){
        if(studentDao.updateStudent(userId, student)){
            logger.info("Student " + userId + " updated successfully");
        }else{
            logger.error("Could not update student " + userId);
        }
    }
    
    //Editing professor by userId
    @Override
    public void editProfessor(int userId, Professor professor){
        if(professorDao.updateProfessor(userId, professor)){
            logger.info("Professor " + userId + " updated successfully");
        }else{
            logger.error("Could not update professor " + userId);
        }
    }
    
    //Fetches role of the user
    @Override
    public int getRole(int userId){
        if(userDao.getRoleById(userId) != -1){
            return userDao.getRoleById(userId);
        }
        return -1;
    }
    
    //Displays a list of all courses on the console
    @Override
    public void viewAllCourses(){
        List<Course> courseList = courseCatalogDao.viewAllCourses();
        logger.info(String.format("%15s %20s %20s %20s %15s %15s %15s", "COURSE ID", "NAME", "PROFESSOR ID", " # OF STUDENTS", "CREDITS", "FEE", "CATALOG TYPE"));
        courseList.forEach(course -> {
            String professorId = Integer.toString(course.getProfessorId());
            if(professorId.equals("0")){
                professorId = "Not allotted";
            }
            logger.info(String.format("%15s %20s %20s %20s %15s %15s %15s", course.getCourseId(), course.getCourseName(), professorId, course.getCountOfStudents(), course.getCredits(), course.getFee(), course.getCatalogType()));
        });
    }
}
