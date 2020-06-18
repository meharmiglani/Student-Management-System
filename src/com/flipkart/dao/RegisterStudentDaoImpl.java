package com.flipkart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.flipkart.constant.SQLConstantQueries;
import com.flipkart.model.Registration;
import com.flipkart.utils.CloseConnectionInterface;
import com.flipkart.utils.DBUtil;

//Handles operations when a student submits his final registration to enroll in all chosen courses
public class RegisterStudentDaoImpl implements RegisterStudentDao, CloseConnectionInterface {
    private static final Logger logger = Logger.getLogger(RegisterStudentDaoImpl.class);

    /*Registers a student after performing the following checks
     * 1. Student must not be registered before
     * 2. Student must have chosen 4 courses
     */
    @Override
    public boolean registerStudent(int studentId) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;

        try{
            statement = conn.prepareStatement(SQLConstantQueries.REGISTER_STUDENT);
            statement.setString(1, "yes");
            statement.setInt(2, studentId);
            int row = statement.executeUpdate();

            if(row == 1){
                logger.info("Student " + studentId + " registered successfully");
                return true;
            }else{
                logger.info("Could not submit registration");
                return false;
            }

        }catch (SQLException e){
            logger.error(e.getMessage());
            return false;
        }finally {
            closeConnection(statement, conn);
        }
    }

    //Checks if a student is already registered as he can't register again
    @Override
    public boolean checkRegistration(int studentId){
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(SQLConstantQueries.IS_REGISTERED);
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() && resultSet.getString(1).equals("yes");

        }catch (SQLException e){
            logger.error(e.getMessage());
            return false;
        }
    }

    //Fetches a list of all registered students and their payment details
    @Override
    public List<Registration> getRegisteredStudents(){
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        List<Registration> registrationList = new ArrayList<>();
        try{
            statement = conn.prepareStatement(SQLConstantQueries.GET_PAYMENT_DETAILS);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                Registration registration = new Registration();
                registration.setStudentName(resultSet.getString(1));
                registration.setStudentId(resultSet.getInt(2));
                registration.setRegistrationId(resultSet.getInt(3));
                registration.setMode(resultSet.getString(4));
                registration.setDate(resultSet.getObject(5, LocalDate.class));
                registration.setAmount(resultSet.getDouble(6));
                registrationList.add(registration);
            }
            return registrationList;

        }catch (SQLException e){
            logger.error(e.getMessage());
            return null;
        }finally {
            closeConnection(statement, conn);
        }
    }
}
