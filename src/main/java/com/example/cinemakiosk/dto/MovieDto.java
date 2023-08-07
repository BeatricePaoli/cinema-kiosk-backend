package com.example.cinemakiosk.dto;

import com.example.cinemakiosk.model.Actor;
import com.example.cinemakiosk.model.Movie;
import com.example.cinemakiosk.model.MovieGenre;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.*;

@Data
public class MovieDto {
    private Long id;
    private byte[] img;
    private String name;
    private String description;
    private Integer durationMins;
    private Float score;
    private Date releaseDate;
    private List<ActorDto> actors;
    private List<String> genres;

    public static MovieDto toDto(Movie movie) {
        MovieDto dto = new MovieDto();
        BeanUtils.copyProperties(movie, dto);
        return dto;
    }
}
