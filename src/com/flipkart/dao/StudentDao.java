package com.flipkart.dao;

import com.flipkart.model.Student;

import java.util.List;

public interface StudentDao {
    boolean insertStudent(Student student);
    boolean deleteStudent(int studentId);
    boolean updateStudent(int studentId, Student newStudent);
    List<Student> viewAllStudents();
}
