package apiTests;

import com.jayway.jsonpath.JsonPath;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class MobiquityAPIs {
    static ExtentTest test;
    static ExtentReports report;

    RequestSpecification request = RestAssured.given();

    private Response response;

    @BeforeClass
    public static void startTest()
    {
        report = new ExtentReports(System.getProperty("user.dir")+File.separator+ "report" +File.separator+"ExtentReportResults.html");
        test = report.startTest("Testing all the scenarios on one test");
    }

    @AfterClass
    public static void endTest()
    {
        report.endTest(test);
        report.flush();
    }
    @Test
    public void SearchUserByDelphine(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";
        System.out.println(System.getProperty("user.dir")+File.separator+ "report" +File.separator+"ExtentReportResults.html");

        response = given().relaxedHTTPSValidation()
                .queryParam("username", "Delphine") //Passing username as Delphine
                .when()
                .get("/users"); //passing users path

        try{
            Assert.assertEquals(response.getStatusCode(), 200); //Validating that the status is 200
            test.log(LogStatus.PASS, "Status code is 200");
        }catch (AssertionError e){
            test.log(LogStatus.FAIL, e.toString());
            Assert.fail(e.toString());
        }
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
        try{
            Assert.assertEquals(response.getStatusCode(), 200); //Validating that the status is 200
            test.log(LogStatus.PASS, "Status code is 200");
        }catch (AssertionError e){
            test.log(LogStatus.FAIL, e.toString());
            Assert.fail(e.toString());
        }

        JsonPath postpath = JsonPath.compile("$..postId");
        List<Object> postId = postpath.read(response.body().prettyPrint());  //Extracting Post ID from response string
        response = given().relaxedHTTPSValidation()
                .queryParam("postId", postId) //Passing the extracted postID from the response
                .when()
                .get("/comments");  //Passing comments path
        try{
            Assert.assertEquals(response.getStatusCode(), 200); //Validating that the status is 200
            test.log(LogStatus.PASS, "Status code is 200");
        }catch (AssertionError e){
            test.log(LogStatus.FAIL, e.toString());
            Assert.fail(e.toString());
        }

        JsonPath Email = JsonPath.compile("$..email");
        List<Object> email = Email.read(response.body().prettyPrint());
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"; //Using regular  expression to create email format
        Pattern pattern = Pattern.compile(regex);

        for(int x = 0; x < email.size(); x++){
            Matcher matcher = pattern.matcher(email.get(x).toString()); //passing the extracted email address

            if(matcher.matches()){
                test.log(LogStatus.PASS, email.get(x).toString() + " Correct Format");

            } else{
                test.log(LogStatus.FAIL, email.get(x).toString() + " Incorrect Format");
            }

        }

   }


}

