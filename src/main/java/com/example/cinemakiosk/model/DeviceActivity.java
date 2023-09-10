package com.example.cinemakiosk.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "device_activity")
public class DeviceActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private SmartBand smartBand;

    @Temporal(TemporalType.TIMESTAMP)
    private Date tms;

    private String emitterSerial;

    @Enumerated(EnumType.STRING)
    private DeviceActivityEvent eventCode;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    private Integer quantity;

    @ManyToOne
    private BarProduct product;
}
