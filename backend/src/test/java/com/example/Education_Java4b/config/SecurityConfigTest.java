package com.example.Education_Java4b.config;

import com.example.Education_Java4b.config.jwt.JwtAuthenticationEntryPoint;
import com.example.Education_Java4b.config.jwt.JwtAuthenticationFilter;
import com.example.Education_Java4b.config.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CorsFilter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecurityConfigTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private CorsFilter corsFilter;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void jwtAuthenticationFilter() {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(authenticationManager, jwtTokenProvider);
        assertNotNull(filter);
    }

    @Test
    void passwordEncoder() {
        assertNotNull(passwordEncoder);
    }
}