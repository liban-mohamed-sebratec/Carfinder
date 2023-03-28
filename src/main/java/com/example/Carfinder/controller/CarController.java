package com.example.Carfinder.controller;

import com.example.Carfinder.models.Car;
import com.example.Carfinder.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/car/v1")
@EnableScheduling
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping("/search")
    public List<Car> getCars() throws IOException, InterruptedException {
        return carService.getCars();
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

    @GetMapping("list/{city}")
    public List<Car> addCarToList(@PathVariable("city") String city){
        return carService.addCarsToList(city);
    }

}
