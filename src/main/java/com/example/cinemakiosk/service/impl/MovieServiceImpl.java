package com.example.cinemakiosk.service.impl;

import com.example.cinemakiosk.dto.ActorDto;
import com.example.cinemakiosk.dto.MovieDto;
import com.example.cinemakiosk.dto.MovieFilterDto;
import com.example.cinemakiosk.dto.MovieSearchResponseDto;
import com.example.cinemakiosk.model.Movie;
import com.example.cinemakiosk.model.MovieGenre;
import com.example.cinemakiosk.repository.ActorRepository;
import com.example.cinemakiosk.repository.MovieRepository;
import com.example.cinemakiosk.service.MovieService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    @Value("${base-url}")
    private String baseUrl;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Override
    public MovieSearchResponseDto search(MovieFilterDto dto) {
        List<MovieDto> current = movieRepository.findAll(MovieRepository.search(dto, true))
                .stream().map(this::toDto).collect(Collectors.toList());
        List<MovieDto> future = movieRepository.findAll(MovieRepository.search(dto, false))
                .stream().map(this::toDto).collect(Collectors.toList());
        return new MovieSearchResponseDto(current, future);
    }

    @Override
    public MovieDto getById(Long id) {
        Optional<Movie> movieOpt = movieRepository.findByIdWithFetch(id);
        if (movieOpt.isPresent()) {
            Movie movie = movieOpt.get();
            MovieDto dto = this.toDto(movie);
            dto.setGenres(movie.getGenres().stream().map(MovieGenre::getName).collect(Collectors.toList()));
            dto.setActors(movie.getActors().stream()
                    .map(a -> new ActorDto(a.getName() + " " + a.getSurname(),
                            baseUrl + "/api/movies/actors/img/" + a.getId()))
                    .collect(Collectors.toList()));
            return dto;
        }
        return null;
    }

    @Override
    public MovieDto toDto(Movie movie) {
        MovieDto dto = new MovieDto();
        BeanUtils.copyProperties(movie, dto);
        dto.setImg(baseUrl + "/api/movies/img/" + movie.getId());
        return dto;
    }

    @Override
    public byte[] getImgByMovieId(Long id) {
        return movieRepository.findImgByMovieId(id);
    }

    @Override
    public byte[] getImgByActorId(Long id) {
        return actorRepository.findImgByActorId(id);
    }
}
