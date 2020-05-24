package com.automation.APIReview;
import static io.restassured.RestAssured.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.response.Response;
import io.restassured.http.ContentType;

import java.util.List;
import java.util.Scanner;

public class WeatherAPP {

    static {
        baseURI = "https://www.metaweather.com/api/location";
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter city name: ");
        String city = scanner.nextLine();
        String woeid = getWOEID(city);
        printWeatherInfo(woeid);
    }
    public static String getWOEID(String city) {
        Response response = given().queryParam("query", city).get("/search");
        String woeid = response.jsonPath().getString("woeid");
        //System.out.println("WOIED :: " + woeid);
        return woeid;
    }
    public static void printWeatherInfo(String woeid){
        woeid = woeid.replaceAll("\\D", "");//to delete all non-digits
        Response response = get("{woeid}", woeid);
        List<String> weatherStateName=response.jsonPath().getList("consolidated_weather.weather_state_name");
        List<Double> temp=response.jsonPath().getList("consolidated_weather.the_temp");
        List<String> date=response.jsonPath().getList("consolidated_weather.applicable_date");

        for (int i = 0; i <weatherStateName.size(); i++) {
            System.out.println("Date: "+ date.get(i));
            System.out.println("weather_state: "+weatherStateName.get(i));
            System.out.println("temperature: "+ temp.get(i));
            System.out.println("");

        }





    }

}
