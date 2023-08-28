package com.example.cinemakiosk.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "device_activity")
public class DeviceActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String deviceId;
    private Boolean hasEnteredBar;
    private Boolean hasEnteredWrongRoom;
    private Boolean hasLeftTheater;

    @Temporal(TemporalType.TIMESTAMP)
    private Date activationTms;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deActivationTms;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateTms;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bar_order_id", referencedColumnName = "id")
    private BarOrder barOrder;
}
