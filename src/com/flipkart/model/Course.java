package com.flipkart.model;

public class Course {
    private String courseName;
    private int courseId;
    private String professor;
    private int credits;

    public Course(String courseName, int courseId, String professor, int credits, int semester) {
        this.courseName = courseName;
        this.courseId = courseId;
        this.professor = professor;
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

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

}
