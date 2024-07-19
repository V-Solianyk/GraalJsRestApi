package com.example.demo.service;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.dto.ScriptRequestDto;
import com.example.demo.dto.ScriptResponseDto;
import com.example.demo.mapper.ScriptMapper;
import com.example.demo.model.Script;
import com.example.demo.repository.ScriptRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class ScriptServiceTest {

    @Mock
    private ScriptRepository repository;
    @Mock
    private ScriptMapper mapper;

    @InjectMocks
    private ScriptServiceImpl scriptService;

    @Test
    void executeScript_NonBlockingExecution_InitiatesSuccessfully() {
        ScriptRequestDto requestDto = new ScriptRequestDto();
        requestDto.setBody("console.log('Hello World');");

        Script script = new Script(requestDto.getBody(), Script.ScriptStatus.QUEUED);
        when(repository.save(any(Script.class))).thenReturn(script);

        String result = scriptService.executeScript(requestDto, false);

        Assertions.assertEquals("Script execution initiated", result);
        verify(repository, times(1)).save(any(Script.class));
    }

    @Test
    void executeScript_BlockingExecution_InitiatesSuccessfully() {
        ScriptRequestDto requestDto = new ScriptRequestDto();
        requestDto.setBody("console.log('Hello World');");

        Script script = new Script(requestDto.getBody(), Script.ScriptStatus.QUEUED);

        when(repository.save(any(Script.class))).thenReturn(script);
        String result = scriptService.executeScript(requestDto, true);

        Assertions.assertEquals("Script execution initiated", result);
        verify(repository, times(3)).save(any(Script.class));
    }

    @Test
    void getScripts_StatusFilter_ReturnsFilteredScripts() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Script> scripts = List.of(new Script(), new Script());
        PageImpl<Script> scriptPage = new PageImpl<>(scripts, pageable, scripts.size());

        ScriptResponseDto dto1sScriptResponseDto1 = new ScriptResponseDto();
        ScriptResponseDto scriptResponseDto2 = new ScriptResponseDto();

        when(repository.findByStatus(Script.ScriptStatus.QUEUED, pageable))
                .thenReturn(scriptPage.getContent());
        when(mapper.toDto(scripts.get(0))).thenReturn(dto1sScriptResponseDto1);
        when(mapper.toDto(scripts.get(1))).thenReturn(scriptResponseDto2);

        List<ScriptResponseDto> result = scriptService
                .getScripts(Optional.of(Script.ScriptStatus.QUEUED), pageable);

        Assertions.assertEquals(2, result.size());
    }

    @Test
    void getScriptById_ValidId_ReturnsScript() {
        Script script = new Script();
        script.setId(1L);
        script.setBody("console.log('test');");
        ScriptResponseDto expectedDto = new ScriptResponseDto();
        expectedDto.setId(1L);
        expectedDto.setBody("console.log('test');");

        when(repository.findById(1L)).thenReturn(Optional.of(script));
        when(mapper.toDto(script)).thenReturn(expectedDto);

        ScriptResponseDto actualDto = scriptService.getScriptById(1L);

        Assertions.assertNotNull(actualDto);
        Assertions.assertEquals(expectedDto.getId(), actualDto.getId());
        Assertions.assertEquals(expectedDto.getBody(), actualDto.getBody());
    }

    @Test
    void stopScriptById_ValidId_StopsScript() {
        Script script = new Script();
        script.setId(1L);
        script.setStatus(Script.ScriptStatus.EXECUTING);

        when(repository.findById(1L)).thenReturn(Optional.of(script));
        scriptService.stopScriptById(1L);

        Assertions.assertEquals(Script.ScriptStatus.STOPPED, script.getStatus());
        verify(repository, times(1)).save(script);
    }

    @Test
    void deleteById_ValidId_DeletesScript() {
        scriptService.deleteById(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}
