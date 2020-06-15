package com.flipkart.service;

import com.flipkart.dao.*;
import com.flipkart.model.Payment;
import com.flipkart.utils.CourseListInterface;
import com.flipkart.model.Course;
import com.flipkart.model.Grade;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

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

    //View catalog
    public void viewCourseCatalog(){
        List<Course> list = courseCatalogDao.viewCourseCatalog();
        logger.info("******************* COURSE CATALOG ************************");
        logger.info(String.format("%15s %15s %20s", "COURSE ID", "CREDITS","COURSE NAME"));
        list.forEach(course -> {
            logger.info(String.format("%15s %15s %20s", course.getCourseId(), course.getCredits(), course.getCourseName()));
        });
        logger.info("************************************************************");
    }

    // AddCourse
    public boolean addCourse(int studentId, String studentName, int courseId) {
        return registerCourseDao.addCourse(studentId, studentName, courseId);
    }

    // DeleteCourse
    public boolean deleteCourse(int studentId, int courseId) {
        return registerCourseDao.deleteCourse(studentId, courseId);
    }

    //View marks by subject
    @Override
    public void viewMarksByCourse(int studentId) {
        List<Grade> gradeList = fetchReportCardDao.getMarksByCourse(studentId);
        if (gradeList == null){
            logger.info("Submit Registration to view your marks");
        }
        displayMarks(gradeList);
    }

    // View registered course
    public void viewRegisteredCourses(int studentId) {
        List<Course> list = viewRegisteredCoursesDao.viewRegisteredCourses(studentId);
        if(list != null){
            logger.info("*************** List of Registered Courses *****************");
            logger.info(String.format("%20s %20s", "COURSE ID", "COURSE NAME"));
            list.forEach(course -> logger.info(String.format("%20s %20s", course.getCourseId(), course.getCourseName())));
            logger.info("************************************************************");
        }
    }

    //View Report Card
    @Override
    public void viewReportCard(int studentId) {
        if(!registerStudentDao.checkRegistration(studentId)){
            logger.info("Register to view your report card");
            return;
        }
        logger.info("*************** Report Card for student " + studentId + " *********************");
        List<Grade> gradeList = fetchReportCardDao.getReportCard(studentId);
        if(!displayMarks(gradeList)){
            return;
        }
        double sum = 0;
        for(Grade grade: gradeList){
            sum += grade.getMarks();
        }
        double percentage = sum / 4;
        logger.info("Final Percentage: " + percentage);
        if(percentage >= 50){
            logger.info("Status: Passed");
        }else{
            logger.info("Status: Failed");
        }
        logger.info("**********************************************************************************");
    }

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

    public void submitRegistration(int studentId){
        if(registerStudentDao.checkRegistration(studentId)){
            logger.info("You are already registered");
            return;
        }

        int enrolledCourses = registerCourseDao.courseLimitCheck(studentId);
        if(enrolledCourses < 4){
            logger.info("Please select 4 courses to submit your registration. You are currently enrolled in " + enrolledCourses + " courses");
            return;
        }

        //Pay Fee
        logger.info("Please pay the fee in order to confirm your registration. Below is a summary of your chosen courses");
        List<Payment> feeList = payFeeDao.fetchCourseFee(studentId);
        double fee = 0;
        logger.info(String.format("%20s %20s %20s", "COURSE ID", "COURSE NAME", "FEE"));
        for(Payment payment: feeList){
            fee += payment.getFee();
            logger.info(String.format("%20s %20s %20s", payment.getCourseId(), payment.getCourseName(), payment.getFee()));
        }
        logger.info("Fee payable: " + fee);

        double scholarshipAmount = payFeeDao.getScholarshipAmount(studentId);

        if(scholarshipAmount != -1){
            double reduction = (scholarshipAmount/100) * fee;
            fee -= reduction;
        }
        logger.info("Scholarship Amount for student " + studentId + " = " + scholarshipAmount + "%");
        logger.info("Fee after scholarship: " + fee);
        logger.info("Press Y to pay fee and N to cancel registration.");
        String ans = scn.nextLine();

        if(ans.equalsIgnoreCase("N")){
            logger.info("Registration cancelled");
            return;
        }

        int paymentId = 1;
        logger.info("Enter mode: 1 - Card\n2 - Cash\n3 - Wallet");
        try{
            paymentId = Integer.parseInt(scn.nextLine());
        }catch (NumberFormatException e){
            logger.error(e.getMessage());
            return;
        }

        logger.info("Total fee paid: " + fee + "on " + localDate + " " + localTime.getHour() + ":" + localTime.getMinute() + " " + localDate.getDayOfWeek());

        if(payFeeDao.insertIntoRegistration(studentId, paymentId, fee)){
            registerStudentDao.registerStudent(studentId);
        }
    }

    public boolean canAddCourse(int studentId){
        int enrolledCourses = registerCourseDao.courseLimitCheck(studentId);
        if(enrolledCourses == 4){
            logger.info("You have reached the maximum course limit.");
            return false;
        }
        return true;
    }

    public boolean checkForRegistration(int studentId){
        return registerStudentDao.checkRegistration(studentId);
    }
}