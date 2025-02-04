package org.bankapp.bankingapp.service.Impl;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.bankapp.bankingapp.entity.User;
import org.bankapp.bankingapp.exceptions.InvalidJwtAuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${security.jwt.secret-key}")
    private String userSecretKey;

    @Value("${security.jwt.refresh-secret-key}")
    private String refreshTokenSecretKey;

    @Value("${security.jwt.expiration-time}")
    private long expirationTime;

    @Value("${security.jwt.refresh-expiration-time}")
    private long refreshExpirationTime;


    // Extract username (subject) from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Generic method to extract specific claims
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            // Try validating with the user secret key first
            return parseTokenWithKey(token, userSecretKey);
        } catch (JwtException jwtException) {
            logger.error("JWT token error: {}", jwtException.getMessage());
            throw new InvalidJwtAuthenticationException("Invalid JWT token");
        }
    }


    private Claims parseTokenWithKey(String token, String secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    // Generate token for User
    public String generateUserToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());
        return generateToken(claims, user.getEmail(), userSecretKey);
    }

    private String generateToken(Map<String, Object> claims, String subject, String secretKey) {
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Date refreshExpirationDate = new Date(System.currentTimeMillis() + refreshExpirationTime);
        User user = (User) userDetails;
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("id", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(refreshExpirationDate)
                .signWith(getSigningKey(refreshTokenSecretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
