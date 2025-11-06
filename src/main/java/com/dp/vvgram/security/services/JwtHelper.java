package com.dp.vvgram.security.services;

import com.dp.vvgram.exceptions.AccessDeniedException;
import com.dp.vvgram.models.Token;
import com.dp.vvgram.repositories.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtHelper {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final int MINUTES = 60;
    private static TokenRepository tokenRepository;

    public JwtHelper(TokenRepository tokenRepository) {
        JwtHelper.tokenRepository = tokenRepository;
    }

    public static String generateToken(String userName) {
        var now = Instant.now();
        return Jwts.builder()
                .subject(userName)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(MINUTES, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String extractUsername(String token) throws AccessDeniedException {
        return getTokenBody(token).getSubject();
    }

    public static Boolean validateToken(String token, UserDetails userDetails) throws AccessDeniedException {
        final String username = extractUsername(token);
        Optional<Token> t = tokenRepository.findByValue(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token) && !isTokenRevoked(token);
    }

    private static Claims getTokenBody(String token) throws AccessDeniedException {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (SignatureException | ExpiredJwtException e) { // Invalid signature or expired token
            throw new AccessDeniedException("Access denied: " + e.getMessage());
        }
    }

    private static boolean isTokenExpired(String token) throws AccessDeniedException {
        Claims claims = getTokenBody(token);
        return claims.getExpiration().before(new Date());
    }

    private static boolean isTokenRevoked(String token) throws AccessDeniedException {
        Optional<Token> t = tokenRepository.findByValue(token);
        if (t.isEmpty()) {
            throw new AccessDeniedException("Token not found");
        }
        return t.get().isRevoked();
    }

    public static void revokeToken(String token) throws AccessDeniedException {
        Optional<Token> t = tokenRepository.findByValueAndExpired(token, false);
        if (t.isEmpty()) {
            throw new AccessDeniedException("Token not found");
        }
        Token existingToken = t.get();
        existingToken.setRevoked(true);
        existingToken.setExpired(true);
        tokenRepository.save(existingToken);
    }
}
