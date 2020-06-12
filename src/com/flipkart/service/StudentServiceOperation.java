package com.flipkart.service;

import com.flipkart.dao.CourseCatalogDaoImpl;
import com.flipkart.dao.RegisterCourseDaoImpl;
import com.flipkart.dao.ViewRegisteredCoursesDaoImpl;
import com.flipkart.java8.CourseListInterface;
import com.flipkart.model.Course;
import org.apache.log4j.Logger;

import java.util.List;

public class StudentServiceOperation implements StudentServiceInterface, CourseListInterface {
    private final static Logger logger = Logger.getLogger(StudentServiceOperation.class);
    private final RegisterCourseDaoImpl registerCourseDao = new RegisterCourseDaoImpl();
    private final ViewRegisteredCoursesDaoImpl viewRegisteredCoursesDao = new ViewRegisteredCoursesDaoImpl();
    private final CourseCatalogDaoImpl courseCatalogDao = new CourseCatalogDaoImpl();

    //view catalog
    public void viewCourseCatalog(){
        List<Course> list = courseCatalogDao.viewCourseCatalog();
        logger.info("************** COURSE CATALOG *******************");
        String str = "COURSE ID\t\tCREDITS\t\tCOURSE NAME";
        logger.info(str);
        list.forEach(course -> {
            StringBuilder sb = new StringBuilder();
            sb.append(course.getCourseId()).append("\t\t\t\t").append(course.getCredits()).append("\t\t\t\t").append(course.getCourseName());
            logger.info(sb.toString());
        });
        logger.info("*************************************************");
    }

    // AddCourse
    public boolean addCourse(int studentId, String studentName, int courseId, String alternate) {
        if(registerCourseDao.addCourse(studentId, studentName, courseId, alternate)){
            return true;
        }
        logger.error("Error adding course");
        return false;
    }

    // DeleteCourse
    public boolean deleteCourse(int studentId, int courseId) {
        if(registerCourseDao.deleteCourse(studentId, courseId)){
            return true;
        }
        logger.error("You are not enrolled in this course!");
        return false;
    }

    // Pay fee

    // View report card

    // View registered course
    public void viewRegisteredCourses(int studentId) {
        List<Course> list = viewRegisteredCoursesDao.viewRegisteredCourses(studentId);
        if(list != null){
            logger.info("*********** List of Registered Courses ************");
            int i = 0;
            for(Course course: list){
                logger.info("Course " + i);
                logger.info("Course ID: " + course.getCourseId());
                logger.info("Course Name: " + course.getCourseName());
                logger.info("Professor Name: " + course.getProfessorName());
                i++;
            }
            logger.info("***************************************************");
        }
    }
}
