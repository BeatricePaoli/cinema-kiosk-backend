package com.example.cinemakiosk.service;

import com.example.cinemakiosk.dto.AutocompleteTheaterFilterDto;
import com.example.cinemakiosk.dto.TicketTypeDto;

import java.util.List;

public interface TheaterService {
    AutocompleteTheaterFilterDto getFilters(Long movieId);

    List<TicketTypeDto> getTicketTypes(Long id);
}
