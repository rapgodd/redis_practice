package com.giyeon.redis_practice.Controller;

import com.giyeon.redis_practice.service.PublishService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PubSubController {

    private final PublishService publishService;

    @PostMapping("/pub")
    public String publish(){
        publishService.publish("Hello Redis");
        return "published";
    }


}
