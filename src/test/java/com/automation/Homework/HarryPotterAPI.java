package com.automation.Homework;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class HarryPotterAPI {
    @BeforeAll
    public static void setUp() {
        baseURI = ConfigurationReader.getProperty("HarryPotter.URI");
    }
/*
Verify sorting hat
1.Send a get request to /sortingHat.
Request includes :
2.Verify status code 200, content type application/json; charset=utf-8
3.Verify that response body contains one of the following houses: "Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"
 */

@Test
    @DisplayName("Verify sorting hat")
    public void test1(){

    String expected="Gryffindor,Ravenclaw, Slytherin, Hufflepuff";
    Response response= given().
            get("/sortingHat").prettyPeek();

    response.then().assertThat().statusCode(200).
             and().contentType("application/json; charset=utf-8");

    Object object= response.as(Object.class);
    System.out.println(object);
    Assertions.assertTrue(expected.contains(object.toString()));


}

/*
Verify bad key
1.Send a get request to /characters.
 Request includes :•Header Accept with value application/json
 •Query param key with value invalid
 2.Verify status code 401, content type application/json; charset=utf-8
 3.Verify response status line include message Unauthorized
 4.Verify that response body says "error":"APIKeyNotFound"
 */
@Test
    @DisplayName("Verify bad key")
    public void test2(){
    Response response= given().accept(ContentType.JSON).
            queryParam("key","invalid").when().
            get("/characters").prettyPeek();
    response.then().assertThat().statusCode(401).and().
            contentType("application/json; charset=utf-8").and().
            statusLine(containsString("Unauthorized"));

         response.then().body("error",is("API Key Not Found"));
//String expected="API Key Not Found";
//String actual=response.jsonPath().getString("error");
//Assertions.assertEquals(expected,actual);

}
/*
Verify no key
 1.Send a get request to /characters.
 Request includes :•Header Accept with value application/json
 2.Verify status code 409, content type application/json; charset=utf-8
 3.Verify response status line include message Conflict
 4.Verify that response body says"error":"Must pass API key for request"
 */

@Test
    @DisplayName("Verify no key")
    public void test3(){
    Response response= given().accept(ContentType.JSON).get("/characters").prettyPeek();

    response.then().assertThat().statusCode(409).and().
             contentType("application/json; charset=utf-8").and().
            statusLine(containsString("Conflict")).and().
             body("error",is("Must pass API key for request"));
}

/*
Verify number of characters
1.Send a get request to /characters.
Request includes :•Header Accept with value application/json
•Query param key with value {{apiKey}}
2.Verify status code 200, content type application/json; charset=utf-8
3.Verify response contains 194 characters
 */

    @Test
    @DisplayName("Verify number of characters  ")
    public void test4() {
        Response response = given().accept(ContentType.JSON).
                queryParam("key", ConfigurationReader.getProperty("HarryPotter_API_KEY")).
                get("/characters");

        response.then().assertThat().statusCode(200).
                and().contentType("application/json; charset=utf-8");

        List<Object> list=response.jsonPath().getList("");
        //System.out.println(list.size());
        Assertions.assertTrue(list.size()==195);


    }

/*
Verify number of character id and house
1.Send a get request to /characters.
Request includes :•Header Accept with value application/json
•Query param key with value {{apiKey}}
2.Verify status code 200, content type application/json; charset=utf-8
3.Verify all characters in the response have id field which is not empty
4.Verify that value type of the field dumbledoresArmy is a boolean in all characters in the response
5.Verify value of the house in all characters in the response is one of the following:
"Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"
 */

    @Test
    @DisplayName("Verify number of character id and house")
    public void test5() {

        List<String> expected= Arrays.asList(new String[]{"Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"});
        Response response = given().accept(ContentType.JSON).
                queryParam("key", ConfigurationReader.getProperty("HarryPotter_API_KEY")).
                get("/characters");

        response.then().assertThat().statusCode(200).
                and().contentType("application/json; charset=utf-8");
        List<String> idList = response.jsonPath().getList("_id");
        List<Object> dumbledoresArmy_List=response.jsonPath().getList("dumbledoresArmy");
        List<String> houseList=response.jsonPath().getList("house");
        System.out.println(houseList);

        for (int i = 0; i <idList.size(); i++) {
            Assertions.assertFalse(idList.get(i).isEmpty());
            Assertions.assertTrue(dumbledoresArmy_List.get(i).equals(true)||dumbledoresArmy_List.get(i).equals(false));
        }
        Assertions.assertTrue(houseList.containsAll(expected));
    }

 /*
 Verify all character information
 1.Send a get request to /characters.
 Request includes :•Header Accept with value application/json
 •Query param key with value {{apiKey}}
 2.Verify status code 200, content type application/json; charset=utf-8
 3.Select name of any random character
 4.Send a get request to /characters.
 Request includes :•Header Accept with value application/json
 •Query param key with value {{apiKey}}
 •Query param name with value from step 3
 5.Verify that response contains the same character information from step 3.
 Compare all fields.
  */

@Test
    @DisplayName("Verify all character information")
    public void test6(){
    Response response = given().accept(ContentType.JSON).
            queryParam("key", ConfigurationReader.getProperty("HarryPotter_API_KEY")).
            get("/characters");
    response.then().assertThat().statusCode(200).contentType("application/json; charset=utf-8");

    String randomName=response.jsonPath().getString("name[0]");
    System.out.println(randomName);

    Response response1 = given().accept(ContentType.JSON).
            queryParam("key", ConfigurationReader.getProperty("HarryPotter_API_KEY")).
            queryParam("name",randomName).
            get("/characters").prettyPeek();
    response1.then().assertThat().body("name[0]",is(randomName));
}

/*
Verify name search
1.Send a get request to /characters
. Request includes :•Header Accept with value application/json
•Query param key with value {{apiKey}}
•Query param name with value Harry Potter
2.Verify status code 200, content type application/json; charset=utf-8
3.Verify name Harry Potter
4.Send a get request to /characters
. Request includes :•Header Accept with value application/json
•Query param key with value {{apiKey}}
•Query param name with value Marry Potter
5.Verify status code 200, content type application/json; charset=utf-8
6.Verify response body is empty
 */
@Test
    @DisplayName("Verify name search")
    public void test7(){
    Response response=given().accept(ContentType.JSON).
                      queryParam("key",ConfigurationReader.getProperty("HarryPotter_API_KEY")).
                      queryParam("name","Harry Potter").when().
                      get("/characters").prettyPeek();
    response.then().assertThat().
                      statusCode(200).contentType("application/json; charset=utf-8").
                      body("name[0]",is("Harry Potter"));

    Response response1=given().accept(ContentType.JSON).
            queryParam("key",ConfigurationReader.getProperty("HarryPotter_API_KEY")).
            queryParam("name","Marry Potter").when().
            get("/characters").prettyPeek();
    response1.then().assertThat().
            statusCode(200).contentType("application/json; charset=utf-8");
    List<Object> list=response1.jsonPath().getList("");
    Assertions.assertTrue(list.isEmpty());


}
/*
Verify house members
1.Send a get request to /houses.
 Request includes :•Header Accept with value application/json
 •Query param key with value {{apiKey}}
 2.Verify status code 200, content type application/json; charset=utf-8
 3.Capture the id of the Gryffindor house
 4.Capture the ids of the all members of the Gryffindor house
 5.Send a get request to /houses/:id.
 Request includes :•Header Accept with value application/json
 •Query param key with value {{apiKey}}
 •Path param id with value from step 3
 6.Verify that response contains the  same memberids as the step 4
 */
@Test
    @DisplayName("Verify house members")
    public void test8(){
    Response response=given().accept(ContentType.JSON).
            queryParam("key",ConfigurationReader.getProperty("HarryPotter_API_KEY")).
            get("/houses");
    response.then().assertThat().
            statusCode(200).contentType("application/json; charset=utf-8");
    String id="";
   List<Map<String,Object>> objects=response.jsonPath().getList("");
    for (Map<String,Object> each:objects){

       if (each.get("name").equals("Gryffindor")){

           id+=each.get("_id");
       }
    }
    System.out.println(id);

    Response response1=given().accept(ContentType.JSON).
            queryParam("key",ConfigurationReader.getProperty("HarryPotter_API_KEY")).
            pathParam("id",id).
            get("/houses/{id}").prettyPeek();
    response.then().assertThat().body("_id[0]",containsString(id));
}

/*
Verify house members again
1.Send a get request to /houses/{id}.
 Request includes :•Header Accept with value application/json
 •Query param key with value {{apiKey}}
 •Path param id with value 5a05e2b252f721a3cf2ea33f
 2.Capture the ids of all members
 3.Send a get request to /characters.
 Request includes :•Header Accept with value application/json
 •Query param key with value {{apiKey}}
 •Query param house with value Gryffindor
 4.Verify that response contains the same member ids from step 2
 */
@Test
    @DisplayName("Verify house members again")
    public void test9(){
    Response response=given().accept(ContentType.JSON).
            queryParam("key",ConfigurationReader.getProperty("HarryPotter_API_KEY")).
            pathParam("id","5a05e2b252f721a3cf2ea33f").
            get("/houses/{id}");
    List<String> membersID=response.jsonPath().getList("members[0]._id");

Response response1=given().accept(ContentType.JSON).
        queryParam("key",ConfigurationReader.getProperty("HarryPotter_API_KEY")).
        queryParam("house","Gryffindor").when().
        get("/characters");

List<String> id=response1.jsonPath().getList("_id");
Assertions.assertTrue(id.containsAll(membersID));
}


/*
Verify house with most members
1.Send a get request to /houses.
Request includes :•Header Accept with value application/json
•Query param key with value {{apiKey}}
2.Verify status code 200, content type application/json; charset=utf-8
3.Verify that Gryffindor house has the most members
 */
@Test
    @DisplayName("Verify house with most members")
    public void test10(){
    Response response=given().accept(ContentType.JSON).
            queryParam("key",ConfigurationReader.getProperty("HarryPotter_API_KEY")).
         when().
            get("/houses");
    response.then().assertThat().statusCode(200).contentType("application/json; charset=utf-8");

    response.then().assertThat().body("max{it.members.size()}.name",is("Gryffindor"));



}














}
