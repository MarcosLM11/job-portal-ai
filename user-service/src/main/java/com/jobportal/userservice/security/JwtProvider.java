package com.jobportal.userservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {
    private static final String SECRET_KEY = "clave_secreta_123";
    private static final long EXPIRATION_TIME = 3_600_000L;
    private final SecretKey  secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(Authentication auth, Long userId) {
        var authorities = auth.getAuthorities();
        var roles = authorities
                .stream()
                .map(GrantedAuthority::getAuthority)
                .reduce((a, b) -> a + "," + b)
                .orElse("");
        log.debug("JWT generated for userId={}, email={}, expiresInMs={}", userId, auth.getName(), EXPIRATION_TIME);
        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim("email", auth.getName())
                .claim("authorities", roles)
                .claim("userId", userId)
                .signWith(secretKey)
                .compact();
    }
}
