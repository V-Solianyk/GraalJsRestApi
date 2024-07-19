package com.example.demo.mapper;

import com.example.demo.config.MapperConfig;
import com.example.demo.dto.ScriptResponseDto;
import com.example.demo.model.Script;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface ScriptMapper {
    ScriptResponseDto toDto(Script script);
}
