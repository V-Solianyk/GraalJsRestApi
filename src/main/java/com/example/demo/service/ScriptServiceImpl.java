package com.example.demo.service;

import com.example.demo.mapper.ScriptMapper;
import com.example.demo.repository.ScriptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScriptServiceImpl {
    private final ScriptRepository repository;
    private final ScriptMapper mapper;

}
