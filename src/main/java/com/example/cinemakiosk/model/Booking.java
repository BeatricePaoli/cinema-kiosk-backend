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
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer numSeats;
    private Double price;

    @Temporal(TemporalType.DATE)
    private Date dateCreated;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "seats_show", joinColumns = { @JoinColumn(name = "show_id") }, inverseJoinColumns = { @JoinColumn(name = "seat_id") })
    private Set<Seat> seats = new HashSet<>();

    @ManyToOne
    private User user;
}
