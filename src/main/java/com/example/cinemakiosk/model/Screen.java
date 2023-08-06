package com.example.cinemakiosk.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "screen")
public class Screen extends Room {

    @Formula("SELECT COUNT(*) FROM Seat s WHERE s.screen_id = id")
    private Integer totalSeats;

    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL)
    private List<Seat> seats;

    @ManyToOne
    private Theater theater;
}
