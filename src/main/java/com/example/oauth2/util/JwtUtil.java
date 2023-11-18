package com.example.oauth2.util;

import com.example.oauth2.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "COMS656finalprojectUserLoginServiceSecretKeyhopethisisenoughfor256bits"; // Replace with a secure key
    private static final long EXPIRATION_TIME = 86400000; // 24 hours

    public static String generateToken(String name, String email, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(name)
                .claim("role", role)
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}

