package com.flipkart.model;

public class Student extends User{
    private int studentId;
    private boolean isScholar;

    public Student(int studentId, String username, String password, String name, String email, boolean isScholar){
        super(username, password, name, email);
        this.studentId = studentId;
        this.isScholar = isScholar;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public boolean isScholarship() {
        return isScholar;
    }

    public void setScholarship(boolean scholar) {
        isScholar = scholar;
    }
}
