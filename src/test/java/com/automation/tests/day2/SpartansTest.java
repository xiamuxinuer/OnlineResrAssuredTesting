package com.automation.tests.day2;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.*;

public class SpartansTest {

    String base_URL="http://54.224.118.38:8000";

    @Test
    @DisplayName("get list of all spartans")
    public void getAllSpartans(){
        given().auth().basic("admin","admin").
                baseUri(base_URL).when().get("/api/spartans").prettyPeek().then().statusCode(200);

    }

    @Test
    @DisplayName("add new spartan")
    public void addNewSpartan(){

//String body= "{\"gender\": \"Male\", \"name\": \"Random User\", \"phone\": 1059999999}";
        File jsonFile = new File(System.getProperty("user.dir") + "/spartan.json");

        given().contentType(ContentType.JSON).
                auth().basic("admin","admin").body(jsonFile).
                baseUri(base_URL).post("/api/spartans").prettyPeek().then().statusCode(201);


    }

@Test
    @DisplayName("delete some spartan")
    public void deleteSpartan(){
    given().contentType(ContentType.JSON).
            auth().basic("admin","admin").
            baseUri(base_URL).delete("/api/spartans/{id}",907).prettyPeek().then().statusCode(204);
}
















}
