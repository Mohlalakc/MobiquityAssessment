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

public class SearchUsersWithWrongUsername {
    RequestSpecification request = RestAssured.given();

    static ExtentTest test;
    static ExtentReports report;

    private Response response;

    @BeforeClass
    public static void startTest() {
        report = new ExtentReports(System.getProperty("user.dir") + File.pathSeparator + "report" + File.pathSeparator + "ExtentReportResults.html");
        test = report.startTest("Searching for user by wrong username");
    }

    @AfterClass
    public static void endTest() {
        report.endTest(test);
        report.flush();
    }

    @Test
    public void SearchUserWrongUsername() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";

        response = given().relaxedHTTPSValidation()
                .queryParam("username", "irorffjjf") //passing non-existent username
                .when()
                .get("/users"); //Passing users path
        try {
            Assert.assertEquals(response.getStatusCode(), 404); //Validating that the status is 404 not found
            test.log(LogStatus.PASS, "Status code is 200");
        } catch (AssertionError e) {
            test.log(LogStatus.FAIL, e.toString());
            Assert.fail(e.toString());
            String responseBodyAsString = response.getBody().asString();
            System.out.println(responseBodyAsString);
            response.prettyPrint(); // printing response
        }
    }
}
