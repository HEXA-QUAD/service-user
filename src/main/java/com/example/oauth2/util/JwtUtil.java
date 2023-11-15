package com.example.oauth2.util;

import com.example.oauth2.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "COMS656finalprojectUserLoginServiceSecretKeyhopethisisenoughfor256bits"; // Replace with a secure key
    private static final long EXPIRATION_TIME = 86400000; // 24 hours

    public static String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(user.getName())
                .claim("role", user.getRole())
                .claim("email", user.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}

