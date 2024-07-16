package com.example.demo.contoller;

import com.example.demo.service.ScriptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scripts")
@RequiredArgsConstructor
public class ScriptController {
    private final ScriptService scriptService;
}