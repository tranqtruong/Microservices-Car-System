package com.trg.prices.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.trg.prices.entity.Price;
import com.trg.prices.exception.PriceException;
import com.trg.prices.repository.PriceRepository;

@Service
public class PriceService {
    private PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Price getPrice(Long vehicleId) {
        Optional<Price> price = priceRepository.findById(vehicleId);
        if (!price.isPresent()) {
            throw new PriceException("Price not found");
        }
        return price.get();
    }
}
