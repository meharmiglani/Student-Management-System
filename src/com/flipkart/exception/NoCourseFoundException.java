package com.flipkart.exception;

public class NoCourseFoundException extends Exception{
	private int courseId;

	public NoCourseFoundException(int courseId) {
		this.courseId = courseId;
	}
	
	public String getMessage(){
		return "Course " + courseId + " does not exist.";
	}
}
