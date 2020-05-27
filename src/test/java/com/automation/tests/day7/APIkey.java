package com.automation.tests.day7;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
public class APIkey {
    private final String apiKey="29055371";

    @BeforeAll
    public static void setUp(){
        baseURI="http://omdbapi.com/";
    }

@Test
    public void getMovieTest(){
        String itemToSearch="Frozen";
       Response response= given().queryParam("t",itemToSearch).queryParam("apikey",apiKey).
                when().get().prettyPeek();
               response.then().assertThat().statusCode(200).and().
                body("Title",containsString(itemToSearch));
    List<Map<String,Object>> rating= response.jsonPath().getList("Ratings");
    System.out.println(rating);


}



}
