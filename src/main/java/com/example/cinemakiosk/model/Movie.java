package com.example.cinemakiosk.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType;

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

    @Lob
    @JdbcType(VarbinaryJdbcType.class)
    private byte[] img;

    private String name;

    @Column(length = 5000)
    private String description;

    private Integer durationMins;
    private Float score;

    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "actors_movies", joinColumns = { @JoinColumn(name = "movie_id") }, inverseJoinColumns = { @JoinColumn(name = "actor_id") })
    private Set<Actor> actors = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "genres_movies", joinColumns = { @JoinColumn(name = "movie_id") }, inverseJoinColumns = { @JoinColumn(name = "genre_id") })
    private Set<MovieGenre> genres = new HashSet<>();
}
