package com.giyeon.redis_practice.service;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class RedisMessageSubscriber implements MessageListener {


    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("Message received: "+message.toString());

    }
}
