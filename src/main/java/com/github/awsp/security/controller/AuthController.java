package com.github.awsp.security.controller;

import com.github.awsp.model.User;
import com.github.awsp.security.exception.UserAlreadyExistException;
import com.github.awsp.security.payload.request.LoginRequest;
import com.github.awsp.security.payload.request.SignupRequest;
import com.github.awsp.security.payload.response.JwtResponse;
import com.github.awsp.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;


    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authenticationService.signInUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    // TODO: disable when not needed
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest) {
        try {
            User user = authenticationService.signupUser(signupRequest);
            if (user != null) {
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to process");
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}