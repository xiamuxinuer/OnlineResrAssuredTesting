package com.automation.tests.day2;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class ORDSTest {

String base_URL="http://3.90.112.152:1000/ords/hr";

@Test
@DisplayName("get list of all employees")
public void getAllEmployee(){

Response response= given().baseUri(base_URL).when().get("/employees").prettyPeek();


}



@Test
@DisplayName("get employee under specific id")
public void getOneEmployee(){
    Response response= given().baseUri(base_URL).when().get("/employees/{id}",100).prettyPeek();
}









}
