package com.springboot.blog.security;

import com.springboot.blog.exception.BlogApiException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private Integer jwtExpirationInMs;

    // generate token
    public String generateToken(Authentication authentication) {

        String userName = authentication.getName();
        Date currentDate = new Date();
        Date expiredDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        String token = Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        return token;
    }

    // get username from the token
    public String getUsernameFromJWT(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // validate JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            throw new BlogApiException("Invalid JWT signature.");
        } catch (MalformedJwtException ex) {
            throw new BlogApiException("Invalid JWT token.");
        } catch (ExpiredJwtException ex) {
            throw new BlogApiException("Expired JWT token.");
        } catch (UnsupportedJwtException ex) {
            throw new BlogApiException("Unsupported JWT token.");
        } catch (IllegalArgumentException ex) {
            throw new BlogApiException("JWT claims string is empty.");
        }
    }
}
