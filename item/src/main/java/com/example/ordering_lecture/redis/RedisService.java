package com.example.ordering_lecture.redis;

import com.example.ordering_lecture.common.ErrorCode;
import com.example.ordering_lecture.common.OrTopiaException;
import com.example.ordering_lecture.item.dto.ItemResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    public void setValues(String key, ItemResponseDto dto) {
        ZSetOperations<String, String> values = redisTemplate.opsForZSet();
        ObjectMapper objectMapper = new ObjectMapper();
        long timeStamp = System.currentTimeMillis();
        try {
            String data = objectMapper.writeValueAsString(dto);
            values.add(key,data,timeStamp*-1);
        }catch (JsonProcessingException e){
            throw new OrTopiaException(ErrorCode.REDIS_ERROR);
        }
    }
    @Transactional(readOnly = true)
    public Set<String> getValues(String key) {
        ZSetOperations<String, String> values = redisTemplate.opsForZSet();
        return values.range(key,0,2);
    }
}