package com.pm.authservice.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
//    private final Key secretKey;
    private final SecretKey secretKey;
//    public JwtUtil(@Value("${jwt.secret}") String secret) {
//        byte[] keyBytes = Base64.getDecoder()
//                .decode(secret.getBytes(StandardCharsets.UTF_8));
//        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
//    }


    public JwtUtil(@Value("${jwt.secret}") String secret) {
        SecretKey key;
        try {
            // Attempt to decode as Base64 (common for JWT secrets)
            byte[] keyBytes = Base64.getDecoder().decode(secret.getBytes(StandardCharsets.UTF_8));
            key = Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            // Fallback: If not Base64, use raw bytes.
            // NOTE: Secret must be at least 32 characters long for HS256
            key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        }
        this.secretKey = key;
    }

    public String generateToken(String email, String role) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(secretKey)
                .compact();
    }

    public void validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) secretKey)
                    .build()
                    .parseSignedClaims(token);
        } catch (SignatureException e) {
            throw new JwtException("Invalid JWT signature");
        } catch (JwtException e) {
            throw new JwtException("Invalid JWT");
        }
    }

}
