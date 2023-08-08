package com.example.cinemakiosk.repository;

import com.example.cinemakiosk.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {

    @Query("SELECT DISTINCT t FROM Show s " +
            "JOIN s.movie m " +
            "JOIN s.screen.theater t " +
            "WHERE s.date >= CURRENT_DATE " +
            "AND (:movieId IS NULL OR m.id = :movieId)")
    List<Theater> findWithFutureShow(Long movieId);
}
