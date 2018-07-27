package com.ebiz.rediswebsocket.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.*;

/**
 * created by chengjinyun 2018/7/26
 */
@Configuration
public class RedisConfig {
    /**
     * 注入redis模板
     */
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     *  数据类型为String
     * @return
     */
    @Bean
    public ValueOperations<String, String> redisString(){
        return redisTemplate.opsForValue();
    }


    /**
     * 数据类型为哈希
     * @return
     */
    @Bean
    public HashOperations<String, String , String> redisHash(){
        return redisTemplate.opsForHash();
    }

    /**
     * 数据类型为set
     * @return
     */
    @Bean
    public SetOperations<String, String> redisSet(){
        return redisTemplate.opsForSet();
    }

    /**
     * 数据类型为zset
     * @return
     */
    @Bean
    public ZSetOperations<String, String> redisZset() {
        return redisTemplate.opsForZSet();
    }
}
