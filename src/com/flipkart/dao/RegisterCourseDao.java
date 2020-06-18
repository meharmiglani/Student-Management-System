package com.flipkart.dao;

import com.flipkart.exception.CourseLimitExceededException;

public interface RegisterCourseDao {
    boolean addCourse(int studentId, String studentName, int courseId) throws CourseLimitExceededException;
    boolean deleteCourse(int studentId, int courseId);
    int courseLimitCheck(int studentId);
    int getCountOfStudents(int courseId);
    boolean alreadyAddedCourse(int studentId, int courseId);
    boolean updateStudentCount(int courseId, int count, int mode);
}
