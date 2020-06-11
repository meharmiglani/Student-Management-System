package com.flipkart.service;

import com.flipkart.dao.RegisterCourseDaoImpl;
import org.apache.log4j.Logger;

public class RegisterCourseOperation implements RegisterCourseInterface{

    private static Logger logger = Logger.getLogger(RegisterCourseOperation.class);
    private RegisterCourseDaoImpl registerCourseDao = new RegisterCourseDaoImpl();


    @Override
    public boolean addCourse(int studentId, String studentName, int courseId, String alternate) {
        if(registerCourseDao.addCourse(studentId, studentName, courseId, alternate)){
            return true;
        }
        logger.error("Error adding course");
        return false;
    }

    @Override
    public boolean deleteCourse(int studentId, int courseId) {
        if(registerCourseDao.deleteCourse(studentId, courseId)){
            return true;
        }
        logger.error("You are not enrolled in this course!");
        return false;
    }
}
