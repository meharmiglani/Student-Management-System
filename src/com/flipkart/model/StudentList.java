package com.flipkart.model;

public class StudentList {
    private String name;
    private int studentId;
    private String courseName;

    public StudentList(String name, int studentId, String courseName) {
        this.name = name;
        this.studentId = studentId;
        this.courseName = courseName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getCourseName() {
        return courseName;
    }
}
