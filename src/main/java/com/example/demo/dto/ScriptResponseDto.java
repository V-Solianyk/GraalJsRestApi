package com.example.demo.dto;

import com.example.demo.model.Script;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ScriptResponseDto {
    @Enumerated(EnumType.STRING)
    private Script.ScriptStatus status;
    private final LocalDateTime startTime = LocalDateTime.now();
    private LocalDateTime processingTime;
    private String body;
    private String stdout; // todo determine data type if it need in general/
    private String stderr; // todo determine data type if it need in general/
}
