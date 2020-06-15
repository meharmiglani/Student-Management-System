package com.flipkart.service;

import com.flipkart.exception.UserNotFoundException;
import com.flipkart.exception.UsernameAlreadyExistsException;
import com.flipkart.model.User;

public interface UserInterface {
    int checkIdentity(String username, String password) throws UserNotFoundException;
    String getStudentName(int studentId) throws UserNotFoundException;
    int getRole(String username, String password);
}
