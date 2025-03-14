package com.giyeon.redis_practice.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public void redisMasterReplica() {

        redisTemplate.opsForValue().set("major", "Computer Science");
        redisTemplate.opsForHash().put("student", "name", "giyeon");
        redisTemplate.opsForList().leftPush("list", "1");

        System.out.println(redisTemplate.opsForValue().get("major"));
        System.out.println(redisTemplate.opsForHash().get("student", "name"));
        System.out.println(redisTemplate.opsForList().leftPop("list"));

    }


}
