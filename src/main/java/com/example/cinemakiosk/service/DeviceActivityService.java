package com.example.cinemakiosk.service;

import com.example.cinemakiosk.dto.DeviceActivityDto;
import com.example.cinemakiosk.dto.contextbroker.NotificationDto;
import com.example.cinemakiosk.dto.contextbroker.SmartBandDto;
import com.example.cinemakiosk.model.Booking;
import com.example.cinemakiosk.model.SmartBand;

import java.util.List;

public interface DeviceActivityService {

    void addActivationLog(SmartBand smartBand, Booking booking);
    void addEmitterLog(NotificationDto<SmartBandDto> dto);
    Boolean addDeactivationLog(SmartBand smartBand);

    List<DeviceActivityDto> getActivities(Long id);
}
