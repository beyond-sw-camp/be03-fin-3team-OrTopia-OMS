package com.example.ordering_lecture.feign;

import com.example.ordering_lecture.common.ErrorCode;
import com.example.ordering_lecture.common.OrTopiaException;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

public class MyFeignClientConfig {
    @Bean
    public RequestInterceptor requestInterceptor(RedisTemplate<String, String> redisTemplate2) {
        return requestTemplate -> {
            // Redis 채널과 키를 조합한 전체 키 생성
            String key = "RC:admin@test.com";

            // 키 존재 여부 확인
            boolean hasKey = redisTemplate2.hasKey(key);
            if (hasKey) {
                // 조합한 전체 키로 값 가져오기
                String token = redisTemplate2.opsForValue().get(key);
                requestTemplate.header("Authorization", "Bearer " + token);
            } else {
                throw new OrTopiaException(ErrorCode.REDIS_NOT_FOUND_KEY);
            }
        };
    }
}