package com.example.cinemakiosk.dto.contextbroker;

import lombok.Data;

import java.util.Date;

@Data
public class PropertyDto<T> {
    private String type;
    private T value;
    private Date observedAt;
}
