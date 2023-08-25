package apiTests.day06;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;

public class Authorization {
    @BeforeClass
    public void beforeClass() {

        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

   @Test
    public void test1(){
       String email="sgezer@gmail.com";
       String password="sg12345678";
       Response response = RestAssured.given()
               .accept(ContentType.MULTIPART)  // ContentType.ANY de olur... ancak ikisi beraber olmaz
               .and()
               .formParam("email", email)
               .and()
               .formParam("password", password)
               .and()
               .when().log().all()
               .post("/allusers/login");

       Assert.assertEquals(response.statusCode(),200);

       String token=response.path("token");
       System.out.println("token = " + token);
   }

   public static String getToken(){
       String email="sgezer@gmail.com";
       String password="sg12345678";
       Response response = RestAssured.given()
               .accept(ContentType.MULTIPART)
               .and()
               .formParam("email", email)
               .and()
               .formParam("password", password)
               .and()
               .when().log().all()
               .post("/allusers/login");

       Assert.assertEquals(response.statusCode(),200);

       String token=response.path("token");
       return token;
   }

    public static Map<String,Object> getToken(String email, String password){

        Response response = RestAssured.given()
                .accept(ContentType.MULTIPART)
                .and()
                .formParam("email", email)
                .and()
                .formParam("password", password)
                .and()
                .when().log().all()
                .post("/allusers/login");

        Assert.assertEquals(response.statusCode(),200);

        String token=response.path("token");
        Map<String,Object> authorization=new HashMap<>();
        authorization.put("token",token);
        return authorization;
    }
}
