package com.flipkart.service;

public interface StudentServiceInterface {
    void viewCourseCatalog();
    boolean addCourse(int studentId, String studentName, int courseId);
    boolean deleteCourse(int studentId, int courseId);
    void viewRegisteredCourses(int studentId);
    void viewMarksByCourse(int studentId);
    void viewReportCard(int studentId);
}
