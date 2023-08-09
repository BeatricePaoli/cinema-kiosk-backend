package com.example.cinemakiosk.controller;

import com.example.cinemakiosk.dto.MovieFilterDto;
import com.example.cinemakiosk.dto.ShowDto;
import com.example.cinemakiosk.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/shows")
public class ShowController {

    @Autowired
    private ShowService showService;

    @PostMapping("/filter")
    public ResponseEntity<?> getFilters(@RequestBody MovieFilterDto dto) {
        List<ShowDto> result = showService.search(dto);
        return ResponseEntity.ok(result);
    }
}
