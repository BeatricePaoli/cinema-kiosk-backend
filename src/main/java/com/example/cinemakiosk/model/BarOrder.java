package com.example.cinemakiosk.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "bar_order")
public class BarOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date purchaseTms;

    @OneToOne(mappedBy = "barOrder")
    private DeviceActivity deviceActivity;

    @OneToMany(mappedBy = "barOrder", cascade = CascadeType.ALL)
    private List<BarOrderProduct> barOrderProducts;
}
