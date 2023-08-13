package com.example.cinemakiosk.repository;

import com.example.cinemakiosk.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    @Query("SELECT s FROM Seat s WHERE s.screen.id = :screenId")
    List<Seat> findByScreen(Long screenId);
}
