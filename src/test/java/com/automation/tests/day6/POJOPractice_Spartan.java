package com.automation.tests.day6;


import com.automation.pojos.Spartan;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class POJOPractice_Spartan {
    /*
  {
	 “id”: 393,
	“name”: “Michael Scott”,
	“gender”: “Male”,
	 “phone”: 6969696969
					}
 */
    @BeforeAll
    public static void setUp(){
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");
    }

@Test
    public void addSpartanTest(){
        Map<String,String> spartan=new HashMap<>();

        spartan.put("gender","Male");
        spartan.put("name","emma");
        spartan.put("phone","1255559988");

    Response response= given().auth().basic("admin","admin").
            contentType(ContentType.JSON).body(spartan).when().post("/spartans").prettyPeek();

    response.then().
            statusCode(201).and().assertThat().body("data.gender",is("Male"));

    //getting user that we just added to the database , and store it as spartan object:
    Spartan spartanResponse=response.jsonPath().getObject("data",Spartan.class);
    System.out.println(spartanResponse);

    Map<String,Object> spartanMap=response.jsonPath().getObject("data",Map.class);
    System.out.println(spartanResponse instanceof  Spartan);   // must be true




}











}
