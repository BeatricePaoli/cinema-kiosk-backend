package com.example.cinemakiosk.service;

import com.example.cinemakiosk.dto.TheaterFilterDto;
import com.example.cinemakiosk.dto.TicketTypeDto;

import java.util.List;

public interface TheaterService {
    TheaterFilterDto getFilters(Long movieId);

    List<TicketTypeDto> getTicketTypes(Long id);
}
