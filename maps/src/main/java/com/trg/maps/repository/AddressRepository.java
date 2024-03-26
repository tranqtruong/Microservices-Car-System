package com.trg.maps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trg.maps.entity.Address;

/**
 * AddressRepository
 */
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
