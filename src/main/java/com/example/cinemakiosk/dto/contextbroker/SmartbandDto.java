package com.example.cinemakiosk.dto.contextbroker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SmartBandDto extends BaseEntityDto {
    private PropertyDto<String> emitterSerial;

    @JsonProperty("on_info")
    private PropertyDto<PropertyDto<String>> onInfo;

    @JsonProperty("on_status")
    private PropertyDto<PropertyDto<String>> onStatus;
}
