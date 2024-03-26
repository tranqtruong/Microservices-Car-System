package com.trg.vehicles.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trg.vehicles.entity.car.Car;

public interface CarRepository extends JpaRepository<Car, Long> {

}
