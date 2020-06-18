package com.flipkart.dao;

import java.util.List;

import com.flipkart.model.Student;

public interface StudentDao {
    boolean insertStudent(Student student);
    boolean deleteStudent(int studentId);
    boolean updateStudent(int studentId, Student newStudent);
    List<Student> viewAllStudents();
    //void deleteStudentRegistration(int studentId);
    boolean deleteRegisteredCourses(int studentId);
    boolean updateCountsOfCourses(int studentId);
}
