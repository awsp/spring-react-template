package com.github.awsp.security.exception;

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException() {
        super("Username is already taken. ");
    }
}