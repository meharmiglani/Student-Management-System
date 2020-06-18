package com.flipkart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.flipkart.constant.SQLConstantQueries;
import com.flipkart.model.Grade;
import com.flipkart.utils.CloseConnectionInterface;
import com.flipkart.utils.DBUtil;

//Performs all operations on marks/report card of a student
public class FetchReportCardDaoImpl implements FetchReportCardDao, CloseConnectionInterface {
    private final static Logger logger = Logger.getLogger(FetchReportCardDaoImpl.class);

    //Checks and verifies if a report card is available or not
    @Override
    public boolean checkForReportCard(int studentId) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(SQLConstantQueries.CHECK_FOR_REPORT_CARD);
            statement.setInt(1,studentId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) == 4; //All 4 courses must be graded in order for the report card to generate

        }catch (SQLException e){
            logger.error(e.getMessage());
            return false;
        }finally {
            closeConnection(statement, conn);
        }
    }

    //Fetches and displays the report if it is available
    @Override
    public List<Grade> getReportCard(int studentId){
        if(!checkForReportCard(studentId)){
            logger.error("Report card isn't available as all courses haven't been graded. View marks by course");
            return null;
        }
        return getMarksByCourse(studentId);
    }

    //Fetches marks obtained in all subjects
    @Override
    public List<Grade> getMarksByCourse(int studentId){
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        List<Grade> gradeList = new ArrayList<>();
        try{
            statement = conn.prepareStatement(SQLConstantQueries.GET_REPORT_CARD);
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int courseId = resultSet.getInt(1);
                String courseName = resultSet.getString(2);
                int marks = resultSet.getInt(3);
                Grade grade = new Grade(courseId, courseName, marks);
                gradeList.add(grade);
            }
            return gradeList;

        }catch(SQLException e){
            logger.error(e.getMessage());
            return null;
        }finally {
            closeConnection(statement, conn);
        }
    }
    
    
    //Fetches the average of all marks obtained by a student in all courses (Final %)
    @Override
    public double getFinalPercentage(int studentId){
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;

        try{
            statement = conn.prepareStatement(SQLConstantQueries.GET_FINAL_MARKS);
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                return resultSet.getDouble(1);
            }

            return -1;
        }catch(SQLException e){
            logger.error(e.getMessage());
            return -1;
        }finally {
            closeConnection(statement, conn);
        }
    }
}
