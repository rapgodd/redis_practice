package com.giyeon.redis_practice.service;


import com.giyeon.redis_practice.dto.RedisDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RedisService {

    @Autowired
    @Qualifier("redisIntegerTemplate")
    private RedisTemplate<String, Integer> redisIntegerTemplate;

    @Autowired
    @Qualifier("redisJsonTemplate")
    private RedisTemplate<String, Object> redisJsonTemplate;

    @Autowired
    @Qualifier("redisStringTemplate")
    private RedisTemplate<String, String> redisStringTemplate;

    public void redisString(){
        redisStringTemplate.opsForValue().set("stringKey", "string");
        String string = redisStringTemplate.opsForValue().get("stringKey");
        System.out.println(string);
    }


    public void redisInteger() {
        redisIntegerTemplate.opsForValue().set("integerKey", 1);
        Integer integer1 = redisIntegerTemplate.opsForValue().get("integerKey");

        redisIntegerTemplate.opsForValue().increment("integerKey", 1);
        redisIntegerTemplate.opsForValue().increment("integerKey", 1);
        redisIntegerTemplate.opsForValue().increment("integerKey", 1);

        Integer integer2 = redisIntegerTemplate.opsForValue().get("integerKey");

        System.out.println(integer1);
        System.out.println(integer2);
    }

    public void redisJson() {
        redisJsonTemplate.opsForValue().set("jsonKey", new RedisDto("Computer", "Giyeon"));
        RedisDto redisDto = (RedisDto) redisJsonTemplate.opsForValue().get("jsonKey");
        System.out.println(redisDto);

        redisJsonTemplate.opsForHash().put("hashKey", "major", "Computer");
        redisJsonTemplate.opsForHash().put("hashKey", "name", "Giyeon");
        Map<Object, Object> entries = redisJsonTemplate.opsForHash().entries("hashKey");
        String o = (String)redisJsonTemplate.opsForHash().get("hashKey", "major");
        System.out.println(entries);
        System.out.println(o);
    }
}
