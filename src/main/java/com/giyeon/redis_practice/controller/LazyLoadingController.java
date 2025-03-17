package com.giyeon.redis_practice.controller;

import com.giyeon.redis_practice.service.LazyLoadingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LazyLoadingController {

    private final LazyLoadingService lazyLoadingService;


    @GetMapping("/lazy")
    public String lazyLoadRedis(){
        String answer = lazyLoadingService.lazyLoad();
        return answer;
    }

}
