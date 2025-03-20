package com.giyeon.redis_practice.service;

import com.giyeon.redis_practice.domain.User;
import com.giyeon.redis_practice.dto.AnswerDto;
import com.giyeon.redis_practice.dto.UserRequestDto;
import com.giyeon.redis_practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RFuture;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;

@Service
@RequiredArgsConstructor
public class WriteThroughService {
    private final UserRepository userRepository;
    private final RedissonClient redissonClient;


    @Transactional
    public AnswerDto writeThroughRedisson(UserRequestDto userNameDto) throws IOException, InterruptedException {

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String newName = userNameDto.getName();
        int newAge = userNameDto.getAge();

        User updatedUser = User.builder()
                .id(1L)
                .name(newName)
                .age(newAge)
                .build();

        userRepository.update(updatedUser);
        RFuture<Void> rFuture = redissonClient.getBucket("1L").setAsync("name:" + newName + " age:" + newAge);

        rFuture.thenAccept((result) -> {
            try {
                bw.write("Redis에 저장 완료");
                bw.flush();
                bw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).exceptionally((e) -> {
            try {
                bw.write("Redis에 저장 실패");
                bw.flush();
                bw.close();
            } catch (IOException ioException) {
                throw new RuntimeException(ioException);
            }
            return null;
        });

        Thread.sleep(2000);

        AnswerDto dto = AnswerDto.builder()
                .name(userNameDto.getName())
                .age(userNameDto.getAge())
                .build();

        return dto;


    }
}

