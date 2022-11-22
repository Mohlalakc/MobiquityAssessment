import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class SearchUsersWithWrongUsername {
    RequestSpecification request = RestAssured.given();

    private Response response;
    @Test
    public void SearchUserWrongUsername(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";

        response = given().relaxedHTTPSValidation()
                .queryParam("username", "irorffjjf")
                .when()
                .get("/users");
        Assert.assertEquals(response.getStatusCode(), 404);
        String responseBodyAsString = response.getBody().asString();
        System.out.println(responseBodyAsString);
        response.prettyPrint();
    }
}
