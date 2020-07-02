package com.automation.APIReview;

import com.automation.pojos.Spartan;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RebootCamp {
    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "http://52.205.194.10";
        RestAssured.port = 8000 ;
        RestAssured.basePath = "/api";
    }
    @Test
    public void add1SpartanTest1(){
        String data = "{\n" +
                "  \"name\"   : \"Bootcamp user\",\n" +
                "  \"gender\" : \"Male\",\n" +
                "  \"phone\"  : 2131231231\n" +
                "}" ;
        given()
                .log().all()
                .contentType(ContentType.JSON) // extremely important for server to know whats the contenttpe you are sending
                .body(data).
                when()
                .post("/spartans").
                then()
                .log().all()
                .statusCode(201)
                .body("success",is("A Spartan is Born!"))
                .body("data.name",is("Bootcamp user") )
                .body("data.gender",equalTo("Male"))
        ;
    }
    @Test
    public void add1SpartanTest2(){
        Map<String,Object> data = new LinkedHashMap<>(); //HashMap
        data.put("name","Sample name");
        data.put("gender","Male");
        data.put("phone",1931231231);
       Response response= given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(data).
                when()
                .post("/spartans");
                response.then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED) // you can replace with 201
                .body("success",is("A Spartan is Born!"))
                .body("data.name",is("Sample name") )
                .body("data.gender",equalTo("Male"));

        System.out.println(response.getHeaders().getValue("Content-Type"));
        System.out.println(response.getHeaders().getValue("Date"));


    }
    @Test
    public void add1SpartanTest3(){
        Spartan sp = new Spartan("Sample name","Male",1931231231);
     //   SpartanPojo sp = new SpartanPojo("Sample name","Male",1931231231);
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(sp).
                when()
                .post("/spartans").
                then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .body("success",is("A Spartan is Born!"))
                .body("data.name",is("Sample name") )
                .body("data.gender",equalTo("Male"))
        ;

    }
    @Test
    public void add1SpartanTest4(){
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(new File("spartan.json")).
                when()
                .post("/spartans").
                then()
                .log().all()
                .statusCode(201)
        ;
    }
    @Test
    public void add1SpartanTest5(){
        Spartan sp = new Spartan("Sample name","Male",1931231231);
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(sp).
                when()
                .post("/spartans").
                then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .body("success",is("A Spartan is Born!"))
                .body("data.name",is("Sample name") )
                .body("data.gender",equalTo("Male"))
        ;
    }

}
