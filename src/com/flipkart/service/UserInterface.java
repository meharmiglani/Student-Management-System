package com.flipkart.service;

import com.flipkart.exception.UserNotFoundException;

public interface UserInterface {
    int checkIdentity(String username, String password) throws UserNotFoundException;
    String getStudentName(int studentId) throws UserNotFoundException;
    int getRole(String username, String password);
}
