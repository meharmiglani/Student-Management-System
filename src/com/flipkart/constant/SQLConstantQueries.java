package com.flipkart.constant;

public class SQLConstantQueries {
    public static final String CHECK_USER = "SELECT userId FROM User WHERE username = ? AND password = ?";
    public static final String GET_STUDENT_NAME = "SELECT name FROM Student WHERE studentId = ?";
    public static final String GET_CATALOG = "SELECT courseId, name, credits FROM Course";
    public static final String COUNT_OF_STUDENTS = "SELECT countOfStudents FROM Course WHERE courseId = ?";
    public static final String UPDATE_COUNT = "UPDATE Course SET countOfStudents = ? WHERE courseId = ?";
    public static final String ADD_COURSE = "INSERT INTO RegisterCourse(courseId, studentId, studentName, alternate) VALUES(?, ?, ?, ?)";
    public static final String UPDATE_MARKS = "UPDATE RegisterCourse SET marks = ? WHERE studentId = ? AND courseId = ?";
    public static final String DELETE_COURSE = "DELETE FROM RegisterCourse WHERE studentId = ? AND courseId = ?";
    public static final String VIEW_STUDENT_LIST = "SELECT RegisterCourse.studentName, RegisterCourse.studentId " +
                                                    "FROM Professor INNER JOIN RegisterCourse ON Professor.courseId = RegisterCourse.courseId " +
                                                    "WHERE Professor.professorId = ?";
    public static final String REGISTERED_COURSES = "SELECT Course.name, Course.professorId, Course.credits " +
                                                    "FROM RegisterCourse INNER JOIN Course ON RegisterCourse.courseId = Course.courseId " +
                                                    "WHERE RegisterCourse.studentId = ?";
}
