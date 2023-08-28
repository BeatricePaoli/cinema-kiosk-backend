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

    private Integer quantity;

    @ManyToOne
    private DeviceActivity deviceActivity;

    @ManyToOne
    private BarProduct product;
}
