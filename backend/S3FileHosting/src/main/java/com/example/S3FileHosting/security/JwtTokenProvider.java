package com.example.S3FileHosting.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private long expirationDate;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + expirationDate);

        String token = Jwts.builder().
                setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, getKey())
                .compact();

        return token;
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret));
    }

    public String getUsernameFromJWTToken(String token) {
        String username = Jwts.parser()
                .setSigningKey(getKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return username;
    }

    public boolean validateJwtToken(String token) {
        Jwts.parser()
                .setSigningKey(getKey())
                .parse(token);

        return true;
    }
}
