package com.flipkart.service;

public interface RegisterCourseInterface {
    boolean addCourse(int studentId, String studentName, int courseId, String alternate);
    boolean deleteCourse(int studentId, int courseId);
}
