package com.example.cinemakiosk.dto;

import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class DeviceDto {
    private Long id;
    private String contextBrokerId;
    private Boolean isActive;

    public static DeviceDto toDto(DeviceInterface device) {
        DeviceDto dto = new DeviceDto();
        BeanUtils.copyProperties(device, dto);
        return dto;
    }
}
