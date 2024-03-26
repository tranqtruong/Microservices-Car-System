package com.trg.prices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trg.prices.entity.Price;

public interface PriceRepository extends JpaRepository<Price, Long> {

}
