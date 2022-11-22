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

public class EmailValidation {
    static ExtentTest test;
    static ExtentReports report;
    RequestSpecification request = given();

    private Response response;

    @BeforeClass
    public static void startTest()
    {
        report = new ExtentReports(System.getProperty("user.dir")+ File.pathSeparator+ "report" +File.pathSeparator+"ExtentReportResults.html");
        test = report.startTest("Get Comments by Post ID");
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
        System.out.println(System.getProperty("user.dir")+"ExtentReportResults.html");
        response = given().relaxedHTTPSValidation()
                .queryParam("postId", "9") //passing 9 as Post ID
                .when()
                .get("/comments"); //passing comments path

        try{
            Assert.assertEquals(response.getStatusCode(), 200); //Validating that the status is 200
            test.log(LogStatus.PASS, "Status code is 200");
        }catch (AssertionError e){
            test.log(LogStatus.FAIL, e.toString());
            Assert.fail(e.toString());
        }

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
