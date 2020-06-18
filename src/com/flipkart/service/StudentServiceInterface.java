package com.flipkart.service;

import java.util.List;

import com.flipkart.exception.CannotAddMoreCourseException;
import com.flipkart.exception.CourseLimitExceededException;
import com.flipkart.model.Grade;

public interface StudentServiceInterface {
    void viewCourseCatalog(int catalogId);
    boolean addCourse(int studentId, String studentName, int courseId) throws CourseLimitExceededException, CannotAddMoreCourseException;
    boolean deleteCourse(int studentId, int courseId);
    void viewRegisteredCourses(int studentId);
    void viewMarksByCourse(int studentId);
    void viewReportCard(int studentId);
    boolean displayMarks(List<Grade> gradeList);
    void submitRegistration(int studentId);
    boolean canAddCourse(int studentId);
    boolean checkForRegistration(int studentId);
}
