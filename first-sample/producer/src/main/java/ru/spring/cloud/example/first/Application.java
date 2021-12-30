package ru.spring.cloud.example.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import ru.spring.cloud.example.dto.Payload;

import java.util.function.Consumer;
import java.util.function.Supplier;

@RequiredArgsConstructor
@SpringBootApplication
public class Application {
    private final StreamBridge streamBridge;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    todo: remove
    @Bean
    Consumer<Payload> receive() {
        return s -> System.out.println("Received Sensor: " + s);
    }
}
