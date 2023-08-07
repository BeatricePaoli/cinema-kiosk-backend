package com.example.cinemakiosk.service.impl;

import com.example.cinemakiosk.dto.ActorDto;
import com.example.cinemakiosk.dto.MovieDto;
import com.example.cinemakiosk.dto.MovieFilterDto;
import com.example.cinemakiosk.dto.MovieSearchResponseDto;
import com.example.cinemakiosk.model.Movie;
import com.example.cinemakiosk.model.MovieGenre;
import com.example.cinemakiosk.repository.MovieRepository;
import com.example.cinemakiosk.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public MovieSearchResponseDto search(MovieFilterDto dto) {
        List<MovieDto> current = movieRepository.findAll(MovieRepository.search(dto, true))
                .stream().map(MovieDto::toDto).collect(Collectors.toList());
        List<MovieDto> future = movieRepository.findAll(MovieRepository.search(dto, false))
                .stream().map(MovieDto::toDto).collect(Collectors.toList());
        return new MovieSearchResponseDto(current, future);
    }

    @Override
    public MovieDto getById(Long id) {
        Optional<Movie> movieOpt = movieRepository.findByIdWithFetch(id);
        if (movieOpt.isPresent()) {
            Movie movie = movieOpt.get();
            MovieDto dto = MovieDto.toDto(movie);
            dto.setGenres(movie.getGenres().stream().map(MovieGenre::getName).collect(Collectors.toList()));
            dto.setActors(movie.getActors().stream().map(ActorDto::toDto).collect(Collectors.toList()));
            return dto;
        }
        return null;
    }
}
