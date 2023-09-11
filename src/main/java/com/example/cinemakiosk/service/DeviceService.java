package com.example.cinemakiosk.service;

import com.example.cinemakiosk.dto.DeviceDto;
import com.example.cinemakiosk.dto.DeviceFilterDto;

import java.util.List;

public interface DeviceService {
    List<DeviceDto> search(DeviceFilterDto dto);

    DeviceDto getById(Long id);

    boolean deactivate(Long id, String username);
}
