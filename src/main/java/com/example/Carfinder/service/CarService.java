package com.example.Carfinder.service;

import com.example.Carfinder.models.Car;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;;

@Service
public class CarService {

    List<Car> cars = new ArrayList<>();
    public List<Car> getCars(String car) throws IOException {

        String url = "https://bilweb.se/sok/" + car + "?type=1";
        Document doc = Jsoup.connect(url).get();
        Elements carNameDescription = doc.getElementsByClass("Card-heading").select("a");
        Elements carDealer = doc.getElementsByClass("Card-firm").select("span");
        Elements price = doc.getElementsByClass("Card-price").select("p");
        Elements section = doc.getElementsByClass("Card");



        for (int i = 1; i < section.size(); i++){
            if (carNameDescription.get(i).text().contains(car)){
                String link = doc.getElementsByClass("go_to_detail").get(i).select("a").attr("href");
                Document CarDoc = Jsoup.connect(link).get();
                String carName = CarDoc.getElementsByClass("u-hidden u-md-block viewH1 u-marginTsm u-marginBz").select("h2").text();
                String regString = CarDoc.getElementsByClass("u-marginAz u-textWeightLight").select("p").text();
                String reg = regString.substring(0,6);
                String type = CarDoc.getElementsByClass("u-marginAz u-textWeightLight").get(3).select("a").text();
                String gearBox = CarDoc.getElementsByClass("u-marginAz u-textWeightLight").get(4).select("p").text();
                String carYear = CarDoc.getElementsByClass("u-marginAz u-textWeightLight").get(6).select("p").text();
                String a = price.get(i).text().replace(" kr", "");
                Integer carPrice = Integer.parseInt(a.replace(" ", ""));


                Elements loop = CarDoc.getElementsByClass("u-marginAz u-textWeightLight").select("p");
                String carOwners = null;
                String carMilage = null;

                for (Element item : loop){
                    if (item.previousElementSibling().text().contains("Antal ägare")){
                        carOwners = item.text();
                    }
                    if (item.previousElementSibling().text().contains("Mil")){
                        carMilage = item.text();
                    }
                }

                Car carEntity = new Car(carName, type, carPrice, carYear, carMilage, reg, gearBox, carDealer.text() );
                cars.add(carEntity);
            }

        }
        return cars;
    }
    public List<Car> getCheapestCar(String carName) throws IOException {
       List<Car>cheapestCars = getCars(carName).stream()
                .sorted(Comparator.comparing(Car::getPrice, Comparator.comparing(Math::abs)))
                .limit(1)
                .toList();
        return cheapestCars;
    }

    }

