package com.flipkart.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.flipkart.dao.CourseCatalogDaoImpl;
import com.flipkart.dao.FetchReportCardDaoImpl;
import com.flipkart.dao.PayFeeDaoImpl;
import com.flipkart.dao.RegisterCourseDaoImpl;
import com.flipkart.dao.RegisterStudentDaoImpl;
import com.flipkart.dao.ViewRegisteredCoursesDaoImpl;
import com.flipkart.exception.CannotAddMoreCourseException;
import com.flipkart.exception.CourseLimitExceededException;
import com.flipkart.model.Course;
import com.flipkart.model.Grade;
import com.flipkart.model.Payment;
import com.flipkart.utils.CourseListInterface;

public class StudentServiceOperation implements StudentServiceInterface, CourseListInterface {
    private final static LocalDate localDate = LocalDate.now();
    private final static LocalTime localTime = LocalTime.now();
    private static final Scanner scn = new Scanner(System.in);
    private final static Logger logger = Logger.getLogger(StudentServiceOperation.class);
    private final RegisterCourseDaoImpl registerCourseDao = new RegisterCourseDaoImpl();
    private final ViewRegisteredCoursesDaoImpl viewRegisteredCoursesDao = new ViewRegisteredCoursesDaoImpl();
    private final CourseCatalogDaoImpl courseCatalogDao = new CourseCatalogDaoImpl();
    private final FetchReportCardDaoImpl fetchReportCardDao = new FetchReportCardDaoImpl();
    private final RegisterStudentDaoImpl registerStudentDao = new RegisterStudentDaoImpl();
    private final PayFeeDaoImpl payFeeDao = new PayFeeDaoImpl();

    //View catalog of all the courses available
    @Override
    public void viewCourseCatalog(int catalogId){
        List<Course> list = courseCatalogDao.viewCourseCatalog(catalogId);
        logger.info("******************* COURSE CATALOG ************************");
        logger.info(String.format("%15s %15s %20s", "COURSE ID", "CREDITS","COURSE NAME"));
        list.forEach(course -> {
            logger.info(String.format("%15s %15s %20s", course.getCourseId(), course.getCredits(), course.getCourseName()));
        });
        logger.info("************************************************************");
    }

    // Add a course to the student's list
    @Override
    public boolean addCourse(int studentId, String studentName, int courseId) throws CourseLimitExceededException, CannotAddMoreCourseException{
        return registerCourseDao.addCourse(studentId, studentName, courseId);
    }

    // Delete a course from the student's list
    public boolean deleteCourse(int studentId, int courseId) {
        return registerCourseDao.deleteCourse(studentId, courseId);
    }

    //View marks by subject (Available after final registration has been submitted)
    @Override
    public void viewMarksByCourse(int studentId) {
        if(!registerStudentDao.checkRegistration(studentId)){
            logger.info("Register to view your marks");
            return;
        }

        List<Grade> gradeList = fetchReportCardDao.getMarksByCourse(studentId);
        displayMarks(gradeList);
    }

    // View courses currently added for registration
    @Override
    public void viewRegisteredCourses(int studentId) {
        List<Course> list = viewRegisteredCoursesDao.viewRegisteredCourses(studentId);
        if(list != null){
            logger.info("*************** List of Registered Courses *****************");
            logger.info(String.format("%20s %20s", "COURSE ID", "COURSE NAME"));
            list.forEach(course -> logger.info(String.format("%20s %20s", course.getCourseId(), course.getCourseName())));
            logger.info("************************************************************");
        }
    }

    //View Report Card (Available after final registration has been submitted)
    @Override
    public void viewReportCard(int studentId) {
        if(!registerStudentDao.checkRegistration(studentId)){ //Checks if the student is registered or not
            logger.info("Register to view your report card");  
            return;
        }
        logger.info("*************** Report Card for student " + studentId + " *********************");
        List<Grade> gradeList = fetchReportCardDao.getReportCard(studentId);
        if(!displayMarks(gradeList)){
            return;
        }
        
        double percentage = fetchReportCardDao.getFinalPercentage(studentId); //Calculates the final % of all courses
        logger.info("Final Percentage: " + percentage);
        if(percentage >= 50){
            logger.info("Status: Passed"); //Student is passed if % >= 50 else fail
        }else{
            logger.info("Status: Failed");
        }
        logger.info("**********************************************************************************");
    }
    
    //Displays marks for all enrolled subjects for a student
    @Override	
    public boolean displayMarks(List<Grade> gradeList){
        if(gradeList != null){
            logger.info(String.format("%20s %20s %20s", "COURSE ID", "COURSE NAME", "MARKS"));
            gradeList.forEach(grade -> {
                String marks = Integer.toString(grade.getMarks());
                if(marks.equals("-1")){
                    marks = "Course not graded.";
                }
                logger.info(String.format("%20s %20s %20s", grade.getCourseId(), grade.getCourseName(), marks));
            });
            return true;
        }
        return false;
    }
    
    //Submits the registration with the chosen courses
    @Override
    public void submitRegistration(int studentId){
        if(registerStudentDao.checkRegistration(studentId)){ //Can't register again once already registered
            logger.info("You are already registered");
            return;
        }

        int enrolledCourses = registerCourseDao.courseLimitCheck(studentId); //Checks if 4 courses have been selected by the student
        if(enrolledCourses < 4){
            logger.info("Please select 4 courses to submit your registration. You are currently enrolled in " + enrolledCourses + " courses");
            return;
        }

        //Pay Fee in order to confirm the registration
        logger.info("Please pay the fee in order to confirm your registration. Below is a summary of your chosen courses");
        List<Payment> feeList = payFeeDao.fetchCourseFee(studentId);
        double fee = 0;
        logger.info(String.format("%20s %20s %20s", "COURSE ID", "COURSE NAME", "FEE"));
        
        //Calculate total fee for all subjects
        for(Payment payment: feeList){
            fee += payment.getFee();
            logger.info(String.format("%20s %20s %20s", payment.getCourseId(), payment.getCourseName(), payment.getFee()));
        }
        logger.info("Fee payable: " + fee);
        
        //Retrieve the scholarship amount
        double scholarshipAmount = payFeeDao.getScholarshipAmount(studentId); //Checks if a student has a scholarship or not

        if(scholarshipAmount != -1){
            double reduction = (scholarshipAmount/100) * fee;
            fee -= reduction;
        }
        
        logger.info("Scholarship Amount for student " + studentId + " = " + scholarshipAmount + "%"); //Displays final fee
        logger.info("Fee after scholarship: " + fee);
        logger.info("Press Y to pay fee and N to cancel registration.");
        String ans = scn.nextLine();

        if(ans.equalsIgnoreCase("N")){
            logger.info("Registration cancelled");
            return;
        }

        int paymentId = 1;
        logger.info("Enter mode: 1 - Card\n2 - Cash\n3 - Wallet"); //Asks for payment method
        try{
            paymentId = Integer.parseInt(scn.nextLine());
        }catch (NumberFormatException e){
            logger.error(e.getMessage());
            return;
        }

        logger.info("Total fee paid: " + fee + "on " + localDate + " " + localTime.getHour() + ":" + localTime.getMinute() + " " + localDate.getDayOfWeek());

        if(payFeeDao.insertIntoRegistration(studentId, paymentId, fee)){ //The student is finally registered
            registerStudentDao.registerStudent(studentId);
        }
    }
    
    @Override
    public boolean canAddCourse(int studentId){ //Helper function to check is a student can add more courses or not
        int enrolledCourses = registerCourseDao.courseLimitCheck(studentId); //Max courses = 4
        if(enrolledCourses == 4){
            logger.info("You have reached the maximum course limit.");
            return false;
        }
        return true;
    }
    
    @Override
    public boolean checkForRegistration(int studentId){ //Checks if a student is already registered, if yes then he can't add/delete more courses
        return registerStudentDao.checkRegistration(studentId);
    }
}