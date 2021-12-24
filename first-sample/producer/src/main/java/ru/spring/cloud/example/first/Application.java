package ru.spring.cloud.example.first;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.spring.cloud.example.dto.Payload;

@RequiredArgsConstructor
@RestController
@SpringBootApplication
public class Application {
    private final StreamBridge streamBridge;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void delegateToSupplier(Payload payload) {
        streamBridge.send("source-out-0", payload);
    }
}
