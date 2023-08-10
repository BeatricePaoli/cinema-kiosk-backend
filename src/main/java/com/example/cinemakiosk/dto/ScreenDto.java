package com.example.cinemakiosk.dto;

import com.example.cinemakiosk.model.Screen;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScreenDto extends RoomDto {
    private Integer totalSeats;
    private Map<String, Object> seatChart;

    public static ScreenDto toDto(Screen screen) {
        ScreenDto dto = new ScreenDto();
        BeanUtils.copyProperties(screen, dto);
        return dto;
    }
}
