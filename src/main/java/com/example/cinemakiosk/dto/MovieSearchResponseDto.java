package com.example.cinemakiosk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieSearchResponseDto {
    private List<MovieDto> current;
    private List<MovieDto> future;
}
