package com.bws.restgrpcforwarder.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * Utility class for generating JWT tokens.
 * This class provides a method to generate JWT tokens with specified claims and expiration time.
 */
public class JwtTokenProvider {

    public static String generateToken( String clientId, String key,String audience, long expirationTimeInMinutes) {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        SecretKey securityKey = Keys.hmacShaKeyFor(keyBytes);

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTimeInMinutes * 60 * 1000);

        return Jwts.builder()
                .subject(clientId)
                .issuer(clientId)
                .audience().add(audience).and()
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(securityKey, Jwts.SIG.HS512)
                .compact();
    }
}
