package com.example.cinemakiosk.repository;

import com.example.cinemakiosk.model.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieGenreRepository extends JpaRepository<MovieGenre, Long> {

    List<MovieGenre> getByName(String name);
}
