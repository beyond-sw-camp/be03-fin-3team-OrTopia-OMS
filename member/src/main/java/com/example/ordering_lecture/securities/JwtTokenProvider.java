package com.example.ordering_lecture.securities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtTokenProvider {

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.token.access-expiration-time}")
    private long accessExpirationTime;

    @Value("${jwt.token.refresh-expiration-time}")
    private long refreshExpirationTime;

    public String createAccessToken(String email, String role) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role",role);
        Date now = new Date();
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setClaims(claims);
        jwtBuilder.setIssuedAt(now);
        jwtBuilder.setExpiration(new Date(now.getTime() + accessExpirationTime));
        jwtBuilder.signWith(SignatureAlgorithm.HS256,secretKey);
        return jwtBuilder.compact();
    }

    public String createRefreshToken(String email, String role) {
        Date now = new Date();
        String refreshToken = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshExpirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .claim("role", role)
                .compact();

        // Redis에 리프레시 토큰 저장 (이메일을 키로 사용)
        String redisKey = "RT:" + email;
        redisTemplate.opsForValue().set(redisKey, refreshToken, refreshExpirationTime, TimeUnit.MILLISECONDS);

        return refreshToken;
    }

}
