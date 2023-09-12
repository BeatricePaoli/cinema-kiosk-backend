package com.example.cinemakiosk.repository;

import com.example.cinemakiosk.model.BarProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarProductRepository extends JpaRepository<BarProduct, Long> {

    @Query("FROM BarProduct p " +
            "JOIN FETCH p.theater " +
            "JOIN FETCH p.theater.address " +
            "LEFT JOIN FETCH p.theater.bar " +
            "JOIN FETCH p.theater.admin " +
            "WHERE p.productCode = :productCode AND p.theater.id = :id")
    List<BarProduct> findByProductCodeAndTheater(String productCode, Long id);
}
