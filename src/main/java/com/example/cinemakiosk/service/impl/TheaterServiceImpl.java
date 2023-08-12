package com.example.cinemakiosk.service.impl;

import com.example.cinemakiosk.dto.AutocompleteTheaterFilterDto;
import com.example.cinemakiosk.dto.CityFilterDto;
import com.example.cinemakiosk.dto.TheaterFilterDto;
import com.example.cinemakiosk.dto.TicketTypeDto;
import com.example.cinemakiosk.model.Theater;
import com.example.cinemakiosk.model.TicketType;
import com.example.cinemakiosk.repository.TheaterRepository;
import com.example.cinemakiosk.repository.TicketTypeRepository;
import com.example.cinemakiosk.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TheaterServiceImpl implements TheaterService {

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Override
    public AutocompleteTheaterFilterDto getFilters(Long movieId) {
        List<Theater> theaters;
        if (movieId == null) {
            theaters = theaterRepository.findAllWithFetch();
        } else {
            theaters = theaterRepository.findWithFutureShow(movieId);
        }

        Map<String, List<Theater>> grouped = theaters.stream()
                .collect(Collectors.groupingBy(theater -> theater.getAddress().getCity()));

        List<CityFilterDto> cityFilters = grouped.entrySet().stream().map(entry -> {
            List<TheaterFilterDto> theatersNames = entry.getValue().stream()
                    .map(t -> new TheaterFilterDto(t.getId(), t.getName())).collect(Collectors.toList());
            return new CityFilterDto(entry.getKey(), theatersNames);
        }).collect(Collectors.toList());
        return new AutocompleteTheaterFilterDto(cityFilters);
    }

    @Override
    public List<TicketTypeDto> getTicketTypes(Long id) {
        List<TicketType> ticketTypes = ticketTypeRepository.getByTheater(id);
        return ticketTypes.stream().map(TicketTypeDto::toDto).collect(Collectors.toList());
    }
}
