package com.flipkart.dao;

import com.flipkart.constant.SQLConstantQueries;
import com.flipkart.model.RegisteredCourses;
import com.flipkart.utils.DBUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewRegisteredCoursesDaoImpl implements ViewRegisteredCoursesDao{
    private static Logger logger = Logger.getLogger(ViewRegisteredCoursesDaoImpl.class);
    @Override
    public List<RegisteredCourses> viewRegisteredCourses(int studentId) {
        List<RegisteredCourses> list = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;

        try{
            statement = conn.prepareStatement(SQLConstantQueries.REGISTERED_COURSES);
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                String name = resultSet.getString(1);
                int professorId = resultSet.getInt(2);
                int credits = resultSet.getInt(3);
                RegisteredCourses registeredCourses = new RegisteredCourses(name, professorId, credits);
                list.add(registeredCourses);
            }

            return list;

        }catch(SQLException e){
            logger.error("Could not fetch list");
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
