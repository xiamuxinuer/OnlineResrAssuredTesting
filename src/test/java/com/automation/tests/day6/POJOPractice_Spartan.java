package com.automation.tests.day6;


import com.automation.pojos.Spartan;

import io.restassured.response.Response;

import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

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
    System.out.println(spartanMap);
    System.out.println(spartanResponse instanceof  Spartan);   // must be true


}

@Test
    public void updateSpartan(){
//update user 101 s information:
        int updateUser=101;

        Spartan spartan=new Spartan("halik","Male",1255555888);
    Response response= given().auth().basic("admin","admin").
            contentType(ContentType.JSON).body(spartan).when().put("/spartans/{id}",updateUser).prettyPeek();
    response.then().assertThat().statusCode(204);

    given().auth().basic("admin","admin").when().get("/spartans/{id}",updateUser).prettyPeek().then().
    body("name",is("halik"));
}

@Test
//put request: update entire body:
    public void update(){
        int updateUser=101;

    Response response=  given().auth().basic("admin","admin").when().get("/spartans/{id}",updateUser).prettyPeek();
    Spartan spartan=response.as(Spartan.class);
    spartan.setName("apiemoji");

   given().auth().basic("admin","admin").
            contentType(ContentType.JSON).body(spartan).when().put("/spartans/{id}",updateUser).prettyPeek()
  .then().assertThat().statusCode(204);

    given().auth().basic("admin","admin").when().
            get("/spartans/{id}",updateUser).prettyPeek().then().assertThat().body("name",is("apiemoji"));

}


// partial update existing user:
@Test
    public void patchRequest(){
 Response response=given().auth().basic("admin","admin").contentType(ContentType.JSON).when().get("/spartans");
 //get the list of spartans: all spartans
      List<Spartan> allSpartans=response.jsonPath().getList("",Spartan.class);

    Random random=new Random();
    int randomNum=random.nextInt(allSpartans.size());
    int randomUserID=allSpartans.get(randomNum).getId();



// int userId=67;

      Map<String,String> update=new HashMap<>();
      update.put("name","mojimoji");

    given().auth().basic("admin","admin").
            contentType(ContentType.JSON).body(update).when().patch("/spartans/{id}",randomUserID).prettyPeek().then()
            .assertThat().statusCode(204);
    // after updated partially ,lets make sure name is updated:

    given().auth().basic("admin","admin").when().
            get("/spartans/{id}",randomUserID).prettyPeek().then().assertThat().body("name",is("mojimoji"));

}








}
