package com.flipkart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.flipkart.constant.SQLConstantQueries;
import com.flipkart.model.User;
import com.flipkart.utils.CloseConnectionInterface;
import com.flipkart.utils.DBUtil;

//Performs all user specific operations (Checking identity, fetching role, CRUD)
public class UserDaoImpl implements UserDao, CloseConnectionInterface {
    private final static Logger logger = Logger.getLogger(UserDaoImpl.class);

    //Checks for authentication whie logging in
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

    //Fetches a student's name based on his id
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

    //Fetches role of a user by his username and password
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

    //Creates a new user in the DB
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

    //Fetches role of a user by his id
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

    //Deletes a user from the DB
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

    //Edits the details of the user in the DB
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
