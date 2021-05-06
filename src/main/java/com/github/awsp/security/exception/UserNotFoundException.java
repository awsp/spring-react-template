package com.github.awsp.security.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("User does not exist! ");
    }
}