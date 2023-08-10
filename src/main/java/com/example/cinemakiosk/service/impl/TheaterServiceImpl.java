package com.example.cinemakiosk.service.impl;

import com.example.cinemakiosk.dto.CityFilterDto;
import com.example.cinemakiosk.dto.TheaterFilterDto;
import com.example.cinemakiosk.model.Theater;
import com.example.cinemakiosk.repository.TheaterRepository;
import com.example.cinemakiosk.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TheaterServiceImpl implements TheaterService {

    @Autowired
    private TheaterRepository theaterRepository;

    @Override
    public TheaterFilterDto getFilters(Long movieId) {
        List<Theater> theaters;
        if (movieId == null) {
            theaters = theaterRepository.findAllWithFetch();
        } else {
            theaters = theaterRepository.findWithFutureShow(movieId);
        }

        Map<String, List<Theater>> grouped = theaters.stream()
                .collect(Collectors.groupingBy(theater -> theater.getAddress().getCity()));

        List<CityFilterDto> cityFilters = grouped.entrySet().stream().map(entry -> {
            List<String> theatersNames = entry.getValue().stream()
                    .map(Theater::getName).collect(Collectors.toList());
            return new CityFilterDto(entry.getKey(), theatersNames);
        }).collect(Collectors.toList());

        return new TheaterFilterDto(cityFilters);
    }
}
