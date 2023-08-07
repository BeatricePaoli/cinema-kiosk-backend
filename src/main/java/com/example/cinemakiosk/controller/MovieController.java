package com.example.cinemakiosk.controller;

import com.example.cinemakiosk.dto.MovieDto;
import com.example.cinemakiosk.dto.MovieFilterDto;
import com.example.cinemakiosk.dto.MovieSearchResponseDto;
import com.example.cinemakiosk.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<?> search(@RequestBody MovieFilterDto dto) {
        MovieSearchResponseDto result = movieService.search(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        MovieDto result = movieService.getById(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}
