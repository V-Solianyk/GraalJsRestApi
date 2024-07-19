package com.example.demo.contoller;

import com.example.demo.dto.ScriptRequestDto;
import com.example.demo.dto.ScriptResponseDto;
import com.example.demo.model.Script;
import com.example.demo.service.ScriptService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scripts")
@RequiredArgsConstructor
public class ScriptController {
    private final ScriptService scriptService;

    @PostMapping
    @Operation(summary = "Execute a script",
            description = "Executes a script with optional blocking")
    public String executeScript(@RequestBody ScriptRequestDto requestDto,
                                @RequestParam(defaultValue = "false") boolean blocking) {
        return scriptService.executeScript(requestDto, blocking);
    }

    @GetMapping
    @Operation(summary = "Get list of scripts", description = "Retrieve a list of"
            + " scripts with optional filtering by status")
    public List<ScriptResponseDto> getScripts(@RequestParam Optional<Script.ScriptStatus> status,
                                              Pageable pageable) {
        return scriptService.getScripts(status, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get script by ID", description = "Retrieve detailed information "
            + "about a script by its ID")
    public ScriptResponseDto getScript(@PathVariable Long id) {
        return scriptService.getScriptById(id);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Stop a script", description = "Forcibly stop a running script by its ID")
    public void stopScript(@PathVariable Long id) {
        scriptService.stopScriptById(id);
    }

    @DeleteMapping("/{id}/delete")
    @Operation(summary = "Delete a script", description = "Delete an inactive script by its ID")
    public void deleteScript(@PathVariable Long id) {
        scriptService.deleteById(id);
    }
}
