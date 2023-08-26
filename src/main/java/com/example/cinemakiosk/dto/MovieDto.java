package com.example.cinemakiosk.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MovieDto {
    private Long id;
    private String img;
    private String name;
    private String description;
    private Integer durationMins;
    private Float score;
    private Date releaseDate;
    private List<ActorDto> actors;
    private List<String> genres;
}
