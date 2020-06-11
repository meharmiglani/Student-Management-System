package com.flipkart.dao;

import com.flipkart.constant.SQLConstantQueries;
import com.flipkart.utils.DBUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterCourseDaoImpl implements RegisterCourseDao{
    private static Logger logger = Logger.getLogger(RegisterCourseDaoImpl.class);


    @Override
    public boolean addCourse(int studentId, String studentName, int courseId, String alternate) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        PreparedStatement statement3 = null;

        try{
            statement1 = conn.prepareStatement(SQLConstantQueries.COUNT_OF_STUDENTS);
            statement1.setInt(1, courseId);
            ResultSet result = statement1.executeQuery();
            int count = 0;
            if(result.next()){
                count = result.getInt(1);
            }

            if(count < 10){
                int newCount = count + 1;
                statement2 = conn.prepareStatement(SQLConstantQueries.UPDATE_COUNT);
                statement2.setInt(1, newCount);
                statement2.setInt(2, courseId);
                statement2.executeUpdate();

                statement3 = conn.prepareStatement(SQLConstantQueries.ADD_COURSE);
                statement3.setInt(1, courseId);
                statement3.setInt(2, studentId);
                statement3.setString(3, studentName);
                statement3.setString(4, alternate);
                int rows = statement3.executeUpdate();
                return rows == 1;
            }else{
                //Throw Exception
                logger.error("Course Limit Exceeded");
                return false;
            }

        }catch(SQLException e){
            logger.error(e.getMessage());
            return false;
        }finally {
            try {
                if(statement1 != null) {
                    statement1.close();
                }

                if(statement2 != null) {
                    statement2.close();
                }

                if(statement3 != null) {
                    statement3.close();
                }

            }catch(SQLException e) {
                logger.error(e.getMessage());
            }

            try {
                if(conn != null) {
                    conn.close();
                }
            }catch(SQLException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Override
    public boolean deleteCourse(int studentId, int courseId) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        PreparedStatement statement3 = null;

        try{
            statement1 = conn.prepareStatement(SQLConstantQueries.COUNT_OF_STUDENTS);
            statement1.setInt(1, courseId);
            ResultSet result = statement1.executeQuery();
            System.out.println("Reached here successfully 1 *****");

            int count = 0;
            if(result.next()){
                count = result.getInt(1);
            }
            int newCount = count - 1;

            statement2 = conn.prepareStatement(SQLConstantQueries.UPDATE_COUNT);
            statement2.setInt(1, newCount);
            statement2.setInt(2, courseId);
            statement2.executeUpdate();
            System.out.println("Reached here successfully 2 *****");

            statement3 = conn.prepareStatement(SQLConstantQueries.DELETE_COURSE);
            statement3.setInt(1, studentId);
            statement3.setInt(2, courseId);
            int rows = statement3.executeUpdate();

            return rows == 1;

        }catch(SQLException e){
            logger.error(e.getMessage());
            return false;
        }
    }
}
