package apiTests.day02;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static io.restassured.RestAssured.*;

public class Test_01_UserGetRequest {

    String bookStoreBaseURL="https://bookstore.toolsqa.com/BookStore/v1";
    String kraftBaseURL="https://www.krafttechexlab.com/sw/api/v1";

    @Test
    public void test1(){
        /** task
         *  Given accept type is JSON
         *  When user send GET request to /Books
         *  Then verify that response status code is 200
         *  and body is JSON format
         *  and response body contains "Richard E. Silverman"
         */

        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get(bookStoreBaseURL + "/Books");

        int statusCode = response.statusCode();
        System.out.println("statusCode = " + statusCode);

        assertEquals(response.statusCode(),200);

        assertEquals(response.contentType(),"application/json; charset=utf-8");

        String responseBody = response.body().asString();
        assertTrue(responseBody.contains("Richard E. Silverman"));

        System.out.println("responseBody = " + responseBody);
    }

    @Test
    public void test2(){
        /** task
         * Get All Users
         * kraft api
         */

        Response response = given().accept(ContentType.JSON)
                .queryParam("pagesize", 20)
                .queryParam("page", 1)
                .and()
                .when().log().all()
                .get(kraftBaseURL + "/allusers/alluser");

        System.out.println("response.statusCode() = " + response.statusCode());

        response.prettyPrint();

    }

    @Test
    public void headerTest(){
        Response response = given().accept(ContentType.JSON)
                .pathParam("userId", 62)
                .when()
                .get(kraftBaseURL + "/allusers/getbyid/{userId}");

        response.prettyPrint();


        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=UTF-8");

        //response headers

        System.out.println("response.headers() = " + response.headers());

        assertEquals(response.header("Content-Length"), "756");
        assertEquals(response.header("Connection"), "Upgrade, Keep-Alive");

        assertTrue(response.headers().hasHeaderWithName("Date"));

        System.out.println("response.header(\"Content-Length\") = " + response.header("Content-Length"));
        System.out.println("response.header(\"Content-Type\") = " + response.header("Content-Type"));
        System.out.println("response.header(\"Date\") = " + response.header("Date"));
        System.out.println("-------------------");
        System.out.println("response.headers() = " + response.headers());
        System.out.println("-------------------");
        Headers headers = response.headers();
        System.out.println(headers.getValue("Date"));


        //verify headers
        assertEquals(response.header("Content-Length"),"756");

        //tarihi assert edelim
        assertTrue(response.headers().hasHeaderWithName("Date"));

        //body'i assert edelim
        assertTrue(response.body().asString().contains("sgezer@gmail.com"));

        System.out.println("+++++++++++++++++++++++++++++++");
        //json path finder ile  https://www.site24x7.com/tools/jsonpath-finder-validator.html

        System.out.println("response.path(\"[0].education[0].fromdate\") = " + response.path("[0].education[0].fromdate"));
        System.out.println("-------------------------");
        System.out.println("response.path(\"[0].education[1].study\") = " + response.path("[0].education[1].study"));
        System.out.println("-------------------------");
        System.out.println("response.path(\"[0]['education'][1]['description']\") = " + response.path("[0]['education'][1]['description']"));
        System.out.println("-------------------------");
        System.out.println("-------------------------");
        System.out.println("response.path(\"[0][\\\"education\\\"][1][\\\"description\\\"]\") = " + response.path("[0][\"education\"][1][\"description\"]"));

        System.out.println("\"****************************\" = " + "****************************");

        // başında x olmuyor................

        System.out.println("response.path(\"x[0].terms\") = " + response.path("[0].terms"));
    }

    /*

    Given accept type is json
    And path param id is 444
    When user sends a get request to "/allusers/getbyid/{id}
    Then status code is 404
    And content-type is "application/json; charset=UTF-8"
    And "No User Record Found..." message should be in response payload

     */

    @Test
    public void negativeTest(){

        Response response=given().accept(ContentType.JSON)
                .pathParam("id",444)
                .when().log().all()
                .get(kraftBaseURL+"/allusers/getbyid/{id}");

        Assert.assertEquals(response.statusCode(),404);
        Assert.assertEquals(response.contentType(),"application/json; charset=UTF-8");

        Assert.assertTrue(response.body().asString().contains("No User Record Found..."));
    }
}
