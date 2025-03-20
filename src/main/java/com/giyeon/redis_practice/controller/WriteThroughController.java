package com.giyeon.redis_practice.controller;

import com.giyeon.redis_practice.dto.AnswerDto;
import com.giyeon.redis_practice.dto.UserRequestDto;
import com.giyeon.redis_practice.service.WriteThroughService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class WriteThroughController {

    private final WriteThroughService writeThroughService;

    @PostMapping("/writeThrough")
    public AnswerDto writeThrough(@RequestBody UserRequestDto userNameDto) throws InterruptedException, IOException {
        return writeThroughService.writeThroughRedisson(userNameDto);
    }

}

