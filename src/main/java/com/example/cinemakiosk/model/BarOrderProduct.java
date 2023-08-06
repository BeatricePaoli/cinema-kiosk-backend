package com.example.cinemakiosk.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bar_order_product")
public class BarOrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer quantity;

    @ManyToOne
    private BarOrder barOrder;

    @ManyToOne
    private BarProduct product;

}
