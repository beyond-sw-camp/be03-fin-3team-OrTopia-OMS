package com.example.ordering_lecture.securities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class JwtGlobalFilter implements GlobalFilter {
    @Value("${jwt.secretKey}")
    private String secretKey;

    private final List<String> allowUrl = Arrays.asList("/member/create","/doLogin","/items","/item/*/image");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String reqUri = request.getURI().getPath();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean isAllowed = allowUrl.stream().anyMatch(uri -> antPathMatcher.match(reqUri,uri));
        if(isAllowed){
            return chain.filter(exchange);
        }
        String bearerToken = request.getHeaders().getFirst("Authorization");
        try {
            if(bearerToken != null){
                if(!bearerToken.substring(0,7).equals("Bearer ")){
                    throw new IllegalArgumentException("token의 형식이 맞지않습니다.");
                }
                String token = bearerToken.substring(7);
                Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
                String email = claims.getSubject();
                String role = claims.get("role",String.class);
                exchange.getRequest()
                        .mutate()
                        .header("myEmail",email)
                        .header("myRole",role)
                        .build();
                exchange = exchange.mutate().request(request).build();
            }else{
                throw new RuntimeException("token is empty");
            }
        }catch (Exception e){
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }
}
