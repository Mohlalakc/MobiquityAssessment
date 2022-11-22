import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class SearchUsersByDelphine {
    RequestSpecification request = RestAssured.given();

    private Response response;
    @Test
    public void SearchUserByDelphine(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";

        response = given().relaxedHTTPSValidation()
                .queryParam("username", "Delphine")
                .when()
                .get("/users");
        Assert.assertEquals(response.getStatusCode(), 200);
        String responseBodyAsString = response.getBody().asString();
        System.out.println(responseBodyAsString);
        Assert.assertTrue(responseBodyAsString.contains("Delphine"));
        response.prettyPrint();
    }
}
