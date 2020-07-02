package com.automation.tests.day2;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class ORDSTest {

String base_URL="http://52.205.194.10:1000/ords/hr";

@Test
@DisplayName("get list of all employees")
public void getAllEmployee(){

Response response= given().baseUri(base_URL).when().get("/employees").prettyPeek();


}
// it was just fine last night



@Test
@DisplayName("get employee under specific id")
public void getOneEmployee(){
    Response response= given().baseUri(base_URL).when().get("/employees/{id}",100).prettyPeek();

    int statusCode = response.statusCode();// to get status code
    System.out.println(statusCode);    //to print status code
    Assertions.assertEquals(200,statusCode);

    //assertion:
    response.then().statusCode(200);

}

@Test
    @DisplayName("get countries info")
    public void getCountriesInfo(){
   given().baseUri(base_URL).when().get("/countries").prettyPeek().then().statusCode(200);
}








}
