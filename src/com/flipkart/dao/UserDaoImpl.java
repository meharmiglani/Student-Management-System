package com.flipkart.dao;

import com.flipkart.constant.SQLConstantQueries;
import com.flipkart.utils.DBUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao{
    private static Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Override
    public int checkIdentity(String username, String password) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(SQLConstantQueries.CHECK_USER);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                int userId = result.getInt(1);
                return userId;
            }
            return -1;
        }catch (SQLException e){
            logger.error(e.getMessage());
            return -1;
        }finally {
            //Close the statement
            try {
                if(statement != null) {
                    statement.close();
                }
            }catch(SQLException e) {
                logger.error(e.getMessage());
            }

            //Close the connection
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
    public String getStudentName(int studentId) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;

        try{
            statement = conn.prepareStatement(SQLConstantQueries.GET_STUDENT_NAME);
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                return resultSet.getString(1);
            }

            logger.error("No such student found");
            return "";

        }catch (SQLException e){
            logger.error(e.getMessage());
            return "";
        }finally {
            //Close the statement
            try {
                if(statement != null) {
                    statement.close();
                }
            }catch(SQLException e) {
                logger.error(e.getMessage());
            }

            //Close the connection
            try {
                if(conn != null) {
                    conn.close();
                }
            }catch(SQLException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
