package com.example.cinemakiosk.dto.contextbroker;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.Date;

@Data
public class PropertyDto<T> {
    private String type;
    @JsonAlias({"value", "@value"})
    private T value;
    private Date observedAt;
}
