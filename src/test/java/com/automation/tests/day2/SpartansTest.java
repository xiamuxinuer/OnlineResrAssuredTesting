package com.automation.tests.day2;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.*;

public class SpartansTest {

    String base_URL="http://52.205.194.10:8000";

    @Test
    @DisplayName("get list of all spartans")
    public void getAllSpartans(){
        Response response= given().auth().basic("admin","admin").
                baseUri(base_URL).when().get("/api/spartans").prettyPeek();
        response.then().assertThat().statusCode(200);

        List<Integer>  idList=response.jsonPath().getList("id");
        //  System.out.println(idList);

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
