package com.example.Education_Java4b.services;

import com.example.Education_Java4b.config.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthenticationService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public boolean authenticate(String token) {
        return jwtTokenProvider.validateToken(token);
    }
}