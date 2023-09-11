package com.example.cinemakiosk.repository;

import com.example.cinemakiosk.model.DeviceActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceActivityRepository extends JpaRepository<DeviceActivity, Long> {

    @Query(value = "SELECT a.id as id, a.tms as tms, a.emitter_serial as emitter_serial, " +
            "a.event_code as event_code, a.quantity as quantity, a.booking_id as booking_id, " +
            "a.product_id as product_id, a.smart_band_id as smart_band_id " +
            "FROM device_activity a " +
            "JOIN device d ON d.id = a.smart_band_id " +
            "WHERE d.context_broker_id = :id " +
            "ORDER BY a.tms DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<DeviceActivity> findLastBySmartBandId(String id);

    @Query("FROM DeviceActivity da " +
            "JOIN FETCH da.smartBand " +
            "JOIN FETCH da.booking b " +
            "JOIN FETCH b.seats " +
            "JOIN FETCH b.show " +
            "JOIN FETCH b.show.movie " +
            "JOIN FETCH b.show.screen " +
            "JOIN FETCH b.show.screen.theater " +
            "JOIN FETCH b.show.screen.theater.address " +
            "LEFT JOIN FETCH b.show.screen.theater.bar " +
            "JOIN FETCH b.show.screen.theater.admin " +
            "JOIN FETCH b.user " +
            "WHERE da.smartBand.id = :id " +
            "ORDER BY da.tms DESC ")
    List<DeviceActivity> findBySmartbandId(Long id);
}
