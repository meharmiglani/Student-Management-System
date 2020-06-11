package com.flipkart.dao;

import com.flipkart.constant.SQLConstantQueries;
import com.flipkart.model.Result;
import com.flipkart.model.StudentList;
import com.flipkart.utils.DBUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewStudentListDaoImpl implements ViewStudentListDao{
    private static Logger logger = Logger.getLogger(ViewStudentListDaoImpl.class);

    @Override
    public List<StudentList> studentList(int professorId) {
        List<StudentList> list = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;

        try{
            statement = conn.prepareStatement(SQLConstantQueries.VIEW_STUDENT_LIST);
            statement.setInt(1, professorId);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                String name = resultSet.getString(1);
                int studentId = resultSet.getInt(2);
                StudentList student = new StudentList(name, studentId);
                list.add(student);
            }
            return list;

        }catch (SQLException e){
            logger.error(e.getMessage());
            return null;
        }finally {

            //Close the all connections and statements
            try {
                if(statement != null) {
                    statement.close();
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
}
