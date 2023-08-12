package com.example.cinemakiosk.controller;

import com.example.cinemakiosk.dto.AutocompleteTheaterFilterDto;
import com.example.cinemakiosk.dto.TicketTypeDto;
import com.example.cinemakiosk.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/theaters")
public class TheaterController {

    @Autowired
    private TheaterService theaterService;

    @GetMapping("/filter")
    public ResponseEntity<?> getFilters(@RequestParam(value = "movieId", required = false) Long movieId) {
        AutocompleteTheaterFilterDto result = theaterService.getFilters(movieId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/tickets")
    public ResponseEntity<?> getTicketTypes(@PathVariable("id") Long id) {
        List<TicketTypeDto> result = theaterService.getTicketTypes(id);

        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }
}
