package com.giyeon.redis_practice.Controller;

import com.giyeon.redis_practice.service.DistributeLockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DistributedLockController {

    private final DistributeLockService distributeLockService;


    @PostMapping("/distributed/lock/thread/one")
    public void distributedLockThreadOne(){
        distributeLockService.distributedLock();
    }

    @PostMapping("/distributed/lock/thread/two")
    public void distributedLockThreadTwo(){
        distributeLockService.distributedSecondThreadLock();
    }

    @PostMapping("/user")
    public void createUser(){
        distributeLockService.createUser();
    }


}
