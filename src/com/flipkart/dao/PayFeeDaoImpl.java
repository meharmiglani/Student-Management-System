package com.flipkart.dao;

import com.flipkart.constant.SQLConstantQueries;
import com.flipkart.model.Payment;
import com.flipkart.utils.CloseConnectionInterface;
import com.flipkart.utils.DBUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PayFeeDaoImpl implements PayFeeDao, CloseConnectionInterface {
    private static final Logger logger = Logger.getLogger(PayFeeDaoImpl.class);

    @Override
    public List<Payment> fetchCourseFee(int studentId) {
        List<Payment> list = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;

        try{
            statement = conn.prepareStatement(SQLConstantQueries.FETCH_COURSE_FEE);
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                int courseId = resultSet.getInt(1);
                String courseName = resultSet.getString(2);
                int fee = resultSet.getInt(3);
                Payment payment = new Payment(courseId, courseName, fee);
                list.add(payment);
            }
            return list;

        }catch (SQLException e){
            logger.error(e.getMessage());
            return null;
        }finally {
            closeConnection(statement, conn);
        }
    }

    @Override
    public double getScholarshipAmount(int studentId) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(SQLConstantQueries.GET_SCHOLARSHIP_AMOUNT);
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getDouble(1);
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
    public boolean makePayment(int studentId, String mode, double amount) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(SQLConstantQueries.INSERT_PAYMENT);
            statement.setInt(1, studentId);
            statement.setString(2, mode);
            statement.setTimestamp(3, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            statement.setDouble(4, amount);
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
