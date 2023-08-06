package com.example.cinemakiosk.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;

    private Integer durationMins;

    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "actors_movies", joinColumns = { @JoinColumn(name = "movie_id") }, inverseJoinColumns = { @JoinColumn(name = "actor_id") })
    private Set<Actor> actors = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "genres_movies", joinColumns = { @JoinColumn(name = "movie_id") }, inverseJoinColumns = { @JoinColumn(name = "actor_id") })
    private Set<MovieGenre> genres = new HashSet<>();
}
