package com.example.cinemakiosk.model;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "screen")
public class Screen extends Room {

    @Formula("(SELECT COUNT(*) FROM Seat s WHERE s.screen_id = id)")
    private Integer totalSeats;

    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL)
    private List<Seat> seats;

    @ManyToOne
    private Theater theater;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> seatChart = new HashMap<>();
}
