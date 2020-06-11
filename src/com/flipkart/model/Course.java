package com.flipkart.model;

public class Course {
    private String courseName;
    private int courseId;
    private int professorId;
    private int credits;

    public Course(int courseId, String courseName, int professorId, int credits) {
        this.courseName = courseName;
        this.courseId = courseId;
        this.professorId = professorId;
        this.credits = credits;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getProfessor() {
        return professorId;
    }

    public void setProfessor(int professorId) {
        this.professorId = professorId;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

}
