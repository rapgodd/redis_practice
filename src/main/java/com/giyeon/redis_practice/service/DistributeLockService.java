package com.giyeon.redis_practice.service;

import com.giyeon.redis_practice.repository.UserRepository;
import com.giyeon.redis_practice.domain.User;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class DistributeLockService {

    private final UserRepository userRepository;
    private final RedissonClient redissonClient;

    @Transactional
    public void distributedLock() {

        RLock rl = redissonClient.getLock("user");
        try {
            boolean lock = rl.tryLock(1, TimeUnit.SECONDS);

            if (lock) {
                User user = userRepository.find(1L);
                user.setName("GIYEON CHOI");
                Thread.sleep(20000);

            } else {
                System.out.println("락 잡기 실패.");
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            rl.unlock();
            System.out.println("락 해제");
        }
    }

    @Transactional
    public void distributedSecondThreadLock() {

        RLock rl = redissonClient.getLock("user");
        System.out.println("락 잡기 시도");
        try {
            boolean lock = rl.tryLock(1, TimeUnit.SECONDS);

            if (lock) {
                User user = userRepository.find(1L);
                user.setName("JAMES GIYEON");
            } else {
                System.out.println("락 잡기 실패.");
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            rl.unlock();
            System.out.println("락 해제");
        }
    }


    @Transactional
    public void createUser() {
         User giyeon = new User("giyeon", 1234);
         userRepository.save(giyeon);
    }

}
