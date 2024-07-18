package com.example.demo.dto;

import com.example.demo.model.Script;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScriptResponseDto {
    @Enumerated(EnumType.STRING)
    private Script.ScriptStatus status;
    private final LocalDateTime startTime = LocalDateTime.now();
    private Duration processingTime;
    private String body;
    private String stdout;
    private String stderr;
}
