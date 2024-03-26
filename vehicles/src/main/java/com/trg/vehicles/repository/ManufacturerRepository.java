package com.trg.vehicles.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trg.vehicles.entity.Manufacturer;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {

}
