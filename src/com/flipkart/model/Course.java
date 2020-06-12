package com.flipkart.model;

public class Course {
    private String courseName;
    private int courseId;
    private int professorId;
    private String professorName;
    private int credits;
    private int countOfStudents;

    public Course(int courseId, String courseName){
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public Course(int courseId, String courseName, String professorName){
        this.courseId = courseId;
        this.courseName = courseName;
        this.professorName = professorName;
    }

    public Course(int courseId, String courseName, int countOfStudents){
        this.courseId = courseId;
        this.courseName = courseName;
        this.countOfStudents = countOfStudents;
    }

    public Course(String courseName, int courseId, int credits){
        this.courseName = courseName;
        this.courseId = courseId;
        this.credits = credits;
    }

    public Course(int courseId, String courseName, String professorName, int professorId, int credits) {
        this.courseName = courseName;
        this.courseId = courseId;
        this.professorId = professorId;
        this.credits = credits;
        this.professorName = professorName;
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

    public int getCredits() {
        return credits;
    }

    public int getProfessorId() {
        return professorId;
    }

    public String getProfessorName() {
        return professorName;
    }

    public int getCountOfStudents() {
        return countOfStudents;
    }
}
