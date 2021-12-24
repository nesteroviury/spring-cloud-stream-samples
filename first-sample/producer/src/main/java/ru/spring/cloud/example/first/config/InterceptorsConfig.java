package ru.spring.cloud.example.first.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import ru.spring.cloud.example.dto.Payload;

@Configuration
public class InterceptorsConfig {
    @Bean
    @GlobalChannelInterceptor(patterns = "source-out-*")
    public ChannelInterceptor sourceInterceptor() {
        return new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                Payload payload = (Payload) message.getPayload();
                //todo: change, convert
                return MessageBuilder.withPayload(payload).build();
            }
        };
    }
}
