package com.giyeon.redis_practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublishService {

    private final RedisTemplate<String, String> redisTemplate;

    public void publish(String message){
        redisTemplate.convertAndSend("ExPub", message);
    }

}
