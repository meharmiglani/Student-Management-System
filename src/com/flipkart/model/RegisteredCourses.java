package com.flipkart.model;

public class RegisteredCourses{
    String courseName;
    int professorId;
    private int credits;

    public RegisteredCourses(String courseName, int professorId, int credits) {
        this.courseName = courseName;
        this.professorId = professorId;
        this.credits = credits;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getProfessorId() {
        return professorId;
    }


    public int getCredits() {
        return credits;
    }

}
