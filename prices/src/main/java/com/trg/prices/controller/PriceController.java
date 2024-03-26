package com.trg.prices.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.trg.prices.entity.Price;
import com.trg.prices.exception.PriceException;
import com.trg.prices.service.PriceService;

@RestController
@RequestMapping("/prices")
public class PriceController {
    private PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/{vehicleId}")
    public Price getPrice(@PathVariable Long vehicleId) {
        try {
            return priceService.getPrice(vehicleId);
        } catch (PriceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
