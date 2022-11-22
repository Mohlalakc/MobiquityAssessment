package apiTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetPostsByDelphineID {

    RequestSpecification request = given();

    private Response response;

    @Test
    public void getPostsByDelphineID() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";

        response = given().relaxedHTTPSValidation()
                .queryParam("userId", "9") //Passing 9 as userID query parameter which belongs to Delphine
                .when()
                .get("/posts"); //passing Posts path
        Assert.assertEquals(response.getStatusCode(), 200); //Validating response status is 200
        String responseBodyAsString = response.getBody().asString();
        System.out.println(responseBodyAsString);
        Assert.assertTrue(responseBodyAsString.contains("9")); //Validating that the response string contains 9
        response.prettyPrint();
    }
}
