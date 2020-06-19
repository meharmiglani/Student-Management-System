package com.flipkart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.flipkart.constant.SQLConstantQueries;
import com.flipkart.exception.NoCourseFoundException;
import com.flipkart.model.Course;
import com.flipkart.utils.CloseConnectionInterface;
import com.flipkart.utils.DBUtil;

//Performs all professor operations on courses
public class CourseToTeachDaoImpl implements CourseToTeachDao, CloseConnectionInterface {
    private static Logger logger = Logger.getLogger(CourseToTeachDaoImpl.class);

    //Professor can select a course to teach
    @Override
    public boolean selectCourse(int professorId, int courseId) throws NoCourseFoundException{
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        PreparedStatement statement1 = null;
        try{
            statement = conn.prepareStatement(SQLConstantQueries.SELECT_COURSE_TO_TEACH_PROFESSOR);
            statement.setInt(1, professorId);
            statement.setInt(2, courseId);
            statement.setInt(3, 0);
            int row = statement.executeUpdate();
            if(row != 1){
            	throw new NoCourseFoundException(courseId);
            }
            statement1 = conn.prepareStatement(SQLConstantQueries.SELECT_COURSE_TO_TEACH_PROFESSOR_2);
            statement1.setInt(1, professorId);
            statement1.setInt(2, courseId);
            int row1 = statement1.executeUpdate();

            return row1 == 1;

        }catch (SQLException e){
            logger.info(e.getMessage());
            return false;
        }finally {
            closeConnection(statement, conn);
            closeConnection(statement1, conn);
        }
    }

    //Professor can view a list of available courses to choose which one to teach
    @Override
    public List<Course> viewCoursesAvailableToTeach(){
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        List<Course> list = new ArrayList<>();
        try{
            statement = conn.prepareStatement(SQLConstantQueries.VIEW_AVAILABLE_COURSE_LIST_TO_TEACH);
            statement.setInt(1, 0);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                int courseId = resultSet.getInt(1);
                String courseName = resultSet.getString(2);
                Course course = new Course(courseId, courseName);
                list.add(course);
            }
            return list;

        }catch (SQLException e){
            logger.info(e.getMessage());
            return null;
        }finally {
            closeConnection(statement, conn);
        }
    }

    //Displays a list of courses that a particular professor is already teaching
    @Override
    public List<Course> coursesTeaching(int professorId) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        List<Course> list = new ArrayList<>();
        try{
            statement = conn.prepareStatement(SQLConstantQueries.VIEW_COURSES_TAUGHT);
            statement.setInt(1, professorId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                int courseId = resultSet.getInt(1);
                String courseName = resultSet.getString(2);
                int countOfStudents = resultSet.getInt(3);
                Course course = new Course(courseId, courseName, countOfStudents);
                list.add(course);
            }
            return list;

        }catch (SQLException e){
            logger.info(e.getMessage());
            return null;
        }finally {
            closeConnection(statement, conn);
        }
    }
}
