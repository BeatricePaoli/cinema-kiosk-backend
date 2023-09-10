package com.example.cinemakiosk.dto.contextbroker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CashRegisterDto extends BaseEntityDto {
    private PropertyDto<String> cashRegisterType;
    private RelationshipDto device;
    private RelationshipDto product;
    private PropertyDto<Integer> quantity;
}
