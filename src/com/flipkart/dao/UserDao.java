package com.flipkart.dao;

import com.flipkart.model.User;

public interface UserDao {
    int checkIdentity(String username, String password);
    String getStudentName(int studentId);
    int getRole(String username, String password);
    boolean createUser(User user);
    int getRoleById(int userId);
    boolean deleteUser(int userId);
    boolean editUser(int userId, User user);
}
