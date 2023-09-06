package com.example.cinemakiosk.dto;

import com.example.cinemakiosk.model.Theater;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class TheaterDto {
    private Long id;
    private String name;
    private AddressDto address;

    public static TheaterDto toDto(Theater theater) {
        TheaterDto dto = new TheaterDto();
        BeanUtils.copyProperties(theater, dto);
        dto.setAddress(AddressDto.toDto(theater.getAddress()));
        return dto;
    }
}
