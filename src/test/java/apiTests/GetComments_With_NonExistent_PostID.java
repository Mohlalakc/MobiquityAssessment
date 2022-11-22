package apiTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetComments_With_NonExistent_PostID {
    RequestSpecification request = given();

    private Response response;

    @Test
    public void getComments_With_NonExistentPostId() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";

        response = given().relaxedHTTPSValidation()
                .queryParam("postId", "90000") //passing 9000 as Post ID query parameter
                .when()
                .get("/comments"); //Passing comments to pass
        Assert.assertEquals(response.getStatusCode(), 404); //validating status code is 404 Not Found
        String responseBodyAsString = response.getBody().asString();
        System.out.println(responseBodyAsString);

    }
}
