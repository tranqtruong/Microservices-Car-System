package com.trg.vehicles.entity.car;

import com.trg.vehicles.entity.Manufacturer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Details {
    @NotBlank
    private String body;

    @NotBlank
    private String model;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Manufacturer manufacturer;

    private Integer numberOfDoors;

    private String fuelType;

    private String engine;

    private Integer mileage;

    private Integer modelYear;

    private Integer productionYear;

    private String externalColor;
}
