package com.example.cinemakiosk.controller;

import com.example.cinemakiosk.dto.TheaterFilterDto;
import com.example.cinemakiosk.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/theater")
public class TheaterController {

    @Autowired
    private TheaterService theaterService;

    @GetMapping("/filter")
    public ResponseEntity<?> getFilters(@RequestParam(value = "movieId", required = false) Long movieId) {
        TheaterFilterDto result = theaterService.getFilters(movieId);
        return ResponseEntity.ok(result);
    }
}
