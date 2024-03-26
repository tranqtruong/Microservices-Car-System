package com.trg.vehicles.client;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.trg.vehicles.entity.car.Location;
import com.trg.vehicles.payload.Address;

@Component
public class MapClient {
    private final WebClient webClient;
    private final ModelMapper modelMapper;

    public MapClient(WebClient webClient, ModelMapper modelMapper) {
        this.webClient = webClient;
        this.modelMapper = modelMapper;
    }

    public Location getAddress(Location location) {
        try {
            Address address = webClient.get()
                    .uri("http://localhost:8080/maps?lat={lat}&lon={lon}", location.getLat(), location.getLon())
                    .retrieve()
                    .bodyToMono(Address.class)
                    .block();

            Location mappedLocation = modelMapper.map(address, Location.class);
            mappedLocation.setLat(location.getLat());
            mappedLocation.setLon(location.getLon());
            return mappedLocation;
        } catch (Exception e) {
            // add logging here
        }
        return location;
    }

}
