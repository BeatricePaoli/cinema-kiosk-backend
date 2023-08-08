package com.example.cinemakiosk.service;

import com.example.cinemakiosk.dto.TheaterFilterDto;

public interface TheaterService {
    TheaterFilterDto getFilters(Long movieId);
}
