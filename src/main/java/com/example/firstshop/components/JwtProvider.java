package com.example.firstshop.components;


import com.example.firstshop.database.User;
import com.example.firstshop.exception.ListExceptions;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.InvalidClassException;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class JwtProvider {

    private final SecretKey jwtAccessKey;
    private final SecretKey jwtRefreshKey;
    private final Date jwtAccessExpiration;
    private final Date jwtRefreshExpiration;

    public JwtProvider(
            @Value("${application.security.jwt.access-key.value}") String jwtAccessSecret,
            @Value("${application.security.jwt.refresh-key.value}") String jwtRefreshSecret,
            @Value("${application.security.jwt.access-key.expiration-time}") Integer jwtAccessExpiration,
            @Value("${application.security.jwt.refresh-key.expiration-time}") Integer jwtRefreshExpiration
    ) {
        this.jwtAccessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.jwtRefreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(jwtAccessExpiration)
                .atZone(ZoneId.systemDefault()).toInstant();
        final Instant refreshExpirationInstant = now.plusMinutes(jwtRefreshExpiration)
                .atZone(ZoneId.systemDefault()).toInstant();
        this.jwtAccessExpiration = Date.from(accessExpirationInstant);
        this.jwtRefreshExpiration = Date.from(refreshExpirationInstant);

    }

    public String generateAccessToken(@NonNull User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(jwtAccessExpiration)
                .signWith(jwtAccessKey)
                .claim("roles", user.getRoles())
                .claim("username", user.getUsername())
                .compact();
    }

    public String generateRefreshToken(@NonNull User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(jwtRefreshExpiration)
                .signWith(jwtRefreshKey)
                .compact();
    }

    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            if (secret == jwtAccessKey) {
                Jwts.parserBuilder()
                        .setSigningKey(secret)
                        .build()
                        .parseClaimsJws(token);
            } else if (secret == jwtRefreshKey) {
                Jwts.parserBuilder()
                        .setSigningKey(secret)
                        .build();
            } else {
                throw new InvalidClassException("Wrong secret key");
            }
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    public boolean validateAccessToken(@NonNull String token, @NonNull String email) {
        if (!email.equals(getAccessClaims(token).getSubject())) {
            //#TODO: Change to correct exception;
            throw new ListExceptions.UserAuthenticationException("Wrong email");
        }
        return validateToken(token, jwtAccessKey);
    }

    public boolean validateRefreshToken(@NonNull String token) {
        return validateToken(token, jwtRefreshKey);
    }

    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessKey);
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, jwtRefreshKey);
    }

}

