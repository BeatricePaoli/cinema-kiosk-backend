package com.example.cinemakiosk.repository;

import com.example.cinemakiosk.model.DeviceActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceActivityRepository extends JpaRepository<DeviceActivity, Long> {

    @Query(value = "SELECT * FROM device_activity a " +
            "JOIN device d ON d.id = a.smart_band_id " +
            "WHERE d.context_broker_id = :id " +
            "ORDER BY a.tms DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<DeviceActivity> findLastBySmartBandId(String id);
}
