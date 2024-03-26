package com.trg.vehicles.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.trg.vehicles.payload.Price;

@Component
public class PriceClient {
    private final WebClient webClient;

    public PriceClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public String getPrice(Long vehicleId) {
        try {
            Price price = webClient.get()
                    .uri("http://localhost:8081/prices/{vehicleId}", vehicleId)
                    .retrieve()
                    .bodyToMono(Price.class)
                    .block();

            return String.format("%s %s", price.getCurrency(), price.getPrice());
        } catch (Exception e) {
            // add logging here
        }

        return "(consult price)";
    }

}
