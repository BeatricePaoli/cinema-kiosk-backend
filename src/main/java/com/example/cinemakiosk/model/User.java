package com.example.cinemakiosk.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;

    @OneToMany(mappedBy = "admin")
    private List<Theater> theaters;

    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;

    @ManyToOne
    private Theater theaterCashier;
}
