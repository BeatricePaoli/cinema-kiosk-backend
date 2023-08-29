package com.example.cinemakiosk.dto.contextbroker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SmartBandCompactDto extends BaseEntityDto {
    private String emitterSerial;
    private PropertyDto<String> onInfo;
    private PropertyDto<String> onStatus;
}
