package com.example.cinemakiosk.controller;

import com.example.cinemakiosk.dto.contextbroker.CashRegisterDto;
import com.example.cinemakiosk.dto.contextbroker.NotificationDto;
import com.example.cinemakiosk.dto.contextbroker.SmartBandDto;
import com.example.cinemakiosk.service.DeviceActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/context-broker")
public class ContextBrokerController {

    @Autowired
    private DeviceActivityService deviceActivityService;

    @PostMapping(value = "smartBand")
    public ResponseEntity<?> getNotificationSmartBand(@RequestBody NotificationDto<SmartBandDto> dto) {
        log.info("Data notifica smartband: {}", dto.getNotifiedAt());
        deviceActivityService.addEmitterLog(dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "cashRegister")
    public ResponseEntity<?> getNotificationCashRegister(@RequestBody NotificationDto<CashRegisterDto> dto) {
        log.info("Data notifica cassa: {}", dto.getNotifiedAt());
        deviceActivityService.addPurchaseLog(dto);
        return ResponseEntity.noContent().build();
    }
}
