package com.giyeon.redis_practice.controller;

import com.giyeon.redis_practice.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;

    @PostMapping("/master/replica")
    public void redisString(){
        redisService.redisMasterReplica();
    }


}
