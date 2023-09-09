package com.example.cinemakiosk.service;

import com.example.cinemakiosk.dto.AutocompleteTheaterFilterDto;
import com.example.cinemakiosk.dto.TheaterDto;
import com.example.cinemakiosk.dto.TicketTypeDto;

import java.util.List;

public interface TheaterService {
    List<TheaterDto> getTheaters(String username);

    AutocompleteTheaterFilterDto getFilters(Long movieId);

    TheaterDto getById(Long id);

    List<TicketTypeDto> getTicketTypes(Long id);

    Long saveTheater(TheaterDto dto);

    Boolean deleteById(Long id);

    Boolean checkIsAdminOfTheater(String username, Long id);
}
