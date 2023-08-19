package com.example.cinemakiosk.controller;

import com.example.cinemakiosk.dto.BookingDto;
import com.example.cinemakiosk.service.BookingService;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(@AuthenticationPrincipal Jwt jwt, @RequestBody BookingDto dto) {
        String username = jwt.getClaimAsString("preferred_username");
        dto.setUsername(username);

        Long bookingId = bookingService.createBooking(dto);

        if (bookingId == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(bookingId);
    }

    @GetMapping
    public ResponseEntity<?> getBookings(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
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
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/code/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody ResponseEntity<?> getQrCode(@PathVariable("id") Long id) {
        Boolean isValid = bookingService.isBookingValid(id);
        if (!isValid) {
            return ResponseEntity.notFound().build();
        }
        ByteArrayOutputStream stream = QRCode
                .from(String.valueOf(id))
                .withSize(250, 250)
                .stream();

        return ResponseEntity.ok(new ByteArrayResource(stream.toByteArray()));
    }
}
