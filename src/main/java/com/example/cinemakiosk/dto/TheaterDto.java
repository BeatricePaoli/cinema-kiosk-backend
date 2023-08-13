package com.example.cinemakiosk.dto;

import com.example.cinemakiosk.model.Theater;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class TheaterDto {
    private Long id;
    private String name;
    // TODO: da completare

    public static TheaterDto toDto(Theater theater) {
        TheaterDto dto = new TheaterDto();
        BeanUtils.copyProperties(theater, dto);
        return dto;
    }
}
