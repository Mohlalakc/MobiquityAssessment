package apiTests;

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

import static io.restassured.RestAssured.given;

public class GetPostsByDelphineID {

    RequestSpecification request = given();
    static ExtentTest test;
    static ExtentReports report;

    private Response response;

    @BeforeClass
    public static void startTest()
    {
        report = new ExtentReports(System.getProperty("user.dir")+ File.pathSeparator+ "report" +File.pathSeparator+"ExtentReportResults.html");
        test = report.startTest("Get posts by Delphine's ID");
    }

    @AfterClass
    public static void endTest()
    {
        report.endTest(test);
        report.flush();
    }

    @Test
    public void getPostsByDelphineID() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";

        response = given().relaxedHTTPSValidation()
                .queryParam("userId", "9") //Passing 9 as userID query parameter which belongs to Delphine
                .when()
                .get("/posts"); //passing Posts path
        try{
            Assert.assertEquals(response.getStatusCode(), 200); //Validating that the response status is 200
            test.log(LogStatus.PASS, "Status code is 200");
        }catch (AssertionError e){
            test.log(LogStatus.FAIL, e.toString());
            Assert.fail(e.toString());
        String responseBodyAsString = response.getBody().asString();
        System.out.println(responseBodyAsString);
        Assert.assertTrue(responseBodyAsString.contains("9")); //Validating that the response string contains 9
        response.prettyPrint();
    }
}}
