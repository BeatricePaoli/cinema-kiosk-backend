package com.example.cinemakiosk.repository;

import com.example.cinemakiosk.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {

    @Query("FROM Screen s WHERE s.theater.id = :id")
    List<Screen> findByTheaterId(Long id);
}
