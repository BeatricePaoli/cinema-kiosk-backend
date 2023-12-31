package com.example.cinemakiosk.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue(DeviceType.CASHREGISTER)
public class CashRegister extends Device {
}
