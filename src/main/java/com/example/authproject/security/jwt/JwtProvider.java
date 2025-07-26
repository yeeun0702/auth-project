package com.example.authproject.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }


    // JWT 토큰 생성
    public String generateToken(String username, List<String> roles) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration); // 현재 시간 + 만료 시간

        return Jwts.builder()
            .setSubject(username)
            .claim("roles", roles)
            .setIssuedAt(now)                 // 발급 시간
            .setExpiration(expiry)            // 만료 시간
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }


    // JWT 토큰의 유효성 검사 (서명 및 만료 여부 확인)
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)           // 서명 검증을 위한 키 설정
                .build()
                .parseClaimsJws(token);       // 토큰 파싱 시 오류 없으면 유효
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // 서명 오류, 만료, 포맷 오류 등
            return false;
        }
    }


    // JWT 토큰에서 username(subject) 추출
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }


    // JWT 토큰에서 roles claim 추출
    public List<String> getRoles(String token) {
        return getClaims(token).get("roles", List.class);
    }

    // JWT에서 Claims (payload) 추출
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody(); // Header와 Signature는 무시하고 Payload만 가져옴
    }
}
