package com.example.Education_Java4b.config.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtKeyProvider {

    @Value("${app.jwtSecret}")
    private String jwtSecret;
//    public SecretKey getJwtSecretKey() {
//        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
//    }
//}

    private static final Logger logger = LoggerFactory.getLogger(JwtKeyProvider.class);

    public SecretKey getJwtSecretKey() {
        logger.info("Using JWT secret key: {}", jwtSecret);
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
}