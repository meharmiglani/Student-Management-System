package com.flipkart.dao;

import com.flipkart.model.Course;

import java.util.List;

public interface CourseToTeachDao {
    boolean selectCourse(int professorId, int courseId);
    List<Course> viewCoursesAvailableToTeach();
    List<Course> coursesTeaching(int professorId);
}
