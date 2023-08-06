package com.example.cinemakiosk.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "ticket_type")
public class TicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private Double price;

    @Enumerated(EnumType.STRING)
    private ProjectionType projectionType;

    private Boolean availableOnline;

    @ElementCollection(targetClass = Day.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "days", joinColumns = @JoinColumn(name = "ticket_type_id"))
    @Column(name = "day", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<Day> days;

    @ManyToOne
    private Theater theater;
}
