package com.example.Carfinder.service;

import com.example.Carfinder.models.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Timer;

@Service
public class BevakningService {
    String a;
    String b;
    Integer c;
    @Autowired
    CarService carService;

    public List<Car> getBevakning(String bevakningBrand, String bevakningModel, Integer maxPrice) throws IOException {
        a = bevakningBrand;
        b = bevakningModel;
        c = maxPrice;
        return carService.getBevakning(bevakningBrand, bevakningModel, maxPrice);
    }

    public void setTimer() throws IOException {
        LocalTime time = LocalTime.now();
        LocalTime i = LocalTime.now();
        while(time.getMinute() < i.getMinute() + 10){
            time = LocalTime.now();
        }
        getBevakning(a,b,c);
    }
}
