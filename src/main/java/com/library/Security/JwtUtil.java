package com.library.Security;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "librarysecret";

    public String generateToken(Long userId,String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        // Convert the secret key to a proper Key object
        byte[] keyBytes = Base64.getEncoder().encode(SECRET_KEY.getBytes());
        Key key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());

        // Build and sign the token
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // Token valid for 10 hours
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public Long extractUserId(String token) {
    	Claims claims = extractClaims(token);
        return claims.get("userId", Long.class);
    }
    
    private Claims extractClaims(String token) {
    	byte[] keyBytes = Base64.getEncoder().encode(SECRET_KEY.getBytes());
        Key key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
    
    
}