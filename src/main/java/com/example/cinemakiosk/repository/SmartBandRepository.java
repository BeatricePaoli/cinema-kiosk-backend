package com.example.cinemakiosk.repository;

import com.example.cinemakiosk.model.SmartBand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmartBandRepository extends JpaRepository<SmartBand, Long> {

    @Query("FROM SmartBand s WHERE s.theater.id = :id")
    List<SmartBand> findByTheaterId(Long id);
}
