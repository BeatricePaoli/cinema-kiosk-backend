package com.example.cinemakiosk.dto;

import lombok.Data;

@Data
public class MovieFilterDto {
    private String movie;
    private String city;
    private String cinema;

    private Long movieId;
}
