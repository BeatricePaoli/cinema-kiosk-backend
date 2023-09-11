package com.example.cinemakiosk.dto;

import com.example.cinemakiosk.model.BarProduct;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class BarProductDto {
    private Long id;
    private String name;
    private Double price;
    private String currency;
    private String productCode;

    public static BarProductDto toDto(BarProduct product) {
        BarProductDto dto = new BarProductDto();
        BeanUtils.copyProperties(product, dto);
        return dto;
    }
}
