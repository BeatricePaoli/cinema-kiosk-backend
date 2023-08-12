package com.example.cinemakiosk.repository;

import com.example.cinemakiosk.model.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {

    @Query("SELECT t FROM TicketType t WHERE t.theater.id = :theaterId")
    List<TicketType> getByTheater(Long theaterId);

}
