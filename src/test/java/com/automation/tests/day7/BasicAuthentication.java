package com.automation.tests.day7;

import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

public class BasicAuthentication {


@Test
    public void spartanAuthentication(){
 baseURI= ConfigurationReader.getProperty("SPARTAN.URI");
    given().auth().basic("admin","admin").when()
            .get("/spartans").prettyPeek()
             .then().assertThat().statusCode(200);
}

@Test
    public void authorizationTest(){
    baseURI= ConfigurationReader.getProperty("SPARTAN.URI");
    Spartan spartan=new Spartan("emma","Male",1523333252);

    given().auth().basic("user","user")
            .body(spartan).contentType(ContentType.JSON)
            .when()
            .post("/spartans").prettyPeek()
            .then().assertThat().statusCode(403).body("message",is("Forbidden"));
}

@Test
    public void authenticationTest(){
    baseURI= ConfigurationReader.getProperty("SPARTAN.URI");

    get("/spartans").prettyPeek().then().assertThat().statusCode(401);
}









}
