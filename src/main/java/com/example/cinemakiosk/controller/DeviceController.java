package com.example.cinemakiosk.controller;

import com.example.cinemakiosk.dto.DeviceDto;
import com.example.cinemakiosk.dto.DeviceFilterDto;
import com.example.cinemakiosk.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

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
}
