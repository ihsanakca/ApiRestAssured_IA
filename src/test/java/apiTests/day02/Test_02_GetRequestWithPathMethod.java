package apiTests.day02;

import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class Test_02_GetRequestWithPathMethod {

    @BeforeClass
    public void beforeClassSetUp() {
        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void testWithPathMethod() {
        /**
         *         "id": 24,
         *         "name": "mike",
         *         "email": "mike@gmail.com",
         *         "password": "$2y$10$KWJ2f3iTUFvkvzTS7/O0AOBmfwYknjscuwdA8n4c25gkzFqi9tswW",
         *         "about": "Excellent QA",
         *         "terms": "2",
         *         "date": "2022-09-12 20:50:38",
         *         "job": "SDET",
         *         "company": "Amazon",
         *         "website": "Krafttechnologie",
         *         "location": "USD",
         *         "skills": [
         *             "Cucumber",
         *             "TestNG"
         *         ],
         */

        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("userId", 24)
                .when()
                .get("/allusers/getbyid/{userId}");

        assertEquals(response.statusCode(), 200);

        //path metodu ile bodyden veri alma
        System.out.println("response.body().path(\"name\").toString() = " + response.body().path("name").toString());
        System.out.println("response.path(\"name\").toString() = " + response.path("name").toString());
        System.out.println("response.path(\"name\") = " + response.path("name"));
        System.out.println("response.path(\"id\") = " + response.path("id"));
        System.out.println("response.path(\"email\") = " + response.path("email"));
        System.out.println("response.path(\"company\") = " + response.path("company"));
        System.out.println("response.path(\"website\") = " + response.path("website"));

        //bilgileri assert edelim
        int userId = response.path("id[0]");
        assertEquals(userId, 24);
        assertEquals(response.path("email[0]"), "mike@gmail.com");
        assertEquals(response.path("company[0]"), "Amazon");

    }

    @Test
    public void testAllUsersWithPathMethod() {
        /**Class Task
         * Given accept type JSON
         * and Query parameter value pagesize 50
         * and Query parameter value page 1
         * When user send GET request to /allusers/alluser
         * Then response status code is 200
         * And response content type is "application/json; charset=UTF-8"
         * Verify that first id 1
         * verify that first name MercanS
         * verify that last id is 102
         * verify that last name is GHAN
         */

        Response response = given()
                .accept(ContentType.JSON)
                .queryParam("pagesize", 50)
                .queryParam("page", 1)
                .and()
                .when().log().all()
                .get("/allusers/alluser");

        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=UTF-8");

        //headerları kontrol edelim
        assertEquals(response.header("Content-Type"), "application/json; charset=UTF-8");
        assertEquals(response.getHeader("Content-Type"), "application/json; charset=UTF-8");
        assertTrue(response.headers().toString().contains("Content-Type"));

        //ilk elemanı assert edelim
        int id1 = response.path("id[0]");
        assertEquals(id1, 1);
        assertEquals(response.path("name[0]"), "MercanS");

        int lastId = response.path("id[-1]");
        assertEquals(lastId, 102);
        assertEquals(response.path("name[-1]"), "GHAN");

        System.out.println("response.path(\"id[49]\") = " + response.path("id[49]"));
        System.out.println("response.path(\"name[49]\") = " + response.path("name[49]"));

        //3. elemanın companysini alalım
        System.out.println("response.path(\"company[2]\") = " + response.path("company[2]"));
        assertEquals(response.path("company[2]"), "Amazon");

        //3.elemanın skillsini alalım
        System.out.println("response.path(\"skills[2]\") = " + response.path("skills[2]"));

        //3.elemanın skillsini alalım ve ikincisini assert edelim
        System.out.println("response.path(\"skills[2][1]\") = " + response.path("skills[2][1]"));
        assertEquals(response.path("skills[2][1]"), "TestNG");

        //3.elemanın 2.education'ın school'unu alalım
        System.out.println("response.path(\"education[2].school[1]\") = " + response.path("education[2].school[1]"));
        assertEquals(response.path("education[2].school[1]"), "Krafttech Technologie Bootcamp");

        //son elemanın education'ın degree'sini alalım
        System.out.println("response.path(\"education[-1].degree[0]\") = " + response.path("education[-1].degree[0]"));

        //20. elemanın education bilgilerini alalım
       List<String > listEducation= response.path("education[19]");
        System.out.println("listEducation = " + listEducation);

        //20.elemanın ikinci education'ın bilgilerini alalım
        Map<String,Object>mapSecondEducation=response.path("education[19][1]");
        System.out.println("mapSecondEducation = " + mapSecondEducation);

        //20.elemanın ikinci education'ın id.sini alalım
        System.out.println("mapSecondEducation.get(\"id\") = " + mapSecondEducation.get("id"));
        System.out.println("response.path(\"education[19].id[1]\") = " + response.path("education[19].id[1]"));

    }

    @Test
    public void testBookStoreGetBooks() {
        String bookStoreBaseURL="https://bookstore.toolsqa.com/BookStore";
        /**
    Given accept type json
    When user sends a get request to https://bookstore.toolsqa.com/BookStore/v1/Books
    Then status code should be 200
    And content type should be application/json; charset=utf-8
    And the first book isbn should be 9781449325862
    And the first book publisher should be O'Reilly Media

     */

        Response response = given()
                .accept(ContentType.JSON)
                .when().log().all()
                .get(bookStoreBaseURL + "/v1/Books");

        //status codu assert edelim
        assertEquals(response.statusCode(),200);
        //content type'ı assert edelim.
        assertEquals(response.contentType(),"application/json; charset=utf-8");

        //isbn'yi assert edelim
        System.out.println("response.path(\"books.isbn[0]\") = " + response.path("books.isbn[0]"));
        assertEquals(response.path("books.isbn[0]"),"9781449325862");
        //2.yol
        System.out.println("response.path(\"books[0].isbn\") = " + response.path("books[0].isbn"));
        assertEquals(response.path("books[0].isbn"),"9781449325862");

        //publisher'ı assert edelim
        System.out.println("response.path(\"books.publisher[0]\") = " + response.path("books.publisher[0]"));
        assertEquals(response.path("books.publisher[0]"),"O'Reilly Media");
        //2.yol
        System.out.println("response.path(\"books[0].publisher\") = " + response.path("books[0].publisher"));
        assertEquals(response.path("books[0].publisher"),"O'Reilly Media");

        //son kitabın publisher'ını alalım
        System.out.println("response.path(\"books[7].publisher\") = " + response.path("books[7].publisher"));
        System.out.println("response.path(\"books.publisher[7]\") = " + response.path("books.publisher[7]"));

        //bütün isbn'leri alalım..
        System.out.println("response.path(\"books.isbn\") = " + response.path("books.isbn"));

    }
}
