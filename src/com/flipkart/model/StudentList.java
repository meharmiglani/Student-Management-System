package com.flipkart.model;

public class StudentList {
    private String name;
    private int studentId;
    private String courseName;
    private int marks;

    public StudentList(String name, int studentId, String courseName, int marks) {
        this.name = name;
        this.studentId = studentId;
        this.courseName = courseName;
        this.marks = marks;
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

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}
