package com.flipkart.dao;

public interface RegisterCourseDao {
    boolean addCourse(int studentId, String studentName, int courseId, String alternate);
    boolean deleteCourse(int studentId, int courseId);
}
