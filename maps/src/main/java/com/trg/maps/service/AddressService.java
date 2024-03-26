package com.trg.maps.service;

import org.springframework.stereotype.Service;

import com.trg.maps.entity.Address;
import com.trg.maps.repository.AddressRepository;

@Service
public class AddressService {
    private AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address findAddressById(Integer id) {
        return addressRepository.findById(id).get();
    }
}
