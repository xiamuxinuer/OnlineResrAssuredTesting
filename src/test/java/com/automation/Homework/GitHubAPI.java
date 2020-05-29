package com.automation.Homework;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class GitHubAPI {

    @BeforeAll
    public static void setUp() {
        baseURI = ConfigurationReader.getProperty("Github.URI");
    }
/*
Verify organization information
 1.Send a get request to /orgs/:org.
  Request includes :•Path param org with value cucumber
  2.Verify status code 200,
  content type application/json;charset=utf-8
  3.Verify value of the login field is cucumber
  4.Verify value of the name field is cucumber
  5.Verify value of the id field is 320565
 */

    @Test
    @DisplayName("Verify organization information")
    public void test1() {
        Response response = given().queryParam("org", "cucumber")
                .when().get(" /orgs/:org").prettyPeek();
        response.then().assertThat().statusCode(200).and()
                .contentType("application/json; charset=utf-8")
                .body("login", is("cucumber"))
                .body("name", is("Cucumber"))
                .body("id", is(320565));
    }

/*
Verify error message
1.Send a get request to /orgs/:org.
Request includes :•Header Accept with value application/xml
•Path param org with value cucumber
2.Verify status code 415,
 content type application/json; charset=utf-8
 3.Verify response status line include message Unsupported Media Type
 */

    @Test
    @DisplayName("Verify error message")
    public void test2() {
        Response response = given().accept(ContentType.XML).
                queryParam("org", "cucumber")
                .when().get(" /orgs/:org").prettyPeek();
        response.then().assertThat().statusCode(415).and()
                .contentType("application/json; charset=utf-8").and()
                .statusLine(containsString("Unsupported Media Type"));

    }

    /*
    Number of repositories
    1.Send a get request to /orgs/:org.
     Request includes :•Path param org with value cucumber
     2.Grab the value of the field public_repos
     3.Send a get request to /orgs/:org/repos.
     Request includes :•Path param org with value cucumber
     4.Verify that number of objects in the response  is equal to value from step 2
     */
    @Test
    @DisplayName("Number of repositories")
    public void test3() {
        Response response = given().
                queryParam("org", "cucumber")
                .when().get(" /orgs/:org");
        int numOfRepo = response.jsonPath().getInt("public_repos");  //90
        Response response1 = given().
                queryParam("org", "cucumber")
                .when().get("/orgs/:org/repos");
        int size = response1.body().jsonPath().getList("").size();
        System.out.println(size);   //30


    }

    /*
    Repository id information
    1.Send a get request to /orgs/:org/repos.
     Request includes :•Path param org with value cucumber
     2.Verify that id field is unique in every object in the response
     3.Verify that node_id field is unique in every object in the response
     */
    @Test
    @DisplayName("Repository id information")
    public void test4() {
        Response response = given().
                queryParam("org", "cucumber")
                .when().get("/orgs/:org/repos").prettyPeek();

        List<Object> idtList = response.jsonPath().getList("id");
        List<Object> node_idList = response.jsonPath().getList("node_id");

//        for (int i = 0; i <=idtList.size()-1; i++) {
//            if (idtList.get(i) != idtList.get(i + 1)) {
//                System.out.println("id is unique");
//            }
//        }

        Assertions.assertTrue(unique(idtList));
        Assertions.assertTrue(unique(node_idList));

    }


    public boolean unique(List list) {
        for (int i = 0; i <= list.size() - 1; i++) {
            if (list.get(i) != list.get(i + 1))
                return true;
        }
        return false;
    }


    /*
    Repository owner information
    1.Send a get request to /orgs/:org.
    Request includes :•Path param org with value cucumber
    2.Grab the value of the field id
    3.Send a get request to /orgs/:org/repos.
    Request includes :•Path param org with value cucumber
    4.Verify that value of the id inside the owner object in every response is equal to value from step 2
     */
    @Test
    @DisplayName("Repository owner information")
    public void test5() {
        Response response = given().queryParam("org", "cucumber")
                .when().get("/orgs/:org").prettyPeek();
        int id = response.jsonPath().getInt("id");

        Response response1 = given().
                queryParam("org", "cucumber")
                .when().get("/orgs/:org/repos");
        List<Integer> ownerIDList = response1.jsonPath().getList("owner.id");

        for (int i = 0; i <= ownerIDList.size() - 1; i++) {
            Assertions.assertTrue(id == ownerIDList.get(i));
        }
    }
/*
Ascending order by full_name sort
1.Send a get request to /orgs/:org/repos.
Request includes :•Path param org with value cucumber
•Query param sort with value full_name
2.Verify that all repositories are listed in alphabetical order based on the value of the field name
 */

    @Test
    @DisplayName("Ascending order by full_name sort")
    public void test6() {
        Response response = given().queryParam("org", "cucumber").
                queryParam("sort", "full_name")
                .when().get("/orgs/:org/repos");
        List<String> nameList = response.jsonPath().getList("name");

        for (int i = 0; i < nameList.size() - 1; i++) {
            Assertions.assertTrue(nameList.get(i).charAt(0) <= nameList.get(i + 1).charAt(0));
//            System.out.println(nameList.get(i).charAt(0)+"----"+ nameList.get(i+1).charAt(0) );
//            System.out.println("");
        }


    }
/*
Descending order by full_name sort
1.Send a get request to /orgs/:org/repos.
Request includes :•Path param org with value cucumber
•Query param sort with value full_name
•Query param direction with value desc
2.Verify that all repositories are listed in reverser alphabetical order
   based on the value of the field name
 */

    @Test
    @DisplayName("Descending order by full_name sort")
    public void test7() {
        Response response = given().queryParam("org", "cucumber").
                queryParam("sort", "full_name")
                .queryParam("direction", "desc")
                .when().get("/orgs/:org/repos");
        List<String> nameList = response.jsonPath().getList("name");

        for (int i = 0; i < nameList.size() - 1; i++) {
            Assertions.assertTrue(nameList.get(i).charAt(0) >= nameList.get(i + 1).charAt(0));
            //      System.out.println(nameList.get(i).charAt(0)+"----"+ nameList.get(i+1).charAt(0) );
        }

    }

/*
Default sort
1.Send a get request to /orgs/:org/repos.
Request includes :•Path param org with value cucumber
2.Verify that by default all repositories are listed in descending order
 based on the value of the field created_at
 */

    @Test
    @DisplayName("Default sort")
    public void test8() {
        Response response = given().queryParam("org", "cucumber")
                .when().get("/orgs/:org/repos");
        List<String> create_at=response.jsonPath().getList("created_at");
        for (int i = 0; i <create_at.size(); i++) {
            //System.out.println(create_at.get(i));
            System.out.println( create_at.get(i).substring(0,10));

        }


    }


}






