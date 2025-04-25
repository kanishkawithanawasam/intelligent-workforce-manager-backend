package com.iwm.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiry}")
    private int expiryInMs;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }


    public String createToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .claim("role",role )
                .setExpiration(new Date(new Date().getTime()+expiryInMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getRoleFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    boolean validateToken(String token) {
        try{
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        }catch (SecurityException e){
            System.out.println(e.getMessage());
        }catch (MalformedJwtException e){
            System.out.println(e.getMessage());
        }catch (ExpiredJwtException e){
            System.out.println(e.getMessage());
        }catch (UnsupportedJwtException e){
            System.out.println(e.getMessage());
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

}
