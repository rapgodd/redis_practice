package com.giyeon.redis_practice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class RedisDto {

    private String major;
    private String name;

}
