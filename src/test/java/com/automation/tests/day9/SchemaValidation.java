package com.automation.tests.day9;
import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;



public class SchemaValidation {

    @Test
    public void badSSLCertificateTest(){
        /**
         * no valid certificate - no handshake, no secure connection
         *     relaxedHTTPSValidation - ignore SSL certificate issues
         *      * Use relaxed HTTP validation with SSLContext protocol SSL.
         *      This means that you'll trust all hosts regardless if the SSL certificate is invalid.
         *      By using this method you don't need to specify a keystore or trust store.
         */
        baseURI = "https://untrusted-root.badssl.com/";
        Response response = given().relaxedHTTPSValidation().get().prettyPeek();
        System.out.println(response.statusCode());


    }




@Test
    public void schemaValidation(){
        baseURI=ConfigurationReader.getProperty("SPARTAN.URI");

    File schema=new File("spartan_schema.json");
    given().auth().basic("admin","admin").when()
            .get("/spartans/{id}",121).prettyPeek()
           .then().body(JsonSchemaValidator.matchesJsonSchema(schema));

}























}
