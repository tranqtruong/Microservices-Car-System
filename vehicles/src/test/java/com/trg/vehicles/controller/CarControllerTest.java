package com.trg.vehicles.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.trg.vehicles.client.MapClient;
import com.trg.vehicles.client.PriceClient;
import com.trg.vehicles.entity.Manufacturer;
import com.trg.vehicles.entity.car.Car;
import com.trg.vehicles.entity.car.Condition;
import com.trg.vehicles.entity.car.Details;
import com.trg.vehicles.entity.car.Location;
import com.trg.vehicles.services.CarService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<Car> json;

    @MockBean
    private CarService carService;

    @MockBean
    private MapClient mapClient;

    @MockBean
    private PriceClient priceClient;

    private Car getCar() {
        Car car = new Car();
        car.setId(1L);
        car.setLocation(new Location(12.34, 56.78));
        car.setCondition(Condition.NEW);

        Details details = new Details();
        details.setBody("sedan");
        details.setEngine("2.0L");
        details.setExternalColor("white");
        details.setFuelType("petrol");
        details.setManufacturer(new Manufacturer(101, "Toyota"));
        details.setMileage(12345);
        details.setModel("Corolla");
        details.setModelYear(2019);
        details.setNumberOfDoors(4);
        details.setProductionYear(2019);
        car.setDetails(details);
        return car;
    }

    private Location getLocation(Location location) {
        location.setAddress("123 Main St");
        location.setCity("AnyTown");
        location.setState("CA");
        location.setZip("12345");
        return location;
    }

    private String getPrice() {
        return "USD 10000";
    }

    @BeforeEach
    void setup() {
        Car car = getCar();

        given(mapClient.getAddress(any())).willReturn(getLocation(car.getLocation()));
        given(priceClient.getPrice(any())).willReturn(getPrice());

        given(carService.save(any())).willReturn(car);
        given(carService.findById(anyLong())).willAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            car.setId(id);
            car.setLocation(mapClient.getAddress(car.getLocation()));
            car.setPrice(priceClient.getPrice(car.getId()));
            return car;
        });

        given(carService.findAll()).willAnswer(invocation -> {
            car.setLocation(mapClient.getAddress(car.getLocation()));
            car.setPrice(priceClient.getPrice(car.getId()));
            return Collections.singletonList(car);
        });

    }

    @Test
    void testFindAll() throws Exception {
        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"));

        verify(carService, times(1)).findAll();
    }

    @Test
    void testFindById() throws Exception {
        mockMvc.perform(get("/cars/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"));

        verify(carService, times(1)).findById(anyLong());
        verify(mapClient, times(1)).getAddress(any());
        verify(priceClient, times(1)).getPrice(any());
    }

    @Test
    void testSave() throws Exception {
        Car car = getCar();
        car.setId(null);

        mockMvc.perform(post("/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.write(car).getJson()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/hal+json"));

        verify(carService, times(1)).save(any());
    }

    @Test
    void testDeleteById() throws Exception {
        mockMvc.perform(delete("/cars/1"))
                .andExpect(status().isNoContent());

        verify(carService, times(1)).deleteById(anyLong());
    }
}
