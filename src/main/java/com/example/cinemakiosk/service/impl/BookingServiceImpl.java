package com.example.cinemakiosk.service.impl;

import com.example.cinemakiosk.dto.*;
import com.example.cinemakiosk.model.*;
import com.example.cinemakiosk.repository.BookingRepository;
import com.example.cinemakiosk.repository.SeatRepository;
import com.example.cinemakiosk.repository.ShowRepository;
import com.example.cinemakiosk.service.BookingService;
import com.example.cinemakiosk.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookingServiceImpl implements BookingService {

    @Value("${base-url}")
    private String baseUrl;

    @Value("${app.booking.limit-hours}")
    private Integer limitHours;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private SeatRepository seatRepository;

    // TODO: user
    @Override
    public Long createBooking(BookingDto dto) {
        Optional<Show> showOpt = showRepository.findById(dto.getShowId());
        if (showOpt.isPresent()) {
            Show show = showOpt.get();
            String[] splittedTime = show.getStartTime().split(":");

            if (splittedTime.length == 2) {
                LocalDate showDate = DateUtils.convertToLocalDate(show.getDate());
                LocalTime showTime = LocalTime.of(Integer.parseInt(splittedTime[0]), Integer.parseInt(splittedTime[1]));
                LocalDateTime limit = LocalDateTime.of(showDate, showTime).plusHours(limitHours);
                LocalDateTime now = LocalDateTime.now();

                if (!now.isAfter(limit)) {
                    List<Seat> allSeats = seatRepository.findByScreen(show.getScreen().getId());

                    List<SeatTakenDto> seatsTaken = bookingRepository.getSeatsTaken(Collections.singletonList(dto.getShowId()));
                    List<Long> idsTaken = seatsTaken.stream().map(SeatTakenDto::getSeatId).toList();

                    List<String> toBookLabels = dto.getSeats().stream().map(SeatDto::getLabel).collect(Collectors.toList());
                    List<Seat> toBookEntities = allSeats.stream()
                            .filter(seat -> toBookLabels.contains(seat.getLabel()) && !idsTaken.contains(seat.getId()))
                            .toList();

                    if (!toBookLabels.isEmpty() && toBookLabels.size() == toBookEntities.size()) {
                        Booking booking = new Booking();
                        booking.setDateCreated(new Date());
                        booking.setStatus(BookingStatus.CREATED);
                        booking.setPrice(dto.getPrice());
                        booking.setShow(show);
                        booking.setSeats(new HashSet<>(toBookEntities));

                        booking = bookingRepository.save(booking);
                        return booking.getId();
                    } else {
                        log.error("Can't book seats {}, they are already taken", toBookLabels);
                        return null;
                    }
                } else {
                    log.error("Can't book for a past show: show time limit (+1h) {}, now {}", limit, now);
                    return null;
                }
            } else {
                log.error("Start time string with wrong format {}", dto.getStartTime());
                return null;
            }
        } else {
            log.error("Show id {} to book not found", dto.getShowId());
            return null;
        }
    }

    @Override
    public List<BookingDto> getBookings() {
        List<Booking> bookings = bookingRepository.getBookings();
        return bookings.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public BookingDto getById(Long id) {
        Optional<Booking> bookingOpt = bookingRepository.findByIdWithFetch(id);
        return bookingOpt.map(this::toDto).orElse(null);
    }

    @Override
    public Boolean isBookingValid(Long id) {
        return bookingRepository.findById(id).isPresent();
    }

    private BookingDto toDto(Booking booking) {
        Show show = booking.getShow();
        Movie movie = show.getMovie();
        Theater theater = show.getScreen().getTheater();

        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setPrice(booking.getPrice());
        dto.setShowId(show.getId());
        dto.setDate(show.getDate());
        dto.setStartTime(show.getStartTime());

        dto.setTheater(TheaterDto.toDto(theater));
        dto.setCity(theater.getAddress().getCity());

        dto.setMovie(MovieDto.toDto(movie));

        List<SeatDto> seats = booking.getSeats().stream().map(s -> new SeatDto(s.getId(), s.getLabel()))
                .collect(Collectors.toList());
        dto.setSeats(seats);

        dto.setCodeUrl(baseUrl + "/api/bookings/code/" + booking.getId());

        return dto;
    }
}
