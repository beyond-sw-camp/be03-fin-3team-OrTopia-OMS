package com.example.ordering_lecture.securities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Integer expiration;

    public String createToken(String email, String role){
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role",role);
        Date now = new Date();
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setClaims(claims);
        jwtBuilder.setIssuedAt(now);
        jwtBuilder.setExpiration(new Date(now.getTime()+expiration*60*1000L));
        jwtBuilder.signWith(SignatureAlgorithm.HS256,secretKey);
        return jwtBuilder.compact();
    }
}
