package com.automation.tests.day4;

import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class ORDSTestsDay4 {
    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("ORDS.URI");
    }

    /**
     * Warmup!
     * Given accept type is JSON
     * When users sends a GET request to “/employees”
     * Then status code is 200
     * And Content type is application/json
     * And response time is less than 3 seconds
     */

    @Test
    public void warmUpTask() {
        Response response = given().accept(ContentType.JSON).when().get("/employees");
        response.then().assertThat().statusCode(200).and().assertThat()
                .contentType("application/json").time(lessThan(3L), TimeUnit.SECONDS);

        //JsonPath jsonPath=response.jsonPath();

    }

    /**
     * Given accept type is JSON
     * And parameters: q = {"country_id":"US"}
     * When users sends a GET request to "/countries"
     * Then status code is 200
     * And Content type is application/json
     * And country_name from payload is "United States of America"
     */
    @Test
    public void warmUp2() {
        given().accept(ContentType.JSON).when().get("/countries/{id}", "US").prettyPeek().then().
                assertThat().statusCode(200).contentType(ContentType.JSON).
                body("country_name", is("United States of America"));

//    given().
//            accept(ContentType.JSON).
//            queryParam("q", "{\"country_id\":\"US\"}").
//            when().
//            get("/countries").
//            then().assertThat().
//            statusCode(200);

        Response response = given().accept(ContentType.JSON).when().get("/countries").prettyPeek();

        String countryName = response.jsonPath().getString("items.find{it.country_id=='US'}.country_name");
        Map<String, Object> countryUS = response.jsonPath().get("items.find{it.country_id=='US'}");
        System.out.println(countryName);
        System.out.println(countryUS);

        List<String> allCountriesName = response.jsonPath().getList("items.findAll{it.region_id== 2}.country_name");
        System.out.println(allCountriesName);


        for (Map.Entry<String, Object> entry : countryUS.entrySet()) {
            System.out.printf(entry.getKey() + ": " + entry.getValue());
            System.out.println("");
        }
    }



    @Test
    public void getEmployeeTest() {
        Response response = when().get("/employees");
        //collectionName.max{it.propertyName}
        Map<String, ?> bestEmployee = response.jsonPath().get("items.max{it.salary}");
        Map<String, ?> poorGuy = response.jsonPath().get("items.min{it.salary}");

       // int companysPayroll = response.jsonPath().get("items.collect{it.salary}.sum()");

        System.out.println(bestEmployee);
        System.out.println(poorGuy);
      //  System.out.println("Company's payroll: " + companysPayroll);
    }













}