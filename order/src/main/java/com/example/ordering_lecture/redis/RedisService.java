package com.example.ordering_lecture.redis;

import com.example.ordering_lecture.common.ErrorCode;
import com.example.ordering_lecture.common.OrTopiaException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate redisTemplate;
    private final RedisTemplate redisTemplate2;

    public void setValues(String email, String tid) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(email,tid);
    }
    @Transactional(readOnly = true)
    public String getValues(String key) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(key);
    }

    @Transactional(readOnly = true)
    public int getValuesItemCount(Long key) {
        ValueOperations values = redisTemplate2.opsForValue();
        return Integer.parseInt((String) values.get(key));
    }

    public void setItemQuantity(Long id, int count) {
        ValueOperations values = redisTemplate2.opsForValue();
        values.set(id,String.valueOf(count));
    }
}