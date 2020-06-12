package com.flipkart.model;

public class Student extends User{
    private int studentId;
    private boolean isScholar;
    String gender;

    public Student(int studentId, String username, String password, String name, String email, boolean isScholar, String gender){
        super(username, password, name, email);
        this.studentId = studentId;
        this.isScholar = isScholar;
        this.gender = gender;
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

    public String getGender() {
        return gender;
    }
}
