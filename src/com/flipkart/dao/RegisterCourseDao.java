package com.flipkart.dao;

public interface RegisterCourseDao {
    boolean addCourse(int studentId, String studentName, int courseId);
    boolean deleteCourse(int studentId, int courseId);
    int courseLimitCheck(int studentId);
    int getCountOfStudents(int courseId);
    boolean updateStudentCount(int courseId, int count, int mode);
}
