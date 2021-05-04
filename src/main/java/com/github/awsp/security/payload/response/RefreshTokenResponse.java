package com.github.awsp.security.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RefreshTokenResponse {
    private String token;
    private String refreshToken;
    private String tokenType;
}