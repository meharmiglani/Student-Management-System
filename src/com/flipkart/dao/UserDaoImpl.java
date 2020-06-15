package com.flipkart.dao;

import com.flipkart.constant.SQLConstantQueries;
import com.flipkart.model.User;
import com.flipkart.utils.CloseConnectionInterface;
import com.flipkart.utils.DBUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao, CloseConnectionInterface {
    private final static Logger logger = Logger.getLogger(UserDaoImpl.class);

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
                return result.getInt(1);
            }
            return -1;
        }catch (SQLException e){
            logger.error(e.getMessage());
            return -1;
        }finally{
            closeConnection(statement, conn);
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
            closeConnection(statement, conn);
        }
    }

    @Override
    public int getRole(String username, String password) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(SQLConstantQueries.GET_ROLE);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getInt(1);
            }

            return -1;
        }catch (SQLException e){
            logger.error(e.getMessage());
            return -1;
        }finally {
            closeConnection(statement, conn);
        }
    }

    @Override
    public boolean createUser(User user) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(SQLConstantQueries.CREATE_USER);
            statement.setInt(1, user.getId());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getRoleId());
            int row1 = statement.executeUpdate();

            return row1 == 1;

        }catch (SQLException e){
            logger.error(e.getMessage());
            return false;
        }finally {
            closeConnection(statement, conn);
        }
    }

    @Override
    public int getRoleById(int userId) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(SQLConstantQueries.GET_ROLE_BY_ID);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt(1);
            }
            return -1;

        }catch (SQLException e){
            logger.error(e.getMessage());
            return -1;
        }finally {
            closeConnection(statement, conn);
        }
    }

    @Override
    public boolean deleteUser(int userId) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(SQLConstantQueries.DELETE_USER_TABLE);
            statement.setInt(1, userId);
            int row1 = statement.executeUpdate();

            return row1 == 1;

        }catch (SQLException e){
            logger.error(e.getMessage());
            return false;
        }finally {
            closeConnection(statement, conn);
        }
    }

    @Override
    public boolean editUser(int userId, User user) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(SQLConstantQueries.EDIT_USER);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setInt(3, userId);
            int row = statement.executeUpdate();
            return row == 1;

        }catch (SQLException e){
            logger.error(e.getMessage());
            return false;
        }finally {
            closeConnection(statement, conn);
        }
    }
}
