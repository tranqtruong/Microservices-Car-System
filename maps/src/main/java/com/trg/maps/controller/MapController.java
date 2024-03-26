package com.trg.maps.controller;

import org.springframework.web.bind.annotation.RestController;

import com.trg.maps.entity.Address;
import com.trg.maps.service.AddressService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/maps")
public class MapController {
    private AddressService addressService;

    public MapController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public Address getAddress(@RequestParam Double lat, @RequestParam Double lon) {
        return addressService.findAddressById((int) (Math.random() * 50 + 1));
    }

}
