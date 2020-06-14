package com.flipkart.dao;

import com.flipkart.constant.SQLConstantQueries;
import com.flipkart.utils.CloseConnectionInterface;
import com.flipkart.model.Course;
import com.flipkart.utils.DBUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewRegisteredCoursesDaoImpl implements ViewRegisteredCoursesDao, CloseConnectionInterface {
    private final static Logger logger = Logger.getLogger(ViewRegisteredCoursesDaoImpl.class);
    @Override
    public List<Course> viewRegisteredCourses(int studentId) {
        List<Course> list = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;

        try{
            statement = conn.prepareStatement(SQLConstantQueries.REGISTERED_COURSES);
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                int courseId = resultSet.getInt(1);
                String courseName = resultSet.getString(2);
                Course course = new Course(courseId, courseName);
                list.add(course);
            }
            return list;

        }catch(SQLException e){
            logger.error(e.getMessage());
            return null;
        }finally {
            //Close the all connections and statements
            closeConnection(statement, conn);
        }
    }
}
