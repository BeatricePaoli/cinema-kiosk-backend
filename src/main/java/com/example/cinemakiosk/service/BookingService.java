package com.example.cinemakiosk.service;

import com.example.cinemakiosk.dto.BookingDto;

public interface BookingService {
    Long createBooking(BookingDto dto);

    BookingDto getById(Long id);

    Boolean isBookingValid(Long id);
}
