package com.github.awsp.security.service;

import com.github.awsp.model.User;
import com.github.awsp.security.payload.response.JwtResponse;
import com.github.awsp.security.exception.UserAlreadyExistException;
import com.github.awsp.security.payload.request.LoginRequest;
import com.github.awsp.security.payload.request.SignupRequest;

public interface AuthenticationService {
    User signupUser(SignupRequest signupRequest) throws UserAlreadyExistException;
    JwtResponse signInUser(LoginRequest loginRequest);
}