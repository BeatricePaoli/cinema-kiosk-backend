package com.example.cinemakiosk.controller;

import com.example.cinemakiosk.dto.AutocompleteTheaterFilterDto;
import com.example.cinemakiosk.dto.TheaterDto;
import com.example.cinemakiosk.dto.TicketTypeDto;
import com.example.cinemakiosk.service.TheaterService;
import com.example.cinemakiosk.utils.JwtClaimConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/theaters")
public class TheaterController {

    @Autowired
    private TheaterService theaterService;

    @GetMapping
    public ResponseEntity<?> getTheaters(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString(JwtClaimConstants.username);
        List<TheaterDto> result = theaterService.getTheaters(username);
        return ResponseEntity.ok(result);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTheater(@AuthenticationPrincipal Jwt jwt, @PathVariable("id") Long id) {
        String username = jwt.getClaimAsString(JwtClaimConstants.username);

        if (theaterService.checkIsAdminOfTheater(username, id)) {
            Boolean result = theaterService.deleteById(id);
            if (!result) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
