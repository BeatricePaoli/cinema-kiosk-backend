package com.example.cinemakiosk.dto;

import com.example.cinemakiosk.model.DeviceActivityEvent;
import lombok.Data;

import java.util.Date;

@Data
public class DeviceActivityDto {
    private Long id;
    private Date tms;
    private String emitterSerial;
    private DeviceActivityEvent eventCode;
    private BookingDto booking;
    private Integer quantity;
    private BarProductDto product;
}
