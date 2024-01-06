package com.stackunderflow.backend.security.jwt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JWTAuthenticationRequest {
    private String email;
    private String password;
}
