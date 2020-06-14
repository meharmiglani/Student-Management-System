package com.flipkart.dao;

import com.flipkart.constant.SQLConstantQueries;
import com.flipkart.utils.CloseConnectionInterface;
import com.flipkart.utils.DBUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateMarksDaoImpl implements UpdateMarksDao, CloseConnectionInterface {
    private final static Logger logger = Logger.getLogger(UpdateMarksDaoImpl.class);

    @Override
    public boolean updateStudentMarks(int studentId, int courseId, int marks){

        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;

        try{
            statement = conn.prepareStatement(SQLConstantQueries.UPDATE_MARKS);
            statement.setInt(1, marks);
            statement.setInt(2, studentId);
            statement.setInt(3, courseId);
            int row = statement.executeUpdate();

            return row == 1;
        }catch (SQLException e){
            logger.info(e.getMessage());
            return false;
        }finally {
            closeConnection(statement, conn);
        }
    }
}
