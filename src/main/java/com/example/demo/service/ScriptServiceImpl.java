package com.example.demo.service;

import com.example.demo.dto.ScriptRequestDto;
import com.example.demo.dto.ScriptResponseDto;
import com.example.demo.mapper.ScriptMapper;
import com.example.demo.model.Script;
import com.example.demo.repository.ScriptRepository;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScriptServiceImpl implements ScriptService {
    private final ScriptRepository repository;
    private final ScriptMapper mapper;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public String executeScript(ScriptRequestDto body, boolean blocking) {
        Script script = repository.save(new Script(body.getBody(),
                Script.ScriptStatus.QUEUED));
        Future<?> future = executorService.submit(() -> {
            Script localScript = script;
            localScript.setStatus(Script.ScriptStatus.EXECUTING);
            localScript = repository.save(localScript);

            try (ByteArrayOutputStream stdout = new ByteArrayOutputStream();
                    ByteArrayOutputStream stderr = new ByteArrayOutputStream();
                    Context context = Context.newBuilder("js")
                            .out(stdout)
                            .err(stderr)
                            .build()) {

                context.eval(Source.newBuilder("js", body.getBody(), "script.js").build());
                localScript.setStdout(stdout.toString(StandardCharsets.UTF_8));
                localScript.setStderr(stderr.toString(StandardCharsets.UTF_8));
                localScript.setStatus(Script.ScriptStatus.COMPLETED);
            } catch (Exception e) {
                localScript.setStatus(Script.ScriptStatus.FAILED);
                localScript.setStderr(e.getMessage());
            } finally {
                localScript.setProcessingTime(Duration.between(localScript.getStartTime(),
                        LocalDateTime.now()));
                repository.save(localScript);
            }
        });

        if (blocking) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Failed to execute script", e);
            }
        }

        return "Script execution initiated";
    }

    @Override
    public List<ScriptResponseDto> getScripts(Optional<Script.ScriptStatus> status,
                                              Pageable pageable) {
        return status.map(scriptStatus -> repository.findByStatus(scriptStatus, pageable).stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList()))
                .orElseGet(() -> repository.findAll().stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList()));
    }

    @Override
    public ScriptResponseDto getScriptById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("Script doesn't exist"
                        + " by this id " + id));
    }

    @Override
    public void stopScriptById(Long id) {
        Script script = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Script doesn't exist"
                        + " by this id " + id));
        script.setStatus(Script.ScriptStatus.STOPPED);
        repository.save(script);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
