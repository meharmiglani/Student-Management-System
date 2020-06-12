package com.flipkart.dao;

public interface UpdateMarksDao {
    boolean updateStudentMarks(int studentId, int courseId, int marks);
}
