package com.flipkart.model;

public class Student extends User{
    private int studentId;
    boolean isScholarship;

    public Student(int studentId, String username, String password, String name, String email) {
        super(username, password, name, email);
        this.studentId = studentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public boolean isScholarship() {
        return isScholarship;
    }

    public void setScholarship(boolean scholarship) {
        isScholarship = scholarship;
    }
}
