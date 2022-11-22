package apiTests;

import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

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
                .queryParam("username", "Delphine") //Passing username as Delphine
                .when()
                .get("/users"); //passing users path
        Assert.assertEquals(response.getStatusCode(), 200); //Validating response status 200
        String responseBodyAsString = response.getBody().asString();
        System.out.println(responseBodyAsString);
        Assert.assertTrue(responseBodyAsString.contains("Delphine")); //validating that the response string contains Delphine
        response.prettyPrint();

        JsonPath path = JsonPath.compile("$..id");
        List<Object> id = path.read(response.body().prettyPrint()); //Extracting ID from the response

        response = given().relaxedHTTPSValidation()
                .queryParam("id", id)  //passing the ID extracted from the response
                .when()
                .get("/posts");  //Passing posts path
        Assert.assertEquals(response.getStatusCode(), 200); //Validating status 200
        response.body().prettyPrint();

        JsonPath postpath = JsonPath.compile("$..postId");
        List<Object> postId = postpath.read(response.body().prettyPrint());  //Extracting Post ID from response string
        response = given().relaxedHTTPSValidation()
                .queryParam("postId", postId) //Passing the extracted postID from the response
                .when()
                .get("/comments");  //Passing comments path
        Assert.assertEquals(response.getStatusCode(), 200);  //validating status 200
        response.body().prettyPrint();

        JsonPath Email = JsonPath.compile("$..email");
        List<Object> email = Email.read(response.body().prettyPrint());
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"; //Using regular  expression to create email format
        Pattern pattern = Pattern.compile(regex);

        for(int x = 0; x < email.size(); x++){
            Matcher matcher = pattern.matcher(email.get(x).toString()); //passing the extracted email address

            System.out.println(matcher.matches()); //Printing true or false according to validating of email format
        }

   }

}
