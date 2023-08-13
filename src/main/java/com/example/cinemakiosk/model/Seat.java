package com.example.cinemakiosk.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "seat")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String row;
    private String col;

    @ManyToOne
    private Screen screen;

    public String getLabel() {
        return row + "-" + col;
    }
}
