package com.example.cinemakiosk.service.impl;

import com.example.cinemakiosk.dto.MovieFilterDto;
import com.example.cinemakiosk.dto.ShowDto;
import com.example.cinemakiosk.model.Show;
import com.example.cinemakiosk.repository.ShowRepository;
import com.example.cinemakiosk.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowServiceImpl implements ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Override
    public List<ShowDto> search(MovieFilterDto dto) {
        List<Show> shows = showRepository.search(dto.getMovieId(), dto.getCity(), dto.getCinema());
        return shows.stream().map(ShowDto::toDto).collect(Collectors.toList());
    }
}
