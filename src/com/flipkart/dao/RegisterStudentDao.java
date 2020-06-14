package com.flipkart.dao;

import com.flipkart.model.Registration;

import java.util.List;

public interface RegisterStudentDao {
    boolean registerStudent(int studentId);
    boolean checkRegistration(int studentId);
    List<Registration> getRegisteredStudents();
}
