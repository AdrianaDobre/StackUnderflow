package com.stackunderflow.backend.security;

import com.stackunderflow.backend.security.jwt.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JWTAuthenticationFilter authenticationFilter;
    private final UserDetailsServiceImpl userDetailsService;

    private static final String[] ONLY_ACCESS_FOR_ADMIN = {""};
    private static final String[] ONLY_ACCESS_FOR_USER = {"/api/badge/save", "/api/answer", "/api/answer/edit/{id}", "/api/answer/delete/{id}",
            "/api/answer/{id}/accept/{suggestionId}", "/api/answer/{id}/like", "/api/answer/{id}/dislike", "/api/answer/deleteLikeOrDislike/{id}", "/api/post",
            "/api/post/edit/{id}", "/api/post/delete/{id}", "api/post/{id}/bestAnswer/{answerId}", "/api/post/{id}/bestAnswer/{answerId}",
            "/api/suggestion", "/api/delete/suggestion/{id}", "/api/postXTopic/save", "/api/topic/save", "/api/user/all", "/api/user/{id}",
            "/api/userXBadge/save", "/api/vote/save"};

    private static final String[] UNSECURED_URLS = {"/api/auth/register", "/api/auth/login", "/api/badge/all", "/api/answer/allComments",
            "/api/answer/{id}", "/api/answer/all", "/api/answer/{id}/suggestions", "/api/answer/{id}/history", "/api/post/all",
            "/api/suggestion/all", "/api/suggestion/{id}", "/api/postXTopic/all", "/api/topic/all", "/api/user/save", "/api/userXBadge/all", "/api/vote/all", "/api/post/{id}"};

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(UNSECURED_URLS).permitAll()
                        .requestMatchers(ONLY_ACCESS_FOR_USER).hasAuthority("USER")
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
