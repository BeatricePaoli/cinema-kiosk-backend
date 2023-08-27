package com.example.cinemakiosk.dto;

import com.example.cinemakiosk.model.BookingStatus;
import lombok.Data;

import java.util.Date;

@Data
public class BookingCompactDto {
    private Long id;
    private String city;
    private String theaterName;
    private String movieName;
    private Integer totalSeats;
    private Date date;
    private String startTime;
    private Double price;
    private BookingStatus status;
}
