package com.flipkart.dao;

import com.flipkart.model.User;

public interface UserDao {
    int checkIdentity(String username, String password);
    String getStudentName(int studentId);
    String getRole(String username, String password);
    boolean createUser(User user);
    String getRoleById(int userId);
    boolean deleteUser(int userId);
}
