import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class EmailValidation {
    RequestSpecification request = given();

    private Response response;

    @Test
    public void getCommentsByPostId() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";

        response = given().relaxedHTTPSValidation()
                .queryParam("postId", "9") //passing 9 as Post ID
                .when()
                .get("/comments"); //passing comments path
        Assert.assertEquals(response.getStatusCode(), 200); //Validating that the status is 200
        String responseBodyAsString = response.getBody().asString(); //Creating new string to use to extract response field
        System.out.println(responseBodyAsString);
        Assert.assertTrue(responseBodyAsString.contains("9")); //Verifying that the response contains 9 value
        response.prettyPrint();
        JsonPath Email = JsonPath.compile("$..email"); //creating email path
        List<Object> email = Email.read(response.body().prettyPrint()); //Extracting email from the response list
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"; //using regular expression to create email format
        Pattern pattern = Pattern.compile(regex);

        for (int x = 0; x < email.size(); x++) {
            Matcher matcher = pattern.matcher(email.get(x).toString());

            System.out.println(matcher.matches()); //Printing results on whether the email validation matches
        }
    }
}
