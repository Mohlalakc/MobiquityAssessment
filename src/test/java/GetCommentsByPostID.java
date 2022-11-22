import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;
import org.testng.annotations.BeforeMethod;
import static io.restassured.RestAssured.given;

public class GetCommentsByPostID {
    RequestSpecification request = given();

    private Response response;

    @Test
    public void getCommentsByPostId() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";

        response = given().relaxedHTTPSValidation()
                .queryParam("postId", "9")
                .when()
                .get("/comments");
        Assert.assertEquals(response.getStatusCode(), 200);
        String responseBodyAsString = response.getBody().asString();
        System.out.println(responseBodyAsString);
        Assert.assertTrue(responseBodyAsString.contains("9"));
        response.prettyPrint();
    }
}
