package com.trg.vehicles.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trg.vehicles.assembler.CarModelAssembler;
import com.trg.vehicles.entity.car.Car;
import com.trg.vehicles.services.CarService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@Tag(name = "CRUD operations for Car")
@RequestMapping("/cars")
public class CarController {

    private CarService carService;
    private CarModelAssembler assembler;

    public CarController(CarService carService, CarModelAssembler assembler) {
        this.carService = carService;
        this.assembler = assembler;
    }

    @PostMapping
    public ResponseEntity<?> createCar(@Valid @RequestBody Car car) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assembler.toModel(carService.save(car)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCar(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(carService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<?> getCars() {
        List<EntityModel<Car>> cars = carService.findAll().stream()
                .map(assembler::toModel).collect(Collectors.toList());

        CollectionModel<EntityModel<Car>> carResources = CollectionModel.of(cars,
                linkTo(methodOn(CarController.class).getCars()).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(carResources);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCar(@PathVariable Long id, @Valid @RequestBody Car car) {
        car.setId(id);
        return ResponseEntity.status(HttpStatus.OK).body(assembler.toModel(carService.save(car)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
