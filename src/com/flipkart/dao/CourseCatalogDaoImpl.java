package com.flipkart.dao;

import com.flipkart.constant.SQLConstantQueries;
import com.flipkart.utils.CloseConnectionInterface;
import com.flipkart.model.Course;
import com.flipkart.utils.DBUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseCatalogDaoImpl implements CourseCatalogDao, CloseConnectionInterface {
    private final static Logger logger = Logger.getLogger(CourseCatalogDaoImpl.class);

    @Override
    public List<Course> viewCourseCatalog() {
        List<Course> catalog = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(SQLConstantQueries.GET_CATALOG);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                int courseId = resultSet.getInt(1);
                String courseName = resultSet.getString(2);
                int credits = resultSet.getInt(3);
                Course course = new Course(courseName, courseId, credits);
                catalog.add(course);
            }
            return catalog;

        }catch (SQLException e){
            logger.error(e.getMessage());
            return null;
        }finally {
            closeConnection(statement, conn);
        }
    }

    @Override
    public boolean addCourseToCatalog(Course course) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(SQLConstantQueries.CREATE_COURSE);
            statement.setInt(1, course.getCourseId());
            statement.setString(2, course.getCourseName());
            statement.setInt(3, 0);
            statement.setInt(4, 0);
            statement.setInt(5, course.getCredits());
            statement.setDouble(6, course.getFee());
            int row = statement.executeUpdate();

            return row == 1;
        }catch (SQLException e){
            logger.error(e.getMessage());
            return false;
        }finally {
            closeConnection(statement, conn);
        }
    }

    @Override
    public boolean deleteCourse(int courseId) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;

        try{
            statement = conn.prepareStatement(SQLConstantQueries.DELETE_COURSE);
            statement.setInt(1, courseId);
            int row = statement.executeUpdate();

            statement1 = conn.prepareStatement(SQLConstantQueries.DELETE_REGISTER_COURSE);
            statement1.setInt(1, courseId);
            int row1 = statement1.executeUpdate();

            statement2 = conn.prepareStatement(SQLConstantQueries.DELETE_PROFESSOR_COURSE);
            statement2.setInt(1, courseId);
            int row2 = statement2.executeUpdate();

            return row == 1 && row1 == 1 && row2 == 1;

        }catch (SQLException e){
            logger.error(e.getMessage());
            return false;
        }finally {
            closeStatement(statement);
            closeStatement(statement1);
            closeConnection(statement2, conn);
        }
    }

    @Override
    public List<Course> viewAllCourses() {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        List<Course> courseList = new ArrayList<>();
        try{
            statement = conn.prepareStatement(SQLConstantQueries.VIEW_ALL_COURSES);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                Course course = new Course();
                course.setCourseId(resultSet.getInt(1));
                course.setCourseName(resultSet.getString(2));
                course.setProfessorId(resultSet.getInt(3));
                course.setCountOfStudents(resultSet.getInt(4));
                course.setCredits(resultSet.getInt(5));
                course.setFee(resultSet.getDouble(6));
                courseList.add(course);
            }
            return courseList;

        }catch (SQLException e){
            logger.error(e.getMessage());
            return null;
        }finally {
            closeConnection(statement, conn);
        }
    }
}
