package com.example.cinemakiosk.repository;

import com.example.cinemakiosk.dto.DeviceInterface;
import com.example.cinemakiosk.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    @Query(value = "SELECT d.id as id, d.context_broker_id as contextBrokerId, (" +
            "SELECT CASE WHEN da.event_code <> 'DEACTIVATION' THEN false ELSE true END " +
            "FROM device_activity da " +
            "WHERE da.smart_band_id = d.id " +
            "ORDER BY da.tms DESC " +
            "LIMIT 1" +
            ") as isActive " +
            "FROM device d " +
            "WHERE d.theater_id = :theaterId AND d.type = :type " +
            "AND (d.deleted IS NULL OR d.deleted = FALSE)", nativeQuery = true)
    List<DeviceInterface> search(Long theaterId, String type);

    @Query(value = "SELECT d.id as id, d.context_broker_id as contextBrokerId, (" +
            "SELECT CASE WHEN da.event_code <> 'DEACTIVATION' THEN false ELSE true END " +
            "FROM device_activity da " +
            "WHERE da.smart_band_id = d.id " +
            "ORDER BY da.tms DESC " +
            "LIMIT 1" +
            ") as isActive " +
            "FROM device d " +
            "WHERE d.id = :id", nativeQuery = true)
    Optional<DeviceInterface> findDtoById(Long id);
}
