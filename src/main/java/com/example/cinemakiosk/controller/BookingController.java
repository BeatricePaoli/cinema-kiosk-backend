package com.example.cinemakiosk.controller;

import com.example.cinemakiosk.dto.BookingCompactDto;
import com.example.cinemakiosk.dto.BookingDto;
import com.example.cinemakiosk.service.BookingService;
import com.example.cinemakiosk.utils.JwtClaimConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping("api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(@AuthenticationPrincipal Jwt jwt, @RequestBody BookingDto dto) {
        String username = jwt.getClaimAsString(JwtClaimConstants.username);
        dto.setUsername(username);

        Long bookingId = bookingService.createBooking(dto);

        if (bookingId == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(bookingId);
    }

    @GetMapping
    public ResponseEntity<?> getBookings(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString(JwtClaimConstants.username);
        List<BookingDto> result = bookingService.getBookings(username);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        BookingDto result = bookingService.getById(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        Boolean result = bookingService.deleteById(id);
        if (!result) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> validateById(@AuthenticationPrincipal Jwt jwt, @PathVariable("id") Long id) {
        String username = jwt.getClaimAsString(JwtClaimConstants.username);
        Boolean result = bookingService.validateById(id, username);
        if (!result) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/code/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody ResponseEntity<?> getQrCode(@PathVariable("id") Long id) throws JsonProcessingException {
        BookingCompactDto booking = bookingService.getByIdCompact(id);
        if (booking == null) {
            return ResponseEntity.notFound().build();
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(booking);


        ByteArrayOutputStream stream = QRCode
                .from(json)
                .withSize(400, 400)
                .stream();

        return ResponseEntity.ok(new ByteArrayResource(stream.toByteArray()));
    }
}
