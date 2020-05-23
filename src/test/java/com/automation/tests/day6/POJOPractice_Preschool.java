package com.automation.tests.day6;
import com.automation.pojos.Preschool.Student;
import com.automation.utilities.ConfigurationReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import java.io.File;
import java.util.Map;


public class POJOPractice_Preschool {
    @BeforeAll
    public static void setUp(){
        baseURI = ConfigurationReader.getProperty("Preschool.URI");
    }

@Test
    public void addStudent(){
       File file=new File("student.json");
       Response response= given().contentType(ContentType.JSON).body(file).post("/student/create").prettyPeek();

       int studentID=response.jsonPath().getInt("studentId");
    System.out.println(studentID);
}

    @Test
    public void getStudentTest(){
        Response response = get("/student/{id}", 11671).prettyPeek();

           String student= response.jsonPath().getString("students");
         System.out.println(student);

//        Student student1 = response.jsonPath().getObject("students", Student.class);
//        System.out.println(student1);
    }





}
