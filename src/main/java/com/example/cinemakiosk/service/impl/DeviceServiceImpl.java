package com.example.cinemakiosk.service.impl;

import com.example.cinemakiosk.dto.DeviceDto;
import com.example.cinemakiosk.dto.DeviceFilterDto;
import com.example.cinemakiosk.dto.DeviceInterface;
import com.example.cinemakiosk.model.SmartBand;
import com.example.cinemakiosk.repository.DeviceRepository;
import com.example.cinemakiosk.repository.SmartBandRepository;
import com.example.cinemakiosk.service.ContextBrokerService;
import com.example.cinemakiosk.service.DeviceActivityService;
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

    @Autowired
    private SmartBandRepository smartBandRepository;

    @Autowired
    private ContextBrokerService contextBrokerService;

    @Autowired
    private DeviceActivityService deviceActivityService;


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

    @Override
    public boolean deactivate(Long id, String username) {
        Optional<SmartBand> smartbandOpt = smartBandRepository.findById(id);
        if (deviceRepository.deviceBelongsToAdminOrCashier(id, username) && smartbandOpt.isPresent()) {
            SmartBand smartBand = smartbandOpt.get();
            if (contextBrokerService.sendCommand(smartBand.getContextBrokerId(), "on", "true")) {
                return deviceActivityService.addDeactivationLog(smartBand);
            }
        }
        return false;
    }
}
