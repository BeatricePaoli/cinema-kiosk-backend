package com.example.cinemakiosk.dto;

import com.example.cinemakiosk.model.Day;
import com.example.cinemakiosk.model.ProjectionType;
import com.example.cinemakiosk.model.TicketType;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class TicketTypeDto {
    private Long id;
    private String name;
    private Double price;
    private ProjectionType projectionType;
    private Boolean availableOnline;
    private List<Day> days;

    public static TicketTypeDto toDto(TicketType ticket) {
        TicketTypeDto dto = new TicketTypeDto();
        BeanUtils.copyProperties(ticket, dto);
        dto.setDays(new ArrayList<>(ticket.getDays()));
        return dto;
    }
}
