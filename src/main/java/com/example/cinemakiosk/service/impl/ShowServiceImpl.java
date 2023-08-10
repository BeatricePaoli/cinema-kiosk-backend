package com.example.cinemakiosk.service.impl;

import com.example.cinemakiosk.dto.SeatDto;
import com.example.cinemakiosk.dto.SeatTakenDto;
import com.example.cinemakiosk.dto.ShowDto;
import com.example.cinemakiosk.dto.ShowFilterDto;
import com.example.cinemakiosk.model.Show;
import com.example.cinemakiosk.repository.BookingRepository;
import com.example.cinemakiosk.repository.ShowRepository;
import com.example.cinemakiosk.service.ShowService;
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

    @Override
    public List<ShowDto> search(ShowFilterDto dto) {
        List<Show> shows = showRepository.search(dto.getMovieId(), dto.getCity(), dto.getCinema());

        if (!shows.isEmpty()) {
            List<SeatTakenDto> seatsTaken;
            if (dto.getGetBookedSeats()) {
                List<Long> ids = shows.stream().map(Show::getId).collect(Collectors.toList());
                seatsTaken = bookingRepository.getSeatsTaken(ids);
            } else {
                seatsTaken = new ArrayList<>();
            }

            return shows.stream().map(show -> {
                ShowDto showDto = ShowDto.toDto(show);

                List<SeatDto> taken = seatsTaken.stream()
                        .filter(st -> Objects.equals(st.getShowId(), show.getId()))
                        .map(st -> new SeatDto(st.getSeatId(), st.getRow() + "-" + st.getCol()))
                        .collect(Collectors.toList());
                showDto.setSeatsTaken(taken);

                return showDto;
            }).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}
