package com.automation.tests.day8;
import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
public class BookIt{

    @BeforeAll
    public static void setUp(){
        baseURI ="https://cybertek-reservation-api-qa.herokuapp.com";
    }

   @Test
    public void login(){
      Response response= given().queryParam("email","teacherva5@gmail.com").
                         queryParam("password","maxpayne").when().
                          get("/sign").prettyPeek();

       String access_token = response.jsonPath().getString("accessToken");
       System.out.println("Access Token is: "+access_token);

       given().auth().oauth2(access_token).when().
               get("/api/users").prettyPeek().then().assertThat().statusCode(200);
   }

@Test
    public String getToken(){
    Response response= given().queryParam("email","teacherva5@gmail.com").
            queryParam("password","maxpayne").when().
            get("/sign").prettyPeek();

    String access_token = response.jsonPath().getString("accessToken");
    System.out.println("Access Token is: "+access_token);
    return  access_token;
}



@Test
    public String getToken(String email,String password){
    Response response= given().queryParam("email",email).
            queryParam("password",password).when().
            get("/sign").prettyPeek();

    String access_token = response.jsonPath().getString("accessToken");
    System.out.println("Access Token is: "+access_token);
    return access_token;
}

@Test
    public void getRooms(){
    given().auth().oauth2(getToken()).when().
            get("/api/rooms").prettyPeek().then().assertThat().statusCode(200);
}

    @Test
    public void getAllTeamsTest(){
        Response response =
                given().
                        auth().oauth2(getToken("teacherva5@gmail.com","maxpayne")).
                        when().
                        get("/api/teams").prettyPeek();
        response.then().statusCode(200);
    }


    @Test
    public void geAllTeamsTest(){
        Response response = given().
                header("Authorization", "Bearer "+getToken()).
                when().
                get("/api/teams").prettyPeek();
        response.then().statusCode(200);
    }



}
