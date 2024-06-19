package com.example.Education_Java4b.config.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtKeyProvider {

    public SecretKey getJwtSecretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
}