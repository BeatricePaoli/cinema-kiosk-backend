package com.example.cinemakiosk.repository;

import com.example.cinemakiosk.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    @Query("SELECT s FROM Show s " +
            "JOIN s.movie m " +
            "JOIN s.screen.theater t " +
            "WHERE s.date >= CURRENT_DATE " +
            "AND m.id = :movieId " +
            "AND t.id = :theaterId " +
            "AND t.address.city LIKE '%' || :city || '%' ")
    List<Show> search(Long movieId, String city, Long theaterId);
}
