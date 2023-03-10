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

    int counter = 1;
    @Autowired
    BevakningService bevakningService;
    List<Car> cars = new ArrayList<>();
    List<Car> bevakningar = new ArrayList<>();
    public List<Car> getCars(String car, String carModel) throws IOException {

        String url = "https://bilweb.se/sok/" + car + "/" + carModel + "?limit=1000";
        Document doc = Jsoup.connect(url).get();
        Elements carNameDescription = doc.getElementsByClass("Card-heading").select("a");
        Elements carDealer = doc.getElementsByClass("Card-firm").select("span");
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
                Integer carYear = null;

                for(Element a : CarDoc.getElementsByClass("u-textXSmall u-textWeightMedium u-marginBz")){
                    if(a.select("h5").text().contains("Årsmodell")){
                       carYear = Integer.valueOf(a.nextElementSibling().text());
                    }
                }

                Elements price = CarDoc.getElementsByClass("viewPrice").get(0).select("span");
                String a = price.text().replace(" kr", "");
                Integer carPrice = null;

                if(a.contains(" ")){
                    carPrice = Integer.valueOf(a.replace(" ", ""));
                }


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
        List<Car> carList = new ArrayList<>();
        for (Car car : cars){
            if (car.getYear().equals(year)){
                sum += car.getPrice();
                carList.add(car);
            }
        }

        return sum / carList.size();
    }

    public List<Car> getBevakning(String bevakningBrand, String bevakningModel, Integer maxPrice) throws IOException {
        String url = "https://bilweb.se/sok/" + bevakningBrand + "/" + bevakningModel + "?limit=1000";
        Document doc = Jsoup.connect(url).get();
        Elements carNameDescription = doc.getElementsByClass("Card-heading").select("a");
        Elements carDealer = doc.getElementsByClass("Card-firm").select("span");
        Elements section = doc.getElementsByClass("Card");



        for (int i = 1; i < section.size(); i++){
            if (carNameDescription.get(i).text().contains(bevakningBrand)){
                String link = doc.getElementsByClass("go_to_detail").get(i).select("a").attr("href");
                Document CarDoc = Jsoup.connect(link).get();
                String carName = CarDoc.getElementsByClass("u-hidden u-md-block viewH1 u-marginTsm u-marginBz").select("h2").text();
                String regString = CarDoc.getElementsByClass("u-marginAz u-textWeightLight").select("p").text();
                String reg = regString.substring(0,6);
                String type = CarDoc.getElementsByClass("u-marginAz u-textWeightLight").get(3).select("a").text();
                String gearBox = CarDoc.getElementsByClass("u-marginAz u-textWeightLight").get(4).select("p").text();
                Integer carYear = null;

                for(Element a : CarDoc.getElementsByClass("u-textXSmall u-textWeightMedium u-marginBz")){
                    if(a.select("h5").text().contains("Årsmodell")){
                        carYear = Integer.valueOf(a.nextElementSibling().text());
                    }
                }

                Elements price = CarDoc.getElementsByClass("viewPrice").get(0).select("span");
                String a = price.text().replace(" kr", "");
                Integer carPrice = null;

                if(a.contains(" ")){
                    carPrice = Integer.valueOf(a.replace(" ", ""));
                }


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
       for (Car car : cars){
           if (car.getPrice() < maxPrice ){
               System.out.println(car.getPrice());
               bevakningar.add(car);
           }
       }
//       Bevakning bevakning = new Bevakning();
//       bevakning.setCarList(bevakningar);
//
//        System.out.println("ran this : " + counter);
//        counter ++;
//
      return bevakningar;
    }
}

