package com.flipkart.dao;

import com.flipkart.model.User;

public interface UserDao {
    int checkIdentity(String username, String password);
    String getStudentName(int studentId);
}
