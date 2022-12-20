package com.example.cardealer.domain.repositories;

import com.example.cardealer.domain.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT c FROM Car c WHERE make = :make ORDER BY model, travelledDistance DESC")
    List<Car> findByMakeOrderByModelThenByTravelledDistanceDesc(@Param(value = "make") String make);
}
