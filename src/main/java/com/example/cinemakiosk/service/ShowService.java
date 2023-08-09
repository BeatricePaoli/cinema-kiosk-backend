package com.example.cinemakiosk.service;

import com.example.cinemakiosk.dto.MovieFilterDto;
import com.example.cinemakiosk.dto.ShowDto;

import java.util.List;

public interface ShowService {
    List<ShowDto> search(MovieFilterDto dto);
}
