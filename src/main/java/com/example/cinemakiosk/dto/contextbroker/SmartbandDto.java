package com.example.cinemakiosk.dto.contextbroker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SmartbandDto extends BaseEntityDto {
    private PropertyDto<String> emitterSerial;
}
