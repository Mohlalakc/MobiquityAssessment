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

public class MobiquityAPIs {

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

        JsonPath path = JsonPath.compile("$..id");
        List<Object> id = path.read(response.body().prettyPrint());

        response = given().relaxedHTTPSValidation()
                .queryParam("id", id)
                .when()
                .get("/posts");
        Assert.assertEquals(response.getStatusCode(), 200);
        response.body().prettyPrint();

        JsonPath postpath = JsonPath.compile("$..postId");
        List<Object> postId = postpath.read(response.body().prettyPrint());
        response = given().relaxedHTTPSValidation()
                .queryParam("postId", postId)
                .when()
                .get("/comments");
        Assert.assertEquals(response.getStatusCode(), 200);
        response.body().prettyPrint();

        JsonPath Email = JsonPath.compile("$..email");
        List<Object> email = Email.read(response.body().prettyPrint());
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);

        for(int x = 0; x < email.size(); x++){
            Matcher matcher = pattern.matcher(email.get(x).toString());

            System.out.println(matcher.matches());
        }

   }

}
