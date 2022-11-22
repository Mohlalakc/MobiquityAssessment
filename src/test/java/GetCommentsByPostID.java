import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;
import static io.restassured.RestAssured.given;

public class GetCommentsByPostID {
    RequestSpecification request = given();

    private Response response;

    @Test
    public void getCommentsByPostId() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";

        response = given().relaxedHTTPSValidation()
                .queryParam("postId", "9") //passing 9 as Post ID query parameter
                .when()
                .get("/comments"); //Passing comments to pass
        Assert.assertEquals(response.getStatusCode(), 200); //validating status code is 200 OK
        String responseBodyAsString = response.getBody().asString();
        System.out.println(responseBodyAsString);
        Assert.assertTrue(responseBodyAsString.contains("9")); //Validating that the response string contains 9
        response.prettyPrint();
    }
}
