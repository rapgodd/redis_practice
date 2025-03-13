package com.giyeon.redis_practice.Controller;

import com.giyeon.redis_practice.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;

    @PostMapping("/string")
    public void redisString(){
        redisService.redisString();
    }


    @PostMapping("/integer")
    public void redisInteger(){
        redisService.redisInteger();
    }


    @PostMapping("/json")
    public void redisJson(){
        redisService.redisJson();
    }


}
