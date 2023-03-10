package com.example.Carfinder.controller;

import com.example.Carfinder.models.Car;
import com.example.Carfinder.service.BevakningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("bevakning")
public class BevakningController {
    @Autowired
    BevakningService bevakningService;

    @GetMapping("/create/{brand}/{model}/{price}")
    public List<Car> getBevakning(@PathVariable("brand") String bevakningBrand, @PathVariable("model") String bevakningModel, @PathVariable("price") Integer maxPrice) throws IOException {
        return bevakningService.getBevakning(bevakningBrand, bevakningModel, maxPrice);
    }

    @GetMapping("set")
    public void setTimer() throws IOException {
        bevakningService.setTimer();
    }
}
