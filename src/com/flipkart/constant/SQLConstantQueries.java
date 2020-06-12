package com.flipkart.constant;

public class SQLConstantQueries {
    public static final String CHECK_USER = "SELECT userId FROM User WHERE username = ? AND password = ?";
    public static final String GET_ROLE = "SELECT role FROM User WHERE username = ? AND password = ?";
    public static final String GET_STUDENT_NAME = "SELECT name FROM Student WHERE studentId = ?";
    public static final String GET_CATALOG = "SELECT courseId, name, credits FROM Course ORDER BY courseId ASC";
    public static final String CHECK_COURSE_EXISTENCE = "SELECT name FROM Course WHERE courseId = ?";
    public static final String COUNT_OF_STUDENTS = "SELECT countOfStudents FROM Course WHERE courseId = ?";
    public static final String UPDATE_COUNT = "UPDATE Course SET countOfStudents = ? WHERE courseId = ?";
    public static final String ADD_COURSE = "INSERT INTO RegisterCourse(courseId, studentId, courseName, studentName, alternate) VALUES(?, ?, ?, ?, ?)";
    public static final String UPDATE_MARKS = "UPDATE RegisterCourse SET marks = ? WHERE studentId = ? AND courseId = ?";
    public static final String DELETE_COURSE = "DELETE FROM RegisterCourse WHERE studentId = ? AND courseId = ?";
    public static final String VIEW_STUDENT_LIST = "SELECT RegisterCourse.studentName, RegisterCourse.studentId, RegisterCourse.courseName " +
                                                    "FROM Professor INNER JOIN RegisterCourse ON Professor.courseId = RegisterCourse.courseId " +
                                                    "WHERE Professor.professorId = ?";
    public static final String REGISTERED_COURSES = "SELECT RegisterCourse.courseId, RegisterCourse.courseName, Professor.name " +
                                                    "FROM RegisterCourse INNER JOIN Professor ON Professor.courseId = RegisterCourse.courseId " +
                                                    "WHERE RegisterCourse.studentId = ?";
    public static final String VIEW_STUDENT_LIST_BY_COURSE_ID = "SELECT DISTINCT RegisterCourse.studentName, RegisterCourse.studentId, RegisterCourse.courseName " +
                                                                "FROM Professor INNER JOIN RegisterCourse ON Professor.courseId = RegisterCourse.courseId " +
                                                                "WHERE Professor.professorId = ? AND RegisterCourse.courseId = ?";
    public static final String VIEW_AVAILABLE_COURSE_LIST_TO_TEACH = "SELECT courseId, name FROM Course WHERE professorId = ?";
    public static final String SELECT_COURSE_TO_TEACH_PROFESSOR = "UPDATE Course SET professorId = ? WHERE courseId = ? AND professorId = ?";
    public static final String VIEW_COURSES_TAUGHT = "SELECT courseId, name, countOfStudents FROM Course WHERE professorId = ?";
    public static final String CHECK_FOR_REPORT_CARD = "SELECT COUNT(*) FROM RegisterCourse WHERE marks IS NOT NULL AND studentId = ?";
    public static final String GET_REPORT_CARD = "SELECT courseId, courseName, marks FROM RegisterCourse WHERE studentId = ?";
}
