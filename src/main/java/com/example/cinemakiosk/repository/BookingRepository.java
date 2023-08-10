package com.example.cinemakiosk.repository;

import com.example.cinemakiosk.dto.SeatTakenDto;
import com.example.cinemakiosk.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT new com.example.cinemakiosk.dto.SeatTakenDto(sh.id, s.id, s.row, s.col) " +
            "FROM Booking b JOIN b.show sh JOIN b.seats s " +
            "WHERE sh.id IN (:showIds) " +
            "AND b.status != 'CANCELED'")
    List<SeatTakenDto> getSeatsTaken(List<Long> showIds);
}
