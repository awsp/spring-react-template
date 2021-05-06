package com.github.awsp.security.exception;

public class RefreshTokenException extends Exception {
    public RefreshTokenException(String token, String errorMessage) {
        super(String.format("Refresh token: %s: %s", token, errorMessage));
    }
}