package com.trg.vehicles.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trg.vehicles.client.MapClient;
import com.trg.vehicles.client.PriceClient;
import com.trg.vehicles.entity.car.Car;
import com.trg.vehicles.exception.CarException;
import com.trg.vehicles.repository.CarRepository;

@Service
public class CarService {
    private CarRepository carRepository;
    private MapClient mapClient;
    private PriceClient priceClient;

    public CarService(CarRepository carRepository, MapClient mapClient, PriceClient priceClient) {
        this.carRepository = carRepository;
        this.mapClient = mapClient;
        this.priceClient = priceClient;
    }

    public List<Car> findAll() {
        return carRepository.findAll().stream().map(car -> {
            car.setPrice(priceClient.getPrice(car.getId()));
            car.setLocation(mapClient.getAddress(car.getLocation()));
            return car;
        }).toList();
    }

    public Car save(Car car) {
        if (car.getId() != null) {
            return carRepository.findById(car.getId()).map(carToBeUpdated -> {
                carToBeUpdated.setCondition(car.getCondition());
                carToBeUpdated.setDetails(car.getDetails());
                carToBeUpdated.setLocation(car.getLocation());
                return carRepository.save(carToBeUpdated);
            }).orElseThrow(() -> new CarException("Car with id " + car.getId() + " not found"));
        }
        return carRepository.save(car);
    }

    public Car findById(Long id) {
        return carRepository.findById(id).map(car -> {
            car.setPrice(priceClient.getPrice(car.getId()));
            car.setLocation(mapClient.getAddress(car.getLocation()));
            return car;
        }).orElseThrow(() -> {
            throw new CarException("Car with id " + id + " not found");
        });
    }

    public void deleteById(Long id) {
        if (!carRepository.findById(id).isPresent()) {
            throw new CarException("Car with id " + id + " not found");
        }
        carRepository.deleteById(id);
    }

}
