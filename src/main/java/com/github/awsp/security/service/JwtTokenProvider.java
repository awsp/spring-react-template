package com.github.awsp.security.service;

import com.github.awsp.repo.UserRepository;
import com.github.awsp.security.config.JwtProperties;
import com.github.awsp.security.exception.UserNotFoundException;
import com.github.awsp.security.model.RefreshToken;
import com.github.awsp.security.repo.RefreshTokenRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private String secretKey;


    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes());
    }

    public String generateJwtToken(final UserDetails userDetails) {
        return generateJwtTokenFromUsername(userDetails.getUsername());
    }

    public String generateJwtTokenFromUsername(final String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtProperties.getValidityInMs()))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String generateJwtRefreshToken(UserDetails userDetails) throws UserNotFoundException {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepository.findByUsername(userDetails.getUsername()).orElseThrow(UserNotFoundException::new));
        refreshToken.setExpiration(Instant.now().plusMillis(jwtProperties.getRefreshTokenValidityInMs()));
        refreshToken.setToken(UUID.randomUUID().toString());

        RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken);
        return savedRefreshToken.getToken();
    }

    public String getUsernameFromJwtToken(final String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(final String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

}