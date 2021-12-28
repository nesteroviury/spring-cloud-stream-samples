package ru.spring.cloud.example.first.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.messaging.support.ChannelInterceptor;
import ru.spring.cloud.example.first.interceptor.SourceOutDestinationChannelInterceptor;

@Configuration
public class InterceptorsConfig {
    @Bean
    @GlobalChannelInterceptor(patterns = "source-out-*")
    public ChannelInterceptor sourceInterceptor() {
        return new SourceOutDestinationChannelInterceptor();
    }
}
