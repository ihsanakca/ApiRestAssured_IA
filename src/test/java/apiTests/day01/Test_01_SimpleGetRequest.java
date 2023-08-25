package apiTests.day01;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Test_01_SimpleGetRequest {
    String petStoreBaseURL = "https://petstore.swagger.io/v2";

    @Test
    public void test1() {
        Response response = RestAssured.get(petStoreBaseURL + "/store/inventory");

        //print status code
        int statusCode = response.getStatusCode();
        System.out.println("statusCode = " + statusCode);

        //print body
        response.prettyPrint();

    }

    @Test
    public void test2() {
        Response response = RestAssured
                .given()
                .headers("Accept",ContentType.JSON) //gerekli değil
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .get(petStoreBaseURL + "/store/inventory");

        System.out.println("response.statusCode() = " + response.statusCode());

        //status codu Assert edelim
        Assert.assertEquals(response.statusCode(), 200);

        response.prettyPrint();

        //content-type'ı assert edelim
        Assert.assertEquals(response.contentType(), "application/json");
        System.out.println(response.contentType());
    }
    @Test
    public void test3() {
        // RestAssured Library kullanarak assert edelim
        RestAssured.given().accept(ContentType.JSON)
                .when()
                .get(petStoreBaseURL + "/store/inventory")
                .then()
                .assertThat().statusCode(200)
                .and()
                .contentType("application/json");

    }

    @Test
    public void test4() {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get(petStoreBaseURL + "/store/inventory");

        Assert.assertEquals(response.statusCode(), 200);

        //Response Body'i assert edelim..
        System.out.println("response.body().asString() = " + response.body().asString());

        Assert.assertTrue(response.body().asString().contains("good pet"));

    }
}
