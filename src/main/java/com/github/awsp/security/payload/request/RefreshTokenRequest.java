package com.github.awsp.security.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RefreshTokenRequest {
    @NotBlank
    private String refreshToken;

    @NotBlank
    private String token;
}