package com.flipkart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.flipkart.constant.SQLConstantQueries;
import com.flipkart.exception.CannotAddMoreCourseException;
import com.flipkart.exception.CourseLimitExceededException;
import com.flipkart.utils.CloseConnectionInterface;
import com.flipkart.utils.DBUtil;

//Performs all operations when a student adds/deletes a course for registration
public class RegisterCourseDaoImpl implements RegisterCourseDao, CloseConnectionInterface {
    private final static Logger logger = Logger.getLogger(RegisterCourseDaoImpl.class);


    /* Adds a course in student's registration after performing various checks as follows
     * 1. Checks for course existence
     * 2. Checks if the student has already submitted his final registration
     * 3. The student limit for the course isn't exhausted
     * 4. The course limit for the student isn't exhausted (Maximum 4)
     */
    
    @Override
    public boolean addCourse(int studentId, String studentName, int courseId) throws CourseLimitExceededException, CannotAddMoreCourseException{
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        PreparedStatement statement1 = null;

        if(alreadyAddedCourse(studentId, courseId)){
            logger.info("You are already enrolled in this course");
            return false;
        }

        try{
            statement1 = conn.prepareStatement(SQLConstantQueries.CHECK_COURSE_EXISTENCE); //Checks for course existence
            statement1.setInt(1,courseId);
            ResultSet resultSet = statement1.executeQuery();

            if(!resultSet.next()){
                logger.error("The chosen course does not exist");
                return false;
            }

            String courseName = resultSet.getString(1);
            int count = getCountOfStudents(courseId);
            if(count!= -1 && count < 10){
                updateStudentCount(courseId, count, 1);
                statement = conn.prepareStatement(SQLConstantQueries.ADD_STUDENT_COURSE);
                statement.setInt(1, courseId);
                statement.setInt(2, studentId);
                statement.setString(3, courseName);
                statement.setString(4, studentName);
                int rows = statement.executeUpdate();
                return rows == 1;

            }else if(count > 4){
            	throw new CannotAddMoreCourseException(courseId);
            }
            else if(count == -1){
                logger.error("SQL Error");
                return false;
            }else{
                throw new CourseLimitExceededException(courseName);
            }
            
        }catch(SQLException e){
            logger.error(e.getMessage());
            return false;
        }finally {
            closeStatement(statement);
            closeConnection(statement1, conn);
        }
    }

    /*Deletes a course from student's selection after the following checks
     * 1. Student must not have submitted his final registration
     * 2. Student must be enrolled first to delete it
     * 3. Course must exist in the university
     */
    @Override
    public boolean deleteCourse(int studentId, int courseId) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        PreparedStatement statement3 = null;

        try{

            statement3 = conn.prepareStatement(SQLConstantQueries.DELETE_STUDENT_COURSE);
            statement3.setInt(1, studentId);
            statement3.setInt(2, courseId);
            int rows = statement3.executeUpdate();

            if(rows != 1){
                logger.error("You are not enrolled in this course");
                return false;
            }

            int count = getCountOfStudents(courseId);
            return updateStudentCount(courseId, count, 2);

        }catch(SQLException e){
            logger.error(e.getMessage());
            return false;
        }finally {
            closeStatement(statement1);
            closeStatement(statement2);
            closeConnection(statement3, conn);
        }
    }
    
    //Check before "adding" a course to verify how many courses is the student currently enrolled in
    @Override
    public int courseLimitCheck(int studentId){
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;

        try{
            statement = conn.prepareStatement(SQLConstantQueries.CHECK_COURSE_LIMIT);
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }else{
                return -1;
            }

        }catch (SQLException e){
            logger.error(e.getMessage());
            return -1;
        }finally {
            closeConnection(statement, conn);
        }
    }

    //Check to see if a student is already enrolled in the course or not
    @Override
    public boolean alreadyAddedCourse(int studentId, int courseId){
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(SQLConstantQueries.CHECK_ALREADY_ADDED_COURSE);
            statement.setInt(1,studentId);
            statement.setInt(2, courseId);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next() && resultSet.getInt(1) == 1;
        }catch (SQLException e){
            logger.error(e.getMessage());
            return false;
        }
    }

    //Fetches count of students in a particular course
    @Override
    public int getCountOfStudents(int courseId){
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(SQLConstantQueries.COUNT_OF_STUDENTS);
            statement.setInt(1, courseId);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                return result.getInt(1);
            }
            return -1;
        }catch (SQLException e){
            logger.error(e.getMessage());
            return -1;
        }finally {
            closeConnection(statement, conn);
        }
    }

    //Updates the count of students in a course in case of a new addition/deletion
    @Override
    public boolean updateStudentCount(int courseId, int count, int mode) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;

        try{
            if(mode == 1){
                count++;
            }else{
                count--;
            }
            statement = conn.prepareStatement(SQLConstantQueries.UPDATE_COUNT);
            statement.setInt(1, count);
            statement.setInt(2, courseId);
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
