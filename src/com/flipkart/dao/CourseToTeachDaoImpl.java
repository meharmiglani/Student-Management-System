package com.flipkart.dao;

import com.flipkart.constant.SQLConstantQueries;
import com.flipkart.java8.CloseConnectionInterface;
import com.flipkart.model.Course;
import com.flipkart.utils.DBUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseToTeachDaoImpl implements CourseToTeachDao, CloseConnectionInterface {
    private static Logger logger = Logger.getLogger(CourseToTeachDaoImpl.class);

    @Override
    public boolean selectCourse(int professorId, int courseId) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(SQLConstantQueries.SELECT_COURSE_TO_TEACH_PROFESSOR);
            statement.setInt(1, professorId);
            statement.setInt(2, courseId);
            statement.setInt(3, 0);
            int row = statement.executeUpdate();

            return row == 1;

        }catch (SQLException e){
            logger.info(e.getMessage());
            return false;
        }finally {
            closeConnection(statement, conn);
        }
    }

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
