package com.flipkart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.flipkart.constant.SQLConstantQueries;
import com.flipkart.model.StudentList;
import com.flipkart.utils.CloseConnectionInterface;
import com.flipkart.utils.DBUtil;

//Performs all operations when a professor views a list of students he teaches
public class ViewStudentListDaoImpl implements ViewStudentListDao, CloseConnectionInterface {
    private static Logger logger = Logger.getLogger(ViewStudentListDaoImpl.class);

    //Fetches a list of all students the professor teaches
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
            	int courseId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int studentId = resultSet.getInt(3);
                String courseName = resultSet.getString(4);
                int marks = resultSet.getInt(5);
                StudentList student = new StudentList(courseId, name, studentId, courseName, marks);
                list.add(student);
            }
            return list;

        }catch (SQLException e){
            logger.error(e.getMessage());
            return null;
        }finally {
            closeConnection(statement, conn);
        }
    }

    //Fetches a list of students by a given courseId
    @Override
    public List<StudentList> studentListByCourseId(int professorId, int courseId) {
        List<StudentList> list = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;

        try{
            statement = conn.prepareStatement(SQLConstantQueries.VIEW_STUDENT_LIST_BY_COURSE_ID);
            statement.setInt(1, professorId);
            statement.setInt(2, courseId);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                String name = resultSet.getString(1);
                int studentId = resultSet.getInt(2);
                String courseName = resultSet.getString(3);
                int marks = resultSet.getInt(4);
                StudentList student = new StudentList(courseId, name, studentId, courseName, marks);
                list.add(student);
            }
            return list;

        }catch (SQLException e){
            logger.error(e.getMessage());
            return null;
        }finally {
            closeConnection(statement, conn);
        }
    }
}
