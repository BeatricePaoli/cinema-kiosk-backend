package com.example.cinemakiosk.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "show")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    private String startTime;
    private String language;

    @Enumerated(EnumType.STRING)
    private ProjectionType projectionType;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;
}
