package com.example.Carfinder.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String name;

    String type;
    Integer price;
    Integer year;
    String mileage;
    String reg;
    String gearBox;
    String dealer;

    public Car(String name, String type, Integer price, Integer year, String mileage, String reg, String gearBox, String dealer) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.year = year;
        this.mileage = mileage;
        this.reg = reg;
        this.gearBox = gearBox;
        this.dealer = dealer;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public Integer getPrice() {
        return price;
    }

    public Integer getYear() {
        return year;
    }

    public String getMileage() {
        return mileage;
    }

    public String getReg() {
        return reg;
    }

    public String getGearBox() {
        return gearBox;
    }

    public String getDealer() {
        return dealer;
    }
}
