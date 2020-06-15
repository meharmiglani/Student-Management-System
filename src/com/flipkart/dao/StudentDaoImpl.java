package com.flipkart.dao;

import com.flipkart.constant.SQLConstantQueries;
import com.flipkart.model.Student;
import com.flipkart.utils.CloseConnectionInterface;
import com.flipkart.utils.DBUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao, CloseConnectionInterface {
    private static final Logger logger = Logger.getLogger(StudentDaoImpl.class);
    private static final RegisterCourseDaoImpl registerCourseDao = new RegisterCourseDaoImpl();

    @Override
    public boolean insertStudent(Student student) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;

        try{
            statement = conn.prepareStatement(SQLConstantQueries.CREATE_STUDENT);
            statement.setInt(1,student.getId());
            statement.setString(2, student.getName());
            statement.setString(3, student.getEmail());
            statement.setString(4, student.getGender());
            statement.setString(5, "no");
            statement.setDouble(6, student.getScholarshipAmount());
            int row = statement.executeUpdate();
            return row == 1;

        }catch (SQLException e){
            logger.error(e.getMessage());
            return false;
        }finally {
            closeConnection(statement, conn);
        }
    }

    @Override
    public boolean deleteStudent(int studentId) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(SQLConstantQueries.DELETE_STUDENT_TABLE);
            statement.setInt(1, studentId);
            int row = statement.executeUpdate();
            deleteStudentRegistration(studentId);
            if(!updateCountsOfCourses(studentId)){
                logger.error("Could not update count of students");
            }
            if(!deleteRegisteredCourses(studentId)){
                logger.error("Could not delete registered courses of the student");
            }
            return row == 1;
        }catch (SQLException e){
            logger.error(e.getMessage());
            return false;
        }finally {
            closeConnection(statement, conn);
        }
    }

    @Override
    public boolean updateStudent(int studentId, Student student) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(SQLConstantQueries.UPDATE_STUDENT);
            statement.setString(1, student.getName());
            statement.setString(2, student.getEmail());
            statement.setString(3, student.getGender());
            statement.setDouble(4, student.getScholarshipAmount());
            statement.setInt(5, studentId);
            int row = statement.executeUpdate();
            return row == 1;

        }catch (SQLException e){
            logger.error(e.getMessage());
            return false;
        }finally {
            closeConnection(statement, conn);
        }
    }

    @Override
    public List<Student> viewAllStudents() {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        List<Student> studentList = new ArrayList<>();

        try{
            statement = conn.prepareStatement(SQLConstantQueries.GET_ALL_STUDENTS);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                Student student = new Student();
                student.setId(resultSet.getInt(1));
                student.setUsername(resultSet.getString(2));
                student.setName(resultSet.getString(3));
                student.setEmail(resultSet.getString(4));
                student.setGender(resultSet.getString(5));
                student.setRegistrationStatus(resultSet.getString(6));
                student.setScholarshipAmount(resultSet.getDouble(7));
                studentList.add(student);
            }
            return studentList;

        }catch (SQLException e){
            logger.error(e.getMessage());
            return null;
        }finally {
            closeConnection(statement, conn);
        }
    }

    public boolean updateCountsOfCourses(int studentId){
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(SQLConstantQueries.GET_REGISTERED_COURSE_BY_ID);
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                int courseId = resultSet.getInt(1);
                int count = registerCourseDao.getCountOfStudents(courseId);
                registerCourseDao.updateStudentCount(courseId, count, 2);
            }
            return true;
        }catch (SQLException e){
            logger.error(e.getMessage());
            return false;
        }finally {
            closeConnection(statement, conn);
        }
    }

    public boolean deleteRegisteredCourses(int studentId){
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(SQLConstantQueries.DELETE_REGISTERED_COURSES);
            statement.setInt(1, studentId);
            int row = statement.executeUpdate();
            return row == 1;
        }catch (SQLException e){
            logger.error(e.getMessage());
            return false;
        }finally {
            closeConnection(statement, conn);
        }
    }

    public void deleteStudentRegistration(int studentId){
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(SQLConstantQueries.DELETE_STUDENT_REGISTRATION);
            statement.setInt(1, studentId);
            statement.executeUpdate();
        }catch (SQLException e){
            logger.error(e.getMessage());
        }finally {
            closeConnection(statement, conn);
        }
    }
}
