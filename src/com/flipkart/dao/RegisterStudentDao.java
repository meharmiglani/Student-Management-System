package com.flipkart.dao;

import java.util.List;

import com.flipkart.model.Registration;

public interface RegisterStudentDao {
    boolean registerStudent(int studentId);
    boolean checkRegistration(int studentId);
    List<Registration> getRegisteredStudents();
}
