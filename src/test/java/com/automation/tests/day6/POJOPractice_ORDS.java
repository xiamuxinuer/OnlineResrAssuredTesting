package com.automation.tests.day6;
import com.automation.pojos.Employee;
import com.automation.pojos.Link;
import com.automation.pojos.Spartan;

import io.restassured.response.Response;

import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.lang.reflect.Array;
import java.util.*;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class POJOPractice_ORDS {

    @BeforeAll
    public static void setUp(){
        baseURI = ConfigurationReader.getProperty("ORDS.URI");
    }

   @Test
    public void getEmployeeTest(){
        Response response=get("/employees/{id}",100).prettyPeek();
       Employee employee=response.as(Employee.class);
       System.out.println(employee);

       List<Map<String,String>> links =response.jsonPath().get("links");
       System.out.println(links);
       System.out.println(links.get(0).get("rel"));

//       List<Link> links=employee.getLinks();
//       System.out.println(links);



   }






}
