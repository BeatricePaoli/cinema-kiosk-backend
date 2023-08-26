package com.example.cinemakiosk.service.impl;

import com.example.cinemakiosk.dto.*;
import com.example.cinemakiosk.model.Show;
import com.example.cinemakiosk.repository.BookingRepository;
import com.example.cinemakiosk.repository.ShowRepository;
import com.example.cinemakiosk.service.MovieService;
import com.example.cinemakiosk.service.ShowService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ShowServiceImpl implements ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private MovieService movieService;

    @Override
    public List<ShowDto> search(ShowFilterDto dto) {
        List<Show> shows = showRepository.search(dto.getMovieId(), dto.getCity(), dto.getTheaterId());

        if (!shows.isEmpty()) {
            List<SeatTakenDto> seatsTaken;
            if (dto.getGetBookedSeats()) {
                List<Long> ids = shows.stream().map(Show::getId).collect(Collectors.toList());
                seatsTaken = bookingRepository.getSeatsTaken(ids);
            } else {
                seatsTaken = new ArrayList<>();
            }

            return shows.stream().map(show -> {
                ShowDto showDto = this.toDto(show);

                List<SeatDto> taken = seatsTaken.stream()
                        .filter(st -> Objects.equals(st.getShowId(), show.getId()))
                        .map(st -> new SeatDto(st.getSeatId(), st.getLabel()))
                        .collect(Collectors.toList());
                showDto.setSeatsTaken(taken);

                return showDto;
            }).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    @Override
    public ShowDto toDto(Show show) {
        ShowDto dto = new ShowDto();
        BeanUtils.copyProperties(show, dto);
        dto.setMovie(movieService.toDto(show.getMovie()));
        dto.setScreen(ScreenDto.toDto(show.getScreen()));
        return dto;
    }
}
