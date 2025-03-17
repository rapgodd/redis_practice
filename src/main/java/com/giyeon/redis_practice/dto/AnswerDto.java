package com.giyeon.redis_practice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerDto {

    private String name;
    private int age;

}
