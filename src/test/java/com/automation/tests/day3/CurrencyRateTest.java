package com.automation.tests.day3;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;



public class CurrencyRateTest {

    @BeforeAll  //must be static
    public static void setUp(){
        baseURI="http://api.openrates.io";
    }

@Test
    public void getLatestRate(){
    Response response=given().queryParam("base","USD").when().get("/latest").prettyPeek();
    response.then().assertThat().statusCode(200);
    response.then().assertThat().body("base",is("USD"));
    response.then().assertThat().contentType("application/json");

   Headers headers= response.getHeaders();
   String content_type= headers.getValue("Content-Type");
    System.out.println(content_type);

    String date= LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    response.then().assertThat().body("date",containsString(date));
}

@Test
    public void getHistoryOfRate(){
    Response response=given().queryParam("base","USD").when().get("/2008-01-02").prettyPeek();

    response.then().assertThat().statusCode(200).and().body("date",is("2008-01-02")).
            and(). body("rates.USD", is(1.0f));

            float param=response.jsonPath().get("rates.USD");
                 System.out.println(param);

    Assertions.assertEquals(1.0,param);

}








}
