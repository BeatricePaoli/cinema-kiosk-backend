package com.example.cinemakiosk.service;

import com.example.cinemakiosk.dto.contextbroker.SmartBandCompactDto;

import java.util.List;

public interface ContextBrokerService {
    List<SmartBandCompactDto> searchSmartBands();
    <T> Boolean sendCommand(String deviceId, String attr, T value);
}
