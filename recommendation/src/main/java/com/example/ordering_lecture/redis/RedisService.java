package com.example.ordering_lecture.redis;

import com.example.ordering_lecture.common.ErrorCode;
import com.example.ordering_lecture.common.OrTopiaException;
import com.example.ordering_lecture.recommend.dto.RecommendationRedisData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate redisTemplate;

    public void setValues(Long key, List<RecommendationRedisData> recommendationRedisDatas) {
        ListOperations<Long, String> values = redisTemplate.opsForList();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            for(RecommendationRedisData dto : recommendationRedisDatas){
                String data = objectMapper.writeValueAsString(dto);
                values.rightPush(key, data);
            }
        }catch (JsonProcessingException e){
            throw new OrTopiaException(ErrorCode.REDIS_ERROR);
        }
    }
    @Transactional(readOnly = true)
    public List<String> getValues(Long key) {
        ListOperations<Long, String> values = redisTemplate.opsForList();
        return values.range(key, 0, 2);
    }

    public void flushAll(){
        redisTemplate.getConnectionFactory().getConnection().serverCommands().flushAll();
    }
}