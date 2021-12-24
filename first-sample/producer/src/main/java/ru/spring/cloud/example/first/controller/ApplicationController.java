package ru.spring.cloud.example.first.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.spring.cloud.example.dto.Payload;

@RequiredArgsConstructor
@RequestMapping("api")
@RestController
public class ApplicationController {
    private final StreamBridge streamBridge;

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    public void delegateToSupplier(@RequestBody Payload payload) {
        streamBridge.send("source-out-0", payload);
    }
}
