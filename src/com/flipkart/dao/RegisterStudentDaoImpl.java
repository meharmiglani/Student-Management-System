package com.flipkart.dao;

import com.flipkart.constant.SQLConstantQueries;
import com.flipkart.model.Registration;
import com.flipkart.utils.CloseConnectionInterface;
import com.flipkart.utils.DBUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegisterStudentDaoImpl implements RegisterStudentDao, CloseConnectionInterface {
    private static final Logger logger = Logger.getLogger(RegisterStudentDaoImpl.class);

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
