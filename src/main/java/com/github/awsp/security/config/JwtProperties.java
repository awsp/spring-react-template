package com.github.awsp.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secretKey = "secret";

    private long validityInMs = 1800000;  // 1/2 hour

    private long refreshTokenValidityInMs = 259200000; // 3 days

}