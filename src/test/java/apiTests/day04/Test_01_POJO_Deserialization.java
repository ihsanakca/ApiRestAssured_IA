package apiTests.day04;

import apiPOJOTemplates.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Test_01_POJO_Deserialization {
    /**
    //TASK
    //base url = https://gorest.co.in
    //end point = /public/v2/users
    //path parameter = {id} --> 1549066
    //send a get request with the above credentials
    //parse to Json object to pojo (custom java class)
    //verify that the body below
    /*
     {
     "id": 1597739,
     "name": "Gajbaahu Sharma",
     "email": "sharma_gajbaahu@hammes.test",
     "gender": "female",
     "status": "inactive"
     }
     */
//****************************************
// custom java class User
    @Test
    public void test1() {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .pathParam("id", 1549066)
                .when()
                .get("https://gorest.co.in/public/v2/users/{id}");

        //assert status code
        Assert.assertEquals(response.statusCode(),200);

        //Assert the body with POJO  (Bu apinin datası sürekli değişmektedir.)
        User user = response.as(User.class);

        System.out.println("user.getId() = " + user.getId());
        int actualId=user.getId();
        int expectedId=1549066;
        Assert.assertEquals(actualId,expectedId);

        System.out.println("user.getName() = " + user.getName());
        String actualName=user.getName();
        String expectedName="Leela Kakkar";
        Assert.assertEquals(actualName,expectedName);

        //diğerleri de benzer şekilde assert edilebilir...
        System.out.println("user.getEmail() = " + user.getEmail());
        System.out.println("user.getGender() = " + user.getGender());
        System.out.println("user.getStatus() = " + user.getStatus());

    }
}
