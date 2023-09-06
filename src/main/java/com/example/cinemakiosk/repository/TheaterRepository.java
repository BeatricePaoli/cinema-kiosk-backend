package com.example.cinemakiosk.repository;

import com.example.cinemakiosk.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {

    List<Theater> findByName(String name);

    @Query("SELECT t FROM Theater t " +
            "JOIN FETCH t.address " +
            "JOIN FETCH t.bar " +
            "JOIN FETCH t.admin " +
            "WHERE t.admin.username = :username AND (t.deleted = FALSE OR t.deleted IS NULL)")
    List<Theater> findByAdmin(String username);

    @Query("SELECT t FROM Theater t " +
            "JOIN FETCH t.address " +
            "JOIN FETCH t.bar " +
            "JOIN FETCH t.admin " +
            "WHERE t.deleted = FALSE OR t.deleted IS NULL")
    List<Theater> findAllWithFetch();

    @Query("SELECT DISTINCT t FROM Show s " +
            "JOIN s.movie m " +
            "JOIN s.screen.theater t " +
            "JOIN FETCH t.address " +
            "WHERE s.date >= CURRENT_DATE " +
            "AND (:movieId IS NULL OR m.id = :movieId) " +
            "AND (t.deleted = FALSE OR t.deleted IS NULL)")
    List<Theater> findWithFutureShow(Long movieId);

    @Query("SELECT new java.lang.Boolean(count(*) > 0) " +
            "FROM Theater t  " +
            "WHERE t.id = :id AND t.admin.username = :username")
    Boolean theaterBelongsToAdmin(String username, Long id);
}
