package com.ebiz.rediswebsocket.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

/**
 * created by chengjinyun 2018/7/26
 */
@RestController
public class testRedis {
    private static Logger logger = LoggerFactory.getLogger(testRedis.class);

    @Autowired
    private ValueOperations<String, String> stringRedisTemplate;

    @RequestMapping("/test/login")
    public String test1(String username, String password) throws UnsupportedEncodingException {
        logger.info(username+password);
        stringRedisTemplate.set(username, password);
        return "登录成功";
    }
}
