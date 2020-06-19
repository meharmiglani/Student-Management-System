package com.flipkart.exception;

public class CannotAddMoreCourseException extends Exception{
	private int courseId;

	public CannotAddMoreCourseException(int courseId) {
		this.courseId = courseId;
	}
	
	public int receiveMessage() {
		return this.courseId;
	}
}
