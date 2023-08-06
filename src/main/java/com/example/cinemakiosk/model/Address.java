package com.example.cinemakiosk.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "address")
    private Theater theater;

    private String street;
    private String number;
    private String city;
    private String country;
    private String zipCode;
}
