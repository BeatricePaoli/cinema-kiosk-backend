package com.example.cinemakiosk.controller;

import com.example.cinemakiosk.dto.DeviceActivityDto;
import com.example.cinemakiosk.dto.DeviceDto;
import com.example.cinemakiosk.dto.DeviceFilterDto;
import com.example.cinemakiosk.service.DeviceActivityService;
import com.example.cinemakiosk.service.DeviceService;
import com.example.cinemakiosk.utils.JwtClaimConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceActivityService deviceActivityService;

    @PostMapping("/filter")
    public ResponseEntity<?> getDevices(@RequestBody DeviceFilterDto dto) {
        List<DeviceDto> result = deviceService.search(dto);

        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        DeviceDto result = deviceService.getById(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<?> getActivitiesById(@PathVariable("id") Long id) {
        List<DeviceActivityDto> result = deviceActivityService.getActivities(id);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> deactivateById(@AuthenticationPrincipal Jwt jwt, @PathVariable("id") Long id) {
        String username = jwt.getClaimAsString(JwtClaimConstants.username);
        Boolean result = deviceService.deactivate(id, username);
        if (!result) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}
