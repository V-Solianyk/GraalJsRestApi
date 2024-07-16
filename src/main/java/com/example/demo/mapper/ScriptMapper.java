package com.example.demo.mapper;

import com.example.demo.config.MapperConfig;
import com.example.demo.dto.ScriptRequestDto;
import com.example.demo.dto.ScriptResponseDto;
import com.example.demo.model.Script;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface ScriptMapper {
    Script toModel(ScriptRequestDto requestDto);

    ScriptResponseDto toDto(Script script);
}
