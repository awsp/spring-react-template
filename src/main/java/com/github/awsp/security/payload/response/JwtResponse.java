package com.github.awsp.security.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;

    @Builder.Default
    private String type = "Bearer";

    private String refreshToken;

    private String username;
}