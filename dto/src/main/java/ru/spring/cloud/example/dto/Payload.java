package ru.spring.cloud.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Payload implements Serializable {
    private static final long serialVersionUID = 1L;

    private String path;
    private int partition;
}
