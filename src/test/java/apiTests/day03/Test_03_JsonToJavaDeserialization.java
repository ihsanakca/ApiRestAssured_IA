package apiTests.day03;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Map;

public class Test_03_JsonToJavaDeserialization {
    /**
     * //TASK
     * //base url = https://gorest.co.in
     * //end point = /public/v2/users
     * //path parameter = {id} --> 1560419
     * //send a get request with the above credentials
     * //parse to Json object to java collection
     * //verify that the body below
     * /*
     * {
     * "id": 1560419,
     * "name": "Bhargava Nambeesan",
     * "email": "bhargava_nambeesan@jast.example",
     * "gender": "male",
     * "status": "active"
     * }
     */

    //test with hard assertion
    @Test
    public void test1() {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .pathParam("id", 1560419)
                .when()
                .get("https://gorest.co.in/public/v2/users/{id}");

        Assert.assertEquals(response.statusCode(), 200);

        //path() method
        //verify id
        int actualId = response.path("id");
        int expectedId = 1560419;
        Assert.assertEquals(actualId, expectedId);

        //verify email
        String actualEmail = response.path("email");
        String expectedEmail = "bhargava_nambeesan@jast.example";
        Assert.assertEquals(actualEmail, expectedEmail);

        //de-serialization json to java collection ---> as() method ile

        Map<String, Object> mapOfData = response.as(Map.class);

        System.out.println("mapOfData = " + mapOfData);

        //verify id
        double actualId2 = (double) mapOfData.get("id");
        Assert.assertEquals(actualId2, expectedId);

        //verify email
        String actualEmail2 = (String) mapOfData.get("email");
        Assert.assertEquals(actualEmail2, expectedEmail);

    }
    //test with soft assertion
    @Test
    public void test2SoftAssertion() {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .pathParam("id", 1560419)
                .when()
                .get("https://gorest.co.in/public/v2/users/{id}");

       Map<String,Object>mapOfData= response.as(Map.class);
        double actualId2 = (double) mapOfData.get("id");
        double expectedId = 1560419.0;

        String actualEmail2 = (String) mapOfData.get("email");
        String expectedEmail = "bhargava_nambeesan@jast.example";

        SoftAssert softAssert=new SoftAssert();

        softAssert.assertEquals(actualId2,expectedId);
        softAssert.assertEquals(actualEmail2,expectedEmail);
        softAssert.assertEquals(response.statusCode(),200);

        softAssert.assertAll();
    }
}
