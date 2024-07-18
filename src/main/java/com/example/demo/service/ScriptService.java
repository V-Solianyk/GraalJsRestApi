package com.example.demo.service;

import com.example.demo.dto.ScriptRequestDto;
import com.example.demo.dto.ScriptResponseDto;
import com.example.demo.model.Script;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface ScriptService {
    String executeScript(ScriptRequestDto body, boolean blocking);

    List<ScriptResponseDto> getScripts(Optional<Script.ScriptStatus> status, Pageable pageable);

    ScriptResponseDto getScriptById(Long id);

    void stopScriptById(Long id);

    void deleteById(Long id);
}
