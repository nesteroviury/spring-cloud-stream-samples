package ru.spring.cloud.example.dto;

import lombok.Data;
@Data
public class Payload {
    private String path;
    private int partition;
}
