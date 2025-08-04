package com.bws.restgrpcforwarder.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * Utility class for generating JWT tokens.
 * Provides static method to generate JWT tokens with claims and expiration.
 */
public final class JwtTokenProvider {

    // Private constructor to prevent instantiation
    private JwtTokenProvider() {}

    /**
     * Generates a JWT token with the given claims and expiration time.
     * @param clientId the client ID (subject & issuer)
     * @param key the secret key (base64 encoded)
     * @param audience the audience claim
     * @param expirationTimeInMinutes expiration time in minutes
     * @return signed JWT token
     */
    public static String generateToken(String clientId, String key, String audience, long expirationTimeInMinutes) {
        try {
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
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to generate JWT token", e);
        }
    }
}
