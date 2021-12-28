package ru.spring.cloud.example.first.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.*;
import ru.spring.cloud.example.dto.Payload;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ApplicationController {
    private static final int DEFAULT_PARTITION_VALUE = 0;
    private static final String PARTITION_KEY_HEADER_KEY = "partitionKey";

    private final StreamBridge streamBridge;

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    public void delegateToSupplier(@RequestBody Payload payload) {
        int partitionIndex = payload.getPartition() >= 0 ? payload.getPartition() : DEFAULT_PARTITION_VALUE;
        Message<Payload> message = MessageBuilder
                .withPayload(payload)
                .setHeader(PARTITION_KEY_HEADER_KEY, partitionIndex)
                .build();
        streamBridge.send("source-out-0", message);
    }
}
