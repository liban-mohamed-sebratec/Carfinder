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

    @GetMapping("/{carName}/{carModel}")
    public List<Car> getCars(@PathVariable("carName") String carName, @PathVariable("carModel") String carModel) throws IOException {
        return carService.getCars(carName, carModel);
    }

    @GetMapping("/{carName}/{carModel}/cheapest")
    public List<Car> getCheapestCar() throws IOException {
        return carService.getCheapestCar();
    }

    @GetMapping("/{carName}/{carModel}/avg-price")
    public Integer getAvgPrice(){
        return carService.getAvgPrice();
    }

    @GetMapping("/{carName}/{carModel}/{year}/avg-price")
    public Integer getAvgPricePerYear(@PathVariable("year") Integer year){
        return carService.getAvgPricePerYear(year);
    }


}
