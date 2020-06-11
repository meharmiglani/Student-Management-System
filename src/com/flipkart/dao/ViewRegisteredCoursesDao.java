package com.flipkart.dao;

import com.flipkart.model.RegisteredCourses;

import java.util.List;

public interface ViewRegisteredCoursesDao {
    List<RegisteredCourses> viewRegisteredCourses(int studentId);
}
