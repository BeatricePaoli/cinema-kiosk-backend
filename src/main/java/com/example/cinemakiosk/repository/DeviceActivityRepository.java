package com.example.cinemakiosk.repository;

import com.example.cinemakiosk.model.DeviceActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceActivityRepository extends JpaRepository<DeviceActivity, Long> {

    @Query("FROM DeviceActivity d WHERE d.smartBand.contextBrokerId = :id AND d.deActivationTms IS NULL")
    List<DeviceActivity> findActiveBySmartBandId(String id);
}
