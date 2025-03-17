package com.giyeon.redis_practice.service;

import com.giyeon.redis_practice.domain.User;
import com.giyeon.redis_practice.dto.AnswerDto;
import com.giyeon.redis_practice.dto.UserRequestDto;
import com.giyeon.redis_practice.repository.UserRepository;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WriteThroughService {
    private final RedisClient redisClient;
    private final UserRepository userRepository;


    @Transactional
    public AnswerDto writeThrough(UserRequestDto userNameDto) throws InterruptedException {

        String newName = userNameDto.getName();
        int newAge = userNameDto.getAge();

        User updatedUser = User.builder()
                .id(1L)
                .name(newName)
                .age(newAge)
                .build();

        userRepository.update(updatedUser);
        RedisFuture<String> redisFuture = redisClient.connect().async()
                .set("1L", "name:" + newName + " age:" + newAge);


        //Non Blocking으로 DTO미리 생성
        AnswerDto dto = AnswerDto.builder()
                .name(userNameDto.getName())
                .age(userNameDto.getAge())
                .build();


        redisFuture.thenAccept((result) -> {
            if(result.equals("OK")){
                System.out.println("Redis에 저장 완료");
            }else{
                System.out.println("Redis에 저장 실패");
            }
            System.out.println(result);
        });

        return dto;
    }
}
