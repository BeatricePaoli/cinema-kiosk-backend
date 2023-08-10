package com.example.cinemakiosk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatTakenDto {
    private Long showId;
    private Long seatId;
    private String row;
    private String col;
}
