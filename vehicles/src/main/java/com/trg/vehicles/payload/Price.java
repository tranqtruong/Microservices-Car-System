package com.trg.vehicles.payload;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Price {
    private Long vehicleId;
    private String currency;
    private BigDecimal price;
}
