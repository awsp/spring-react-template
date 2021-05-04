package com.github.awsp.security.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 50)
    @Email
    private String username;

    @NotBlank
    @Size(min = 8, max = 50)
    private String password;

    private Set<String> roles;
}