package com.example.cinemakiosk.dto;

import com.example.cinemakiosk.model.Address;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class AddressDto {
    private Long id;
    private String street;
    private String number;
    private String city;
    private String country;
    private String zipCode;

    public static AddressDto toDto(Address address) {
        AddressDto dto = new AddressDto();
        BeanUtils.copyProperties(address, dto);
        return dto;
    }
}
