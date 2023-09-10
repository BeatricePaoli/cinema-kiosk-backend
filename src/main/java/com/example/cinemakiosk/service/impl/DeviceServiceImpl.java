package com.example.cinemakiosk.service.impl;

import com.example.cinemakiosk.dto.DeviceDto;
import com.example.cinemakiosk.dto.DeviceFilterDto;
import com.example.cinemakiosk.dto.DeviceInterface;
import com.example.cinemakiosk.repository.DeviceRepository;
import com.example.cinemakiosk.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;


    @Override
    public List<DeviceDto> search(DeviceFilterDto dto) {
        List<DeviceInterface> devices = deviceRepository.search(dto.getTheaterId(), dto.getType());
        return devices.stream().map(DeviceDto::toDto).collect(Collectors.toList());
    }

    @Override
    public DeviceDto getById(Long id) {
        Optional<DeviceInterface> dtoById = deviceRepository.findDtoById(id);
        return dtoById.map(DeviceDto::toDto).orElse(null);
    }
}
