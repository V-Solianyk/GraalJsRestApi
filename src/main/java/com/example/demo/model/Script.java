package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Data
public class Script {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ScriptStatus status;
    private final LocalDateTime startTime = LocalDateTime.now();
    private LocalDateTime processingTime;
    private String body;
    private String stdout; // todo determine data type if it need in general/
    private String stderr; // todo determine data type if it need in general/

    public enum ScriptStatus {
        EXECUTING,
        COMPLETED,
        STOPPED,
        FAILED,
        QUEUE
    }
}
