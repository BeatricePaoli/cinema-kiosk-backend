package com.example.cinemakiosk.dto.contextbroker;

import lombok.Data;

import java.util.Date;

@Data
public class RelationshipDto {
    private String type;
    private String object;
    private Date observedAt;
}
