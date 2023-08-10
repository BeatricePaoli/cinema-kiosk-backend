package com.example.cinemakiosk.dto;

import lombok.Data;

@Data
public class ShowFilterDto {
    private Long movieId;
    private String city;
    private String cinema;
    private Boolean getBookedSeats;
}
