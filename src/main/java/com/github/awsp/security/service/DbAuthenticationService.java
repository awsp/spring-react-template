package com.github.awsp.security.service;

import com.github.awsp.model.User;
import com.github.awsp.repo.UserRepository;
import com.github.awsp.security.exception.UserAlreadyExistException;
import com.github.awsp.security.exception.UserNotFoundException;
import com.github.awsp.security.payload.request.LoginRequest;
import com.github.awsp.security.payload.request.SignupRequest;
import com.github.awsp.security.payload.response.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DbAuthenticationService implements AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final JwtTokenService jwtTokenService;

    @Override
    public User signupUser(SignupRequest signupRequest) throws UserAlreadyExistException {
        if (userRepository.existsUserByUsername(signupRequest.getUsername()))  {
            throw new UserAlreadyExistException();
        }

        User user = User.builder()
                .username(signupRequest.getUsername())
                .password(encoder.encode(signupRequest.getPassword()))
                .build();

        return userRepository.save(user);
    }

    @Override
    public JwtResponse signInUser(final LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        try {
            String jwtToken = jwtTokenService.generateJwtToken(userDetails);
            String refreshToken = jwtTokenService.generateJwtRefreshToken(userDetails);

            return JwtResponse.builder()
                    .username(userDetails.getUsername())
                    .token(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (UserNotFoundException unfe) {
            // TODO: handle this
        }

        return null;
    }
}