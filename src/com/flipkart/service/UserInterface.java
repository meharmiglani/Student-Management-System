package com.flipkart.service;

import com.flipkart.exception.UserNotFoundException;
import com.flipkart.exception.UsernameAlreadyExistsException;
import com.flipkart.model.User;

public interface UserInterface {
    User addUser(User user) throws UsernameAlreadyExistsException;
    void deleteUser(int userId) throws UserNotFoundException;
    void editUser(int id, User newUser) throws UserNotFoundException;
    void listUsers();
}
