package com.giyeon.redis_practice.service;

import com.giyeon.redis_practice.domain.User;
import com.giyeon.redis_practice.repository.UserRepository;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LazyLoadingService {

    private final RedisClient redisClient;
    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;

    @Transactional
    public String lazyLoad(){
        String value = redisTemplate.opsForValue().get("key");

        if(value!=null){
            System.out.println(value);
            return value;
        }

        User user = userRepository.find(1L);

        StatefulRedisConnection<String, String> connect = redisClient.connect();
        RedisAsyncCommands<String, String> async = connect.async();
        async.set("key", user.getName());
        System.out.println("DB에서 가져온거 리턴");

        return user.getName();
    }

}
