package com.flipkart.service;

import java.util.List;

import com.flipkart.exception.NoCourseFoundException;
import com.flipkart.model.StudentList;

public interface ProfessorServiceInterface {
    void viewStudentList(int professorId);
    void viewStudentListByCourseId(int professorId, int courseId);
    void selectCourseToTeach(int professorId, int courseId) throws NoCourseFoundException;
    void viewCoursesToTeach();
    void viewCoursesTaught(int professorId);
    void updateStudentMarks(int studentId, int courseId, int marks);
    void displayStudentList(List<StudentList> list);
    boolean checkForRegistration(int studentId);
}
