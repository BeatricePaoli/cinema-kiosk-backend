package com.example.cinemakiosk.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "theater")
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
    private List<Screen> screens;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
    private List<TicketType> ticketTypes;

    @ManyToOne
    private User admin;

    @OneToMany(mappedBy = "theaterCashier")
    private List<User> cashiers;
}
