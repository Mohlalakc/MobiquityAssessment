package apiTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class SearchUsersWithWrongUsername {
    RequestSpecification request = RestAssured.given();

    private Response response;
    @Test
    public void SearchUserWrongUsername(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";

        response = given().relaxedHTTPSValidation()
                .queryParam("username", "irorffjjf") //passing wrong non-existent username
                .when()
                .get("/users"); //Passing users path
        Assert.assertEquals(response.getStatusCode(), 404); //Validating that the response returns error 404
        String responseBodyAsString = response.getBody().asString();
        System.out.println(responseBodyAsString);
        response.prettyPrint(); // printing response
    }
}
