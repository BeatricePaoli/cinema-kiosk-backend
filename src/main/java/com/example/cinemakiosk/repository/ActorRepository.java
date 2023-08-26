package com.example.cinemakiosk.repository;

import com.example.cinemakiosk.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    @Query("SELECT a.img FROM Actor a WHERE a.id = :id")
    byte[] findImgByActorId(Long id);
}
