package com.github.awsp.security.exception;

public class RefreshTokenExpiredException extends Exception {
    public RefreshTokenExpiredException() {
        super("Refresh token expired. ");
    }
}