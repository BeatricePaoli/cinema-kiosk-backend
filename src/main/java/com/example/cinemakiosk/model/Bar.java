package com.example.cinemakiosk.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "bar")
public class Bar extends Room {

    @OneToMany(mappedBy = "bar", cascade = CascadeType.ALL)
    private List<BarProduct> barProducts;
}
