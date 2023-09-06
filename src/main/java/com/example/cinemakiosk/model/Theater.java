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
    private Boolean deleted;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
    private List<Screen> screens;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bar_id", referencedColumnName = "id")
    private Bar bar;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
    private List<TicketType> ticketTypes;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
    private List<BarProduct> barProducts;

    @ManyToOne
    private User admin;

    @OneToMany(mappedBy = "theaterCashier", cascade = CascadeType.ALL)
    private List<User> cashiers;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
    private List<Device> devices;
}
