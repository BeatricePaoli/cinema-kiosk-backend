package com.example.cinemakiosk.service;

import com.example.cinemakiosk.dto.MovieDto;
import com.example.cinemakiosk.dto.MovieFilterDto;
import com.example.cinemakiosk.dto.MovieSearchResponseDto;

public interface MovieService {

    MovieSearchResponseDto search(MovieFilterDto dto);
    MovieDto getById(Long id);

}
