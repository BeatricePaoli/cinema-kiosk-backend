package com.example.cinemakiosk.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BookingDto {
    private Long id;
    private String city;
    private TheaterDto theater;
    private MovieDto movie;
    private List<SeatDto> seats;
    private Long showId;
    private Date date;
    private String startTime;
    private Double price;
    private String codeUrl;

    private String username;
}
