package com.example.cinemakiosk.repository;

import com.example.cinemakiosk.model.DeviceActivity;
import com.example.cinemakiosk.model.DeviceActivityEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
            "LEFT JOIN FETCH da.product " +
            "WHERE da.smartBand.id = :id " +
            "ORDER BY da.tms DESC ")
    List<DeviceActivity> findBySmartbandId(Long id);

    @Query("SELECT new java.lang.Boolean(count(*) > 0) " +
            "FROM DeviceActivity da " +
            "WHERE da.smartBand.contextBrokerId = :id AND da.tms = :tms AND da.eventCode = :event " +
            "AND ((:emitterSerial IS NULL AND da.emitterSerial IS NULL) OR da.emitterSerial = :emitterSerial) " +
            "AND ((:productId IS NULL AND da.product.id IS NULL) OR da.product.id = :productId) " +
            "AND ((:quantity IS NULL AND da.quantity IS NULL) OR da.quantity = :quantity)")
    Boolean isAlreadyPresent(String id, Date tms, DeviceActivityEvent event, String emitterSerial, Long productId, Integer quantity);
}
