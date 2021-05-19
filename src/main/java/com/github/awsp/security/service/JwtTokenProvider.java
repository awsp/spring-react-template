package com.github.awsp.security.service;

import com.github.awsp.model.User;
import com.github.awsp.security.config.JwtProperties;
import com.github.awsp.security.model.RefreshToken;
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

    private String secretKey;


    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes());
    }

    /**
     * Generate JWT token from UserDetails
     *
     * @param userDetails userDetails
     * @return JWT token
     */
    public String generateJwtToken(final UserDetails userDetails) {
        return generateJwtTokenFromUsername(userDetails.getUsername());
    }

    /**
     * Generate JWT token a username
     *
     * @param username username
     * @return token in String
     */
    public String generateJwtTokenFromUsername(final String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtProperties.getValidityInMs()))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    /**
     * Generate a Base64-based token for refresh token
     *
     * @param user the User model
     * @return refresh token object
     */
    public RefreshToken generateJwtRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiration(Instant.now().plusMillis(jwtProperties.getRefreshTokenValidityInMs()));
        refreshToken.setToken(Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes()));

        return refreshToken;
    }

    /**
     * Get username from JWT token
     *
     * @param token JWT token
     * @return username
     */
    public String getUsernameFromJwtToken(final String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Validate JWT token and it must not expire
     *
     * @param jwtToken JWT token to be examined
     * @return true if valid, false if invalid
     */
    public boolean isValidJwtToken(final String jwtToken) {
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

    /**
     * Simply validate if a given jwt token is valid regardless if it has expired or not.
     *
     * @param jwtToken JWT token to be examined
     * @return true if valid, false if invalid
     */
    public boolean isJwtToken(final String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken);
            claims.getBody().getSubject();
            return true;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return false;
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    /**
     * Validate a given refresh token is valid and not expired
     *
     * @param token refresh token
     * @return true for valid, false for invalid
     */
    public boolean isValidRefreshToken(RefreshToken token) {
        return token.getExpiration().compareTo(Instant.now()) >= 0;
    }
}