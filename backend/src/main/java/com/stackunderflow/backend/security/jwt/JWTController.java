package com.stackunderflow.backend.security.jwt;

import com.stackunderflow.backend.DTOS.RegisterRequestAuth;
import com.stackunderflow.backend.DTOS.TokenDTO;
import com.stackunderflow.backend.Exception.BadCredentialsException;
import com.stackunderflow.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class JWTController {
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<TokenDTO> register(@RequestBody RegisterRequestAuth registerRequestAuth){
        userService.registerUser(registerRequestAuth);
        return getTokenDTOResponseEntity(JWTAuthenticationRequest.builder()
                .email(registerRequestAuth.getEmail())
                .password(registerRequestAuth.getPassword()).build());
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> getTokenForAuthenticatedUser(@RequestBody JWTAuthenticationRequest authRequest) {
        return getTokenDTOResponseEntity(authRequest);
    }

    private ResponseEntity<TokenDTO> getTokenDTOResponseEntity(JWTAuthenticationRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                TokenDTO token = jwtService.generateToken(authRequest.getEmail());
                return new ResponseEntity<>(token, OK);
            }
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
        return null;
    }
}
