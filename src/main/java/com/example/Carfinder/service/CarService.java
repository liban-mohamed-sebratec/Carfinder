package com.example.Carfinder.service;

import com.example.Carfinder.models.Car;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;

@Service
public class CarService {
    List<Car> cars = new ArrayList<>();
    public List<Car> getCars() throws IOException, InterruptedException {
        cars.clear();

        String url = "https://bilweb.se/sok/" + "Volvo" + "/" + "v90" + "?limit=1000";
        Element doc = Jsoup.connect(url).get();
        String amountStr = doc.getElementsByClass("sectionHeading Heading--h2 u-inline").select("span").text().replace(" ", "");
        int amount = Integer.parseInt(amountStr);
        System.out.println(amount);
        Elements carNameDescription = doc.getElementsByClass("Card-heading").select("a");
        Elements section = doc.getElementsByClass("Card");
        System.out.println(carNameDescription.size());
        for (int i = 0; i <= section.size() - 1 ; i++){
            if (carNameDescription.get(i).text().contains("Volvo")) {
                String link = doc.getElementsByClass("go_to_detail").get(i).select("a").attr("href");
                Document CarDoc = Jsoup.connect(link).get();
                String carName = CarDoc.getElementsByClass("u-hidden u-md-block viewH1 u-marginTsm u-marginBz").select("h2").text();
                String regString = CarDoc.getElementsByClass("u-marginAz u-textWeightLight").select("p").text();
                String reg = regString.substring(0,6);
                String type = CarDoc.getElementsByClass("u-marginAz u-textWeightLight").get(3).select("a").text();
                String gearBox = CarDoc.getElementsByClass("u-marginAz u-textWeightLight").get(4).select("p").text();
                String carDealer = CarDoc.getElementsByClass("viewLabel").select("strong").text();
                String carCity = CarDoc.getElementsByClass("List-item u-textWeightLight u-textLarge").select("li").get(1).text();
                Integer carYear = null;

                for (Element a : CarDoc.getElementsByClass("u-textXSmall u-textWeightMedium u-marginBz")) {
                    if (a.select("h5").text().contains("Årsmodell")) {

                        carYear = Integer.valueOf(a.nextElementSibling().text());

                    }
                }

                Elements price = CarDoc.getElementsByClass("viewPrice").get(0).select("span");
                String a = price.text().replace(" kr", "");


                Integer carPrice = null;
                if (a.contains(" ")) {
                    carPrice = Integer.valueOf(a.replace(" ", ""));
                }

                Elements loop = CarDoc.getElementsByClass("u-marginAz u-textWeightLight").select("p");
                String carOwners = null;
                String carMilage = null;

                for (Element item : loop) {
                    if (item.previousElementSibling().text().contains("Antal ägare")) {
                        carOwners = item.text();
                    }
                    if (item.previousElementSibling().text().contains("Mil")) {
                        carMilage = item.text();
                    }
                }

                Car carEntity = new Car(carName, type, carPrice, carYear, carMilage, reg, gearBox, carDealer, carCity);
                if (carEntity.getPrice() != null ){
                    cars.add(carEntity);
                }

            }
        }
        System.out.println("Amount of cars scraped: " + cars.size());
        return cars;
    }
    public List<Car> getCheapestCar() {
        return cars.stream()
                 .sorted(comparing(Car::getPrice, comparing(Math::abs)))
                 .limit(3)
                 .toList();
    }

    public Integer getAvgPrice() {
        Integer sum = 0;

        for (Car i : cars){
            sum += i.getPrice();
        }
        return sum / cars.size();
    }

    public Integer getAvgPricePerYear(Integer year) {
        Integer sum = 0;
        ArrayList<Car> carList = new ArrayList<>();

        for (Car car : cars){
            if (car.getYear().equals(year) && car.getPrice() != null){
                sum += car.getPrice();
                carList.add(car);

            }
        }

        return sum / carList.size();
    }

    public List<Car> addCarsToList(String city){
        ArrayList<Car> newList = new ArrayList<>();
        for (Car car : cars){
            if (car.getYear() == 2019 && car.getCity().toLowerCase().contains(city)){
                newList.add(car);
            }
        }
        return newList;
    }


}

