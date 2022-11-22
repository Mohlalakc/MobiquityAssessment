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

public class GetCommentsByPostID {
    RequestSpecification request = given();
    static ExtentTest test;
    static ExtentReports report;
    private Response response;

    @BeforeClass
    public static void startTest()
    {
        report = new ExtentReports(System.getProperty("user.dir")+ File.pathSeparator+ "report" +File.pathSeparator+"ExtentReportResults.html");
        test = report.startTest("Get Comments by Post IDs");
    }

    @AfterClass
    public static void endTest()
    {
        report.endTest(test);
        report.flush();
    }

    @Test
    public void getCommentsByPostId() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";

        response = given().relaxedHTTPSValidation()
                .queryParam("postId", "9") //passing 9 as Post ID query parameter
                .when()
                .get("/comments"); //Passing comments to pass
        try{
            Assert.assertEquals(response.getStatusCode(), 200); //Validating that the status is 200
            test.log(LogStatus.PASS, "Status code is 200");
        }catch (AssertionError e){
            test.log(LogStatus.FAIL, e.toString());
            Assert.fail(e.toString());
        } //validating status code is 200 OK
        String responseBodyAsString = response.getBody().asString();
        System.out.println(responseBodyAsString);
        Assert.assertTrue(responseBodyAsString.contains("9")); //Validating that the response string contains 9
        response.prettyPrint();
    }
}
