package ru.spring.cloud.example.first.consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.spring.cloud.example.dto.Payload;

import java.util.function.Consumer;

@Configuration
public class KafkaConsumer {
    @Bean
    public Consumer<Payload> consume() {
        return payload -> System.out.println(payload);
    }
}
