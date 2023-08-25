package apiTests.day05;

import apiPOJOTemplates.PostNewUserKraft;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class Test_02_PostMethod {
    Faker faker = new Faker();


    @BeforeClass
    public void beforeClass() {

        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }
    //POST METHOD

    /**
     * Along with the others, there are 3 particular ways to provide data into the request body when we use post method:
     * 1. Assign the JSON body inside a string variable and put it into the body() method
     * 2. Put data inside a map and provide it into the body() method
     * NOTE:body() method converts the data inside the map to JSON automatically. This only happens with POST,PUT and PATCH method.
     * 3. Put data into an object which is created based on a java custom class and put it into the body() method.
     */

    //TASK
    /*
    baseUrl = https://www.krafttechexlab.com/sw/api/v1
    endpoint = /allusers/register
    Given accept type and Content type is JSON
    And request json body is:
    {
    "name": "Melih Gezer",
    "email": "mgezer@mgezer.com",
    "password": "Mg12345678",
    "about": "from Bursa",
    "terms": "3"
    }
    When user sends POST request
    Then status code 200
    And content type should be application/json
    And json payload/response/body should contain:
    a new generated id that is special for user
    verify name and email

     */

    //****************************************
    @Test
    public void postWithString() {
        String jsonBody = "{\n" +
                "  \"name\": \"Melih Gezer\",\n" +
                "  \"email\": \"mgezer@mgezer.com\",\n" +
                "  \"password\": \"Mg12345678\",\n" +
                "  \"about\": \"from Bursa\",\n" +
                "  \"terms\": \"3\"\n" +
                "}";

        Response response = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(jsonBody)  //serialization
                .when().log().all()
                .post("/allusers/register");

        //verify status code
        Assert.assertEquals(response.statusCode(), 200);

        //response bodyi görelim
        response.prettyPrint();

        //verify id
        Assert.assertNotNull(response.path("id"));

        //verify name
        Assert.assertEquals(response.path("name"), "Melih Gezer");

        //verify email
        Assert.assertEquals(response.path("email"), "mgezer@mgezer.com");

        //verify about
        Assert.assertEquals(response.path("about"), "from Bursa");

        //verify terms
        Assert.assertEquals(response.path("terms"), "3");
    }

    //****************************************
    @Test
    public void postWithStringWithJavaFaker() {
        String jsonBody = "{\n" +
                "  \"name\": \"" + faker.name().fullName() + "\",\n" +
                "  \"email\": \"" + faker.internet().emailAddress() + "\",\n" +
                "  \"password\": \"" + faker.internet().password() + "\",\n" +
                "  \"about\": \"" + faker.internet().domainWord() + "\",\n" +
                "  \"terms\": \"" + faker.number().digit() + "\"\n" +
                "}";

        Response response = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(jsonBody)   //serialization
                .when().log().all()
                .post("/allusers/register");


        //verify status code
        Assert.assertEquals(response.statusCode(), 200);

        //response bodyi görelim
        response.prettyPrint();

        //verify id
        Assert.assertNotNull(response.path("id"));

        //verify name
        Assert.assertNotNull(response.path("name"));

        //verify email
        Assert.assertNotNull(response.path("email"));

        //verify about
        Assert.assertNotNull(response.path("about"));

        //verify terms
        Assert.assertNotNull(response.path("terms"));
    }

    //****************************************
    @Test
    public void postWithMapWithJavaFaker() {
        //fake infoları oluşturalım
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        String about = faker.internet().domainWord();
        String terms = faker.number().digit();

        //json bodyi map'e atalım..
        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("name", name);
        jsonBody.put("email", email);
        jsonBody.put("password", password);
        jsonBody.put("about", about);
        jsonBody.put("terms", terms);


        Response response = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(jsonBody)    //serialization
                .when().log().all()
                .post("/allusers/register");


        //verify status code
        Assert.assertEquals(response.statusCode(), 200);

        //response bodyi görelim
        response.prettyPrint();

        //verify id
        Assert.assertNotNull(response.path("id"));

        //verify name
        Assert.assertEquals(response.path("name"), name);

        //verify email
        Assert.assertEquals(response.path("email"), email);

        //verify about
        Assert.assertEquals(response.path("about"), about);

        //verify terms
        Assert.assertEquals(response.path("terms"), terms);
    }

//TASK
    /*
    baseUrl = https://www.krafttechexlab.com/sw/api/v1
    endpoint = /allusers/register
    Given accept type and Content type is JSON
    And request json body is:
   {
    "name": "Melih Gezer",
    "email": "mgezer1@mgezer.com",
    "password": "Mg12345678",
    "about": "from Bursa",
    "terms": "3"
    }
    When user sends POST request
    Then status code 200
    And content type should be application/json
    And json payload/response/body should contain:
    a new generated id that is special for user
    name
    email
    ...
     */

    //THIRD WAY
    //JAVA CUSTOM CLASS

    //****************************************
    @Test
    public void postWithCustomClass() {
        PostNewUserKraft postNewUserKraft1 = new PostNewUserKraft();
        postNewUserKraft1.setName("Melih Gezer");
        postNewUserKraft1.setEmail("mgezer1@mgezer.com");
        postNewUserKraft1.setPassword("Mg12345678");
        postNewUserKraft1.setAbout("from Bursa");
        postNewUserKraft1.setTerms("3");

        System.out.println("postNewUserKraft1 = " + postNewUserKraft1);

        Response response = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(postNewUserKraft1)  //serialization
                .when()
                .post("/allusers/register");

        //verify status code
        Assert.assertEquals(response.statusCode(), 200);

        //response bodyi görelim
        response.prettyPrint();

        //verify id
        Assert.assertNotNull(response.path("id"));

        //verify name
        Assert.assertEquals(response.path("name"), "Melih Gezer");

        //verify email
        Assert.assertEquals(response.path("email"), "mgezer1@mgezer.com");

        //verify about
        Assert.assertEquals(response.path("about"), "from Bursa");

        //verify terms
        Assert.assertEquals(response.path("terms"), "3");

    }

    //****************************************
    @Test
    public void postWithCustomClassWithConstructor() {
        /**
         *
         "name": "Melih Gezer",
         "email": "mgezer2@mgezer.com",
         "password": "Mg12345678",
         "about": "from Bursa",
         "terms": "3"
         }
         */
        PostNewUserKraft postNewUserKraft2 = new PostNewUserKraft("Melih Gezer", "mgezer2@mgezer.com",
                "Mg12345678", "from Bursa", "3");

        System.out.println("postNewUserKraft2 = " + postNewUserKraft2);

        Response response = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(postNewUserKraft2)  //serialization
                .when()
                .post("/allusers/register");

        //verify status code
        Assert.assertEquals(response.statusCode(), 200);

        //response bodyi görelim
        response.prettyPrint();

        //verify id
        Assert.assertNotNull(response.path("id"));

        //verify name
        Assert.assertEquals(response.path("name"), "Melih Gezer");

        //verify email
        Assert.assertEquals(response.path("email"), "mgezer2@mgezer.com");

        //verify about
        Assert.assertEquals(response.path("about"), "from Bursa");

        //verify terms
        Assert.assertEquals(response.path("terms"), "3");

    }
}
