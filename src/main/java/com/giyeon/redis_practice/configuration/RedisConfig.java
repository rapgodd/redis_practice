package com.giyeon.redis_practice.configuration;

import com.giyeon.redis_practice.service.RedisMessageSubscriber;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String masterHost;

    @Value("${spring.data.redis.port}")
    private int masterPort;

    @Value("${spring.data.redis.replica.host}")
    private String replicaHost;

    @Value("${spring.data.redis.replica.port}")
    private int replicaPort;

    @Bean
    public LettuceConnectionFactory redisConn(){
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(masterHost, masterPort));
    }


    /**
     * Redis Template
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(){
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConn());

        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);
        template.setHashValueSerializer(stringSerializer);

        return template;
    }

////////////////////////////

    /**
     * RedissonClient
     */
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + masterHost + ":" + masterPort)
                .setPassword(null);
        return Redisson.create(config);
    }

////////////////////////////

    /**
     * PUB/SUB
     */
    @Bean
    public ChannelTopic channelTopic(){
        return new ChannelTopic("ExampleTopic");
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter(RedisMessageSubscriber subscriber){
        return new MessageListenerAdapter(subscriber);
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerCOntainer(
            RedisConnectionFactory connectionFactory,
            MessageListenerAdapter messageListenerAdapter,
            ChannelTopic channelTopic){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(messageListenerAdapter, channelTopic);
        return container;
    }


}
