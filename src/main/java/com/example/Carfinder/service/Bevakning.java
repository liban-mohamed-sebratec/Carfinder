package com.example.Carfinder.service;

import com.example.Carfinder.models.Car;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Bevakning {
    LocalTime lastFetched;
    LocalTime currentTime;
    List<Car> carList = new ArrayList<>();

    public Bevakning() {

    }

    public LocalTime getLastFetched() {
        return lastFetched;
    }

    public LocalTime getCurrentTime() {
        return currentTime;
    }

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
        lastFetched = LocalTime.now();
    }
}
