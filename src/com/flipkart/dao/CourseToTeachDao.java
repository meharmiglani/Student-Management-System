package com.flipkart.dao;

import java.util.List;

import com.flipkart.exception.NoCourseFoundException;
import com.flipkart.model.Course;

public interface CourseToTeachDao {
    boolean selectCourse(int professorId, int courseId) throws NoCourseFoundException;
    List<Course> viewCoursesAvailableToTeach();
    List<Course> coursesTeaching(int professorId);
}
