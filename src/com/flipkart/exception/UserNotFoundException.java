package com.flipkart.exception;

public class UserNotFoundException extends Exception{
    private String user;

    public UserNotFoundException(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }
}
