package com.example.cinemakiosk.service;

import com.example.cinemakiosk.dto.ShowDto;
import com.example.cinemakiosk.dto.ShowFilterDto;
import com.example.cinemakiosk.model.Show;

import java.util.List;

public interface ShowService {
    List<ShowDto> search(ShowFilterDto dto);
    ShowDto toDto(Show show);
}
