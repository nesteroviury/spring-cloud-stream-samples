package ru.spring.cloud.example.first.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.spring.cloud.example.dto.Payload;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ApplicationController {
    private static final String PARTITION_HEADER_KEY = "partitionKey";

    private final StreamBridge streamBridge;

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    public void delegateToSupplier(@RequestBody Payload payload) {
        streamBridge.send("source-out-0", MessageBuilder
                .withPayload(payload)
                .setHeader(PARTITION_HEADER_KEY, payload.getPartition())
                .build());
    }
}