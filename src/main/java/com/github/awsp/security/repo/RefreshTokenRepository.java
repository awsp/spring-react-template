package com.github.awsp.security.repo;

import com.github.awsp.model.User;
import com.github.awsp.security.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findFirstByTokenOrderByExpirationDesc(String token);

    Optional<RefreshToken> findFirstByUserOrderByExpirationDesc(User user);
}