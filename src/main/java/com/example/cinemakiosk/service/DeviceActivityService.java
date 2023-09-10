package com.example.cinemakiosk.service;

import com.example.cinemakiosk.dto.contextbroker.NotificationDto;
import com.example.cinemakiosk.dto.contextbroker.SmartBandDto;
import com.example.cinemakiosk.model.Booking;
import com.example.cinemakiosk.model.SmartBand;

public interface DeviceActivityService {

    void addActivationLog(SmartBand smartBand, Booking booking);

    void addEmitterLog(NotificationDto<SmartBandDto> dto);
}
