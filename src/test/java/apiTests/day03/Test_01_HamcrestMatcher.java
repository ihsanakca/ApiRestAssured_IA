package apiTests.day03;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Test_01_HamcrestMatcher {
    @BeforeClass
    public void beforeClass() {

        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }
    //****************************************
    @Test
    public void requestParamsWithMap() {
        /**
         * /*
         *
         *     TASK
         *     Given accept type json
         *     And query  parameter value name Thomas Eduson
         *     And query  parameter value skills Cypress
         *     And query  parameter value pagesize 50
         *     And query  parameter value page 1
         *     When user sends GET request to /allusers/alluser
         *     Then response status code should be 200
         *     And response content-type application/json; charset=UTF-8
         *      */

        Map<String,Object> mapBody=new HashMap<>();
        mapBody.put("pagesize",50);
        mapBody.put("page",1);
        mapBody.put("name","Thomas Eduson");
        mapBody.put("skills","Cypress");

        Response response = given().accept(ContentType.JSON)
                .queryParams(mapBody)
                .when()
                .get("/allusers/alluser");

        Assert.assertEquals(response.statusCode(),200);

    }
//**********************************************
    @Test
    public void getOneUser() {
        /**
         *         given accept type is json
         *         And path param id is 62
         *         When user sends a get request to /allusers/getbyid/{id}
         *         Then status code should be 200
         *         And content type should be "application/json; charset=UTF-8"
         */
        given().accept(ContentType.JSON)
                .pathParam("id", 62)
                .when()
                .get("/allusers/getbyid/{id}")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType("application/json; charset=UTF-8");
    }


    //*************************************************
    @Test
    public void getOneUserWithHamcrestMatcher() {
        /**
         *         given accept type is json
         *         And path param id is 62
         *         When user sends a get request to /allusers/getbyid/{id}
         *         Then status code should be 200
         *         And content type should be "application/json; charset=UTF-8"
         *         user's id should be "62"
         *         user's name should be "Selim Gezer"
         *         user's job should be "QA Automation Engineer"
         *         User's second skill should be "Selenium"
         *         User's third education school name should be "Ankara University"
         *         The response header Content-Lenght should be 756
         *         User's email should be "sgezer@gmail.com"
         *         User's company should be "KraftTech"
         *         Response headers should have "Date" header
         *
         */
        given().accept(ContentType.JSON)
                .pathParam("id", 62)
                .when()
                .get("/allusers/getbyid/{id}")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType("application/json; charset=UTF-8")
                .and()
                .body("[0].id", Matchers.equalTo(62),
                        "[0].name", Matchers.equalTo("Selim Gezer"),
                        "[0].job", equalTo("QA Automation Engineer"),
                        "job[0]",equalTo("QA Automation Engineer"),
                        "skills[0][1]", equalTo("Selenium"),
                        "[0].skills[1]", equalTo("Selenium"),
                        "[0].education[2].school", equalTo("Ankara University"),
                        "education[0][2].school", equalTo("Ankara University"),
                        "education[0].school[2]", equalTo("Ankara University"),
                        "[0].email", equalTo("sgezer@gmail.com"),
                        "email[0]", equalTo("sgezer@gmail.com"),
                        "[0].company", equalTo("KraftTech"),
                        "[0].company", equalTo("KraftTech"),
                        "company[0]", equalTo("KraftTech"))
                .and()
                .header("Content-Length", equalTo("756"))
                .header("Content-Length", "756")
                .headers("Content-Length", "756")
                .headers("Date", notNullValue())
                .header("Date", notNullValue());
    }
    //***************************************************
    @Test
    public void testWithLogs() {
        given().accept(ContentType.JSON)
                .pathParam("id", 62)
                .when().log().all()   //request bilgilerini verir...
                .get("/allusers/getbyid/{id}")
                .then().log().all(); //reponse bilgilerini verir...

    }
    //*******************************************************
    @Test
    public void getAllUserWithHamcrestMatcher() {
        /**
         * 30 user çağıralım
         * ilk sayfa olsun
         * end point "/allusers/alluser"
         * status code 200
         * content type json
         * emaillerde "Ramanzi@test.com" var mı bakalım...
         * emaillerde "Ramanzi@test.com","sgezer@gmail.com" ve "jhon@test.com" var mı toplu bakalım..
         *
         */
        given().accept(ContentType.JSON)
                .queryParam("pagesize", 30)
                .and()
                .queryParam("page", 1)
                .when().log().all()   //request bilgilerini verir...
                .get("/allusers/alluser")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType("application/json;")
                .and()
                .body("email", hasItem("Ramanzi@test.com"))  //email'i bu şekilde çağırırsak bütün hepsini bir liste atıyordu..
                .body("email", hasItems("Ramanzi@test.com", "sgezer@gmail.com", "jhon@test.com"))
                .log().all();
    }
    //***********************************************************
    @Test
    public void getAllUserWithHamcrestMatcher_2() {
        /**
         *
         *          given accept type is json
         *          And query param pagesize is 30
         *          And query param page is 1
         *          And take the request logs
         *          When user sends a get request to /allusers/alluser
         *          Then status code should be 200
         *          And content type should be application/json; charset=UTF-8
         *          And response header content-length should be 5946
         *          And response header Connection should be Upgrade, Keep-Alive
         *          And response headers has Date
         *          And json data should have "Selim Gezer","Jhon Nash","zafer" for name
         *          And json data should have "QA" for job
         *          And json data should have "İTÜ" for the tenth user's education school
         *          And json data should have "Junior Developer" for the first user's third experience job
         *          And json data should have "Odtü" for the last user's first education company
         *          Only take the response headers log.
         *
         */
        given().accept(ContentType.JSON)
                .queryParam("pagesize", 30)
                .and()
                .queryParam("page", 1)
                .when().log().all()   //request bilgilerini verir...
                .get("allusers/alluser")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType("application/json;")
                .and()
                .headers("Content-Length", "6632")
                .header("Content-Length", equalTo("6632"))
                .header("Connection", equalTo("Upgrade, Keep-Alive"))
                .headers("Date", notNullValue())
                .header("Date", notNullValue())
                .and()
                .body("name",hasItems("Selim Gezer","Jhon Nash","zafer" ),
                        "job",hasItem("QA"),
                        "education[9].school",hasItem("İTÜ"),
                        "[9].education.school",hasItem("İTÜ"),
                    //    "education.school",hasValue("bilkent"),  //list of list veriyor... hasItem metodu list ile çalışıyor..
                        "[0].experience[2].job",equalTo("Junior Developer"), //aynı
                        "experience[0][2].job",equalTo("Junior Developer"),  //aynı
                        "experience[0].job[2]",equalTo("Junior Developer"),  //aynı
                        "[0].experience.job[2]",equalTo("Junior Developer"),  //aynı
                        "[29].education.school[0]",equalTo("Odtü"),
                        "education[29][0].school",equalTo("Odtü"))
                .and().log().headers();//sadece response headerları loglar

    }


}
