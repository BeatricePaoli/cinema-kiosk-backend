package com.example.cinemakiosk.service;

import com.example.cinemakiosk.dto.MovieDto;
import com.example.cinemakiosk.dto.MovieFilterDto;
import com.example.cinemakiosk.dto.MovieSearchResponseDto;
import com.example.cinemakiosk.model.Movie;

public interface MovieService {

    MovieSearchResponseDto search(MovieFilterDto dto);
    MovieDto getById(Long id);
    MovieDto toDto(Movie movie);
    byte[] getImgByMovieId(Long id);
    byte[] getImgByActorId(Long id);

}
