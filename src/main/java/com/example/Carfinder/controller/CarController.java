package com.example.Carfinder.controller;

import com.example.Carfinder.models.Car;
import com.example.Carfinder.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/car/v1")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping("/{carName}")
    public List<Car> getCars(@PathVariable("carName") String carName) throws IOException {
        return carService.getCars(carName);
    }

    @GetMapping("/{carName}/cheapest")
    public List<Car> getCheapestCar(@PathVariable("carName") String carName) throws IOException {
        return carService.getCheapestCar(carName);
    }

}
