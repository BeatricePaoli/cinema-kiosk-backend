package com.example.cinemakiosk.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue(DeviceType.SMARTBAND)
public class SmartBand extends Device {

    private Boolean deleted;

    @OneToMany(mappedBy = "smartBand", cascade = CascadeType.ALL)
    private List<DeviceActivity> activities;
}
