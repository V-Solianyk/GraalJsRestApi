package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Script {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ScriptStatus status;
    private final LocalDateTime startTime = LocalDateTime.now();
    private Duration processingTime;
    private String body;
    @Lob
    @Column(length = 100000)
    private String stdout;
    @Lob
    @Column(length = 100000)
    private String stderr;

    public enum ScriptStatus {
        EXECUTING,
        COMPLETED,
        STOPPED,
        FAILED,
        QUEUED
    }

    public Script(String body, ScriptStatus status) {
        this.body = body;
        this.status = status;
    }
}
