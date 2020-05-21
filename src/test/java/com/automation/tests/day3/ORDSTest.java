package com.automation.tests.day3;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;
public class ORDSTest {

@BeforeAll
    public static void setUp(){
    baseURI="http://54.224.118.38:1000/ords/hr";
}


    /**
     * given path parameter is "/regions/{id}"
     * when user makes get request
     * and region id is equals to 1
     * then assert that status code is 200
     * and assert that region name is Europe
     */

@Test
    public void test1(){
//    given().
//            pathParam("id", 1).
//            when().
            get("/regions/{id}",1).prettyPeek().
            then().assertThat().
            statusCode(200).
            body("region_name", is("Europe")).
            body("region_id", is(1));
}

@Test
    public void verifyEmployee(){
    Response response=given().accept(ContentType.JSON).when().get("/employees");
    JsonPath jsonPath=response.jsonPath();
    String firstEmployeeName=jsonPath.getString("items[0].first_name");
    String lastEmployeeName=jsonPath.getString("items[-1].first_name");
    System.out.println(firstEmployeeName);
    System.out.println(lastEmployeeName);

    Map<String,Object> firstEmployee= jsonPath.get("items[0]");
    System.out.println(firstEmployee);

}







}
