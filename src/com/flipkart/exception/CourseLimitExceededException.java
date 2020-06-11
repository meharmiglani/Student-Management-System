package com.flipkart.exception;

public class CourseLimitExceededException extends Exception{
    private String course;

    public CourseLimitExceededException(String course) {
        this.course = course;
    }

    public String getCourse() {
        return course;
    }
}
