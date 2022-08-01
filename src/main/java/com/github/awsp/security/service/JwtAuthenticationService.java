package com.github.awsp.security.service;

import com.github.awsp.model.User;
import com.github.awsp.repo.UserRepository;
import com.github.awsp.security.exception.RefreshTokenException;
import com.github.awsp.security.exception.RefreshTokenExpiredException;
import com.github.awsp.security.exception.UserAlreadyExistException;
import com.github.awsp.security.model.RefreshToken;
import com.github.awsp.security.payload.request.LoginRequest;
import com.github.awsp.security.payload.request.RefreshTokenRequest;
import com.github.awsp.security.payload.request.SignupRequest;
import com.github.awsp.security.payload.response.JwtResponse;
import com.github.awsp.security.repo.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationService implements AuthenticationService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwtTokenProvider;

    private static final List<String> AVAILABLE_ROLES = Arrays.asList("USER", "MODERATOR", "ADMIN");
    private static final int DEFAULT_ROLE = 0;

    @Override
    public User signupUser(SignupRequest signupRequest) throws UserAlreadyExistException {
        if (userRepository.existsUserByUsername(signupRequest.getUsername())) {
            throw new UserAlreadyExistException();
        }

        User user = User.builder()
                .username(signupRequest.getUsername())
                .password(encoder.encode(signupRequest.getPassword()))
                .roles(getRoles(signupRequest.getRoles()))
                .build();

        return userRepository.save(user);
    }

    private Set<String> getRoles(Set<String> roles) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        roles.removeIf(role -> !AVAILABLE_ROLES.contains(role));

        if (roles.isEmpty()) {
            roles.add(AVAILABLE_ROLES.get(DEFAULT_ROLE));
        }
        return roles;
    }

    @Override
    public JwtResponse signInUser(final LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtTokenProvider.generateJwtToken(userDetails);

        return userRepository
                .findByUsername(userDetails.getUsername())
                .map(user -> {
                    // We don't want duplicate refresh token
                    RefreshToken refreshToken = refreshTokenRepository.findFirstByUserOrderByExpirationDesc(user)
                            .filter(jwtTokenProvider::isValidRefreshToken)
                            .orElseGet(() -> refreshTokenRepository.save(jwtTokenProvider.generateJwtRefreshToken(user)));

                    return JwtResponse.builder()
                            .username(userDetails.getUsername())
                            .accessToken(jwtToken)
                            .refreshToken(refreshToken.getToken())
                            .build();
                })
                .orElseThrow(() -> new UsernameNotFoundException("Invalid login request"));
    }

    @Override
    public JwtResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws RefreshTokenExpiredException, RefreshTokenException {
        String requestedRefreshToken = refreshTokenRequest.getRefreshToken();
        Optional<RefreshToken> byToken = refreshTokenRepository.findFirstByTokenOrderByExpirationDesc(requestedRefreshToken);
        if (byToken.isPresent() && jwtTokenProvider.isJwtToken(refreshTokenRequest.getToken())) {
            RefreshToken refreshToken = byToken.get();

            // Delete refresh token if it is already expired.
            if (!jwtTokenProvider.isValidRefreshToken(refreshToken)) {
                refreshTokenRepository.delete(refreshToken);
                throw new RefreshTokenExpiredException();
            }

            // Generate new token
            String token = jwtTokenProvider.generateJwtTokenFromUsername(refreshToken.getUser().getUsername());
            return JwtResponse.builder()
                    .username(refreshToken.getUser().getUsername())
                    .accessToken(token)
                    .refreshToken(requestedRefreshToken)
                    .build();
        } else {
            throw new RefreshTokenException(requestedRefreshToken, "Invalid refresh token or auth token. ");
        }
    }

}