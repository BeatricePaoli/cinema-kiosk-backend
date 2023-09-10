package com.example.cinemakiosk.repository;

import com.example.cinemakiosk.dto.SeatTakenDto;
import com.example.cinemakiosk.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT new com.example.cinemakiosk.dto.SeatTakenDto(sh.id, s.id, s.row, s.col) " +
            "FROM Booking b JOIN b.show sh JOIN b.seats s " +
            "WHERE sh.id IN (:showIds) " +
            "AND b.status != 'CANCELED'")
    List<SeatTakenDto> getSeatsTaken(List<Long> showIds);

    @Query("FROM Booking b JOIN FETCH b.seats " +
            "JOIN FETCH b.show " +
            "JOIN FETCH b.show.movie " +
            "JOIN FETCH b.show.screen " +
            "JOIN FETCH b.show.screen.theater " +
            "JOIN FETCH b.show.screen.theater.address " +
            "LEFT JOIN FETCH b.show.screen.theater.bar " +
            "JOIN FETCH b.show.screen.theater.admin " +
            "JOIN FETCH b.user " +
            "WHERE b.id = :id AND b.status != 'CANCELED'")
    Optional<Booking> findByIdWithFetch(Long id);

    @Query("FROM Booking b JOIN FETCH b.seats " +
            "JOIN FETCH b.show " +
            "JOIN FETCH b.show.movie " +
            "JOIN FETCH b.show.screen " +
            "JOIN FETCH b.show.screen.theater " +
            "JOIN FETCH b.show.screen.theater.address " +
            "LEFT JOIN FETCH b.show.screen.theater.bar " +
            "JOIN FETCH b.show.screen.theater.admin " +
            "JOIN FETCH b.user " +
            "WHERE b.user.username = :username AND b.status != 'CANCELED'")
    List<Booking> getBookings(String username);
}
