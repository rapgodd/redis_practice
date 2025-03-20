package com.giyeon.redis_practice.service;

import com.giyeon.redis_practice.domain.User;
import com.giyeon.redis_practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RFuture;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class LazyLoadingService {

    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;
    private final RedissonClient redissonClient;

    @Transactional
    public String lazyLoad(){
        String value = redisTemplate.opsForValue().get("key");

        if(value!=null){
            System.out.println(value);
            return value;
        }

        User user = userRepository.find(1L);

        RBucket bucket = redissonClient.getBucket("key");
        RFuture rFuture = bucket.setAsync(user.getName());

        rFuture.thenAccept((result) -> {
            System.out.println("Redis에 저장 완료");
        }).exceptionally((e) -> {
            System.out.println("Redis에 저장 실패");
            return e;
        });

        return user.getName();
    }

}
