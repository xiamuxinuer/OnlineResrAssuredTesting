package com.automation.tests.day5;

import com.automation.pojos.Spartan;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class POJOPractice {

    @BeforeAll
    public static void setUp(){
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");
    }
    @Test
    public void getAllSpartan(){
        given().accept(ContentType.JSON).auth().basic("admin","admin").when().get("/spartans").prettyPeek().
                then().assertThat().statusCode(200).log().ifError();
    }


    @Test
    public void getUser(){
      Response response=given().auth().basic("admin","admin").
                when().get("/spartans/{id}",393).prettyPeek();
// get response: deserialization
     Spartan spartan=response.as(Spartan.class);
        System.out.println(spartan);
        System.out.println(" user name :"+ spartan.getName());
        Assertions.assertEquals("Michael Scott",spartan.getName());
        Assertions.assertEquals("Male",spartan.getGender());


        Map<String,Object> spartanAsMap=response.as(Map.class);
        System.out.println(spartanAsMap);
        System.out.println(spartanAsMap.get("gender"));
        Assertions.assertEquals("Michael Scott",spartanAsMap.get("name"));

    }

    @Test
    public void addUser(){
        Spartan spartan=new Spartan("emma","Female",5155156666l);

//add a user:serialization
        Response response=given().auth().basic("admin","admin").
                contentType(ContentType.JSON).body(spartan).when().post("/spartans").prettyPeek();

       int userID=response.jsonPath().getInt("data.id");
        System.out.println(userID);

//delete a user:
        given().auth().basic("admin","admin").when().delete("/spartans/{id}",492).prettyPeek().
                then().assertThat().statusCode(204);



    }

}
