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

        Student student=response.jsonPath().getObject("students[0]",Student.class);
        System.out.println(student);
        System.out.println(student.getCompany().getAddress().getCity());








//           String student= response.jsonPath().getString("students");
//          System.out.println(student);
//
//          Map<String,Object> studentMap=response.as(Map.class);
//        System.out.println(studentMap);

//       String studentName=response.jsonPath().getString("students.studentId");
//        System.out.println("studentId is : "+studentName);
//
//        String studentContact=response.jsonPath().getString("students.contact");
//        System.out.println("student contact information: "+studentContact);
//        String  email=response.jsonPath().getString("students.contact.emailAddress");
//        System.out.println("student email address is : "+email);

    }





}
