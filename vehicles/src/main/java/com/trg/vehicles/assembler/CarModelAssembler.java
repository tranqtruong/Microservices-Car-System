package com.trg.vehicles.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;

import com.trg.vehicles.controller.CarController;
import com.trg.vehicles.entity.car.Car;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class CarModelAssembler implements RepresentationModelAssembler<Car, EntityModel<Car>> {

    @Override
    public EntityModel<Car> toModel(Car car) {
        return EntityModel.of(car,
                linkTo(methodOn(CarController.class).getCar(car.getId())).withSelfRel(),
                linkTo(methodOn(CarController.class).getCars()).withRel("cars"));
    }

}
