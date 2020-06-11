package com.flipkart.dao;

import com.flipkart.constant.SQLConstantQueries;
import com.flipkart.model.Student;
import com.flipkart.utils.DBUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDaoImpl implements StudentDao{
    private static Logger logger = Logger.getLogger(StudentDaoImpl.class);

    @Override
    public boolean insertStudent(Student student) {
        return false;
    }

    @Override
    public boolean deleteStudent(int studentId) {
        return false;
    }

    @Override
    public boolean updateStudent(int studentId, Student newStudent) {
        return false;
    }
}
