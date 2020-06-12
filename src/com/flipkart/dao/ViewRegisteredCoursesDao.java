package com.flipkart.dao;

import com.flipkart.model.Course;

import java.util.List;

public interface ViewRegisteredCoursesDao {
    List<Course> viewRegisteredCourses(int studentId);
}
