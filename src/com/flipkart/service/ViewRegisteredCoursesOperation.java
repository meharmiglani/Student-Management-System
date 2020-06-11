package com.flipkart.service;

import com.flipkart.dao.ViewRegisteredCoursesDaoImpl;
import com.flipkart.model.RegisteredCourses;
import org.apache.log4j.Logger;

import java.util.List;

public class ViewRegisteredCoursesOperation implements ViewRegisteredCoursesInterface{
    private static Logger logger = Logger.getLogger(ViewRegisteredCoursesOperation.class);
    ViewRegisteredCoursesDaoImpl viewRegisteredCoursesDao = new ViewRegisteredCoursesDaoImpl();

    @Override
    public void viewRegisteredCourses(int studentId) {
        List<RegisteredCourses> list = viewRegisteredCoursesDao.viewRegisteredCourses(studentId);
        if(list != null){
            logger.info("\n");
            logger.info("********* List of Registered Courses ************");
            for(RegisteredCourses registeredCourses: list){
                System.out.println(registeredCourses.getCourseName() + "   " + registeredCourses.getProfessorId() + "   " + registeredCourses.getCredits());
            }
            logger.info("*************************************************");
        }
    }
}
