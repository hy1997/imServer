package com.yh.imserver.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author wangchen
 * @create 2019/12/14/9:59
 * WebSocket 配置类
 */
@Configuration
public class WebSocketConfig {
    /**
     * 如果使用的是springboot 内置的服务器需要提供此Bean对象
     * 如果使用自己配置的可以不提供
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}