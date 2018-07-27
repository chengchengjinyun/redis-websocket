package com.ebiz.rediswebsocket.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * created by chengjinyun 2018/7/26
 */
@Configuration
public class WebSocketConfig {

    /**
     * 自动注册带有@ServerEndpoint注解的组件
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
