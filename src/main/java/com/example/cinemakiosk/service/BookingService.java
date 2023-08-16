package com.example.cinemakiosk.service;

import com.example.cinemakiosk.dto.BookingDto;

import java.util.List;

public interface BookingService {
    Long createBooking(BookingDto dto);

    List<BookingDto> getBookings(String username);

    BookingDto getById(Long id);

    Boolean isBookingValid(Long id);
}
