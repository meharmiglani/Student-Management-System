package com.flipkart.exception;

public class UsernameAlreadyExistsException extends Exception{
    private String username;

    public UsernameAlreadyExistsException(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
