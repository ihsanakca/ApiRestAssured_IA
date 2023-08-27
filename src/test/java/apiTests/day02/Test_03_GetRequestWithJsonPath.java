package apiTests.day02;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.testng.Assert.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class Test_03_GetRequestWithJsonPath {

    @BeforeClass
    public void beforeClass() {

        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    /**
     * TASK
     * Given accept type is json
     * And Path param user id is 62
     * When user sends a GET request to /allusers/getbyid/{id}
     * Then the status Code should be 200
     * And Content type json should be "application/json; charset=UTF-8"
     * And user's name should be Selim Gezer
     * And user's id should be 62
     * And user's email should be sgezer@gmail.com
     */

    @Test
    public void getUserWithPathMethod() {

        Response response = given().accept(ContentType.JSON)
                .pathParam("userId", 62)
                .when().log().all()
                .get("/allusers/getbyid/{userId}");

        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=UTF-8");

        // path metodu ile id'yi assert edelim
        int userId = response.path("id[0]");
        assertEquals(userId, 62);

        // path metodu ile ismi assert edelim
        assertEquals(response.path("name[0]"), "Selim Gezer");

        //  path metodu ile emaili assert edelim
        assertEquals(response.path("email[0]"), "sgezer@gmail.com");
    }

    @Test
    public void getUserWithJsonPathMethod() {

        Response response = given().accept(ContentType.JSON)
                .pathParam("userId", 62)
                .when().log().all()
                .get("/allusers/getbyid/{userId}");

        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=UTF-8");

        // Json path metodu kullanalım
        JsonPath jsonPath = response.jsonPath();

        //json path ile id'yi assert edelim
        int userId = jsonPath.get("id[0]");
        assertEquals(userId, 62);

        //json path ile name'i assert edelim
        assertEquals(jsonPath.get("name[0]"), "Selim Gezer");

        //json path ile email'i assert edelim
        assertEquals(jsonPath.get("email[0]"), "sgezer@gmail.com");

    }

    /**
     * TASK
     * Given accept type is json
     * And Path param user id is 61
     * When user sends a GET request to /allusers/getbyid/{id}
     * And Content type json should be "application/json; charset=UTF-8"
     * And user's name should be talip tiras
     * And user's id should be 61
     * And user's email should be taliptiras4186@gmail.com
     */

    @Test
    public void testWithJsonPathMethod() {

        Response response = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParam("id", 61)
                .when().log().all()
                .get("/allusers/getbyid/{id}");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=UTF-8");

        //jsonPath ile assert edelim
        JsonPath jsonPath=response.jsonPath();

        //name
        assertEquals(jsonPath.get("name[0]"),"talip tiras");

        //id-----getInt() metodu ile
        assertEquals(jsonPath.getInt("id[0]"),61);

        //email
        assertEquals(jsonPath.get("email[0]"),"taliptiras4186@gmail.com");

    }

    /**
     * /
     *     TASK
     *     Given accept type is json
     *     When user sends a GET request to /allusers/alluser
     *     Then the status Code should be 200
     *     And Content type json should be "application/json; charset=UTF-8"
     *
     */

    @Test
    public void testWithJsonPathMethod_2() {
        Response response = given().accept(ContentType.JSON)
                .queryParam("pagesize", 5)
                .queryParam("page", 1)
                .when().log().all()
                .get("/allusers/alluser");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=UTF-8");

        //jsonPath ile devam edelim
        JsonPath jsonPath=response.jsonPath();

        //ilk elemanın id'sinin 1 olduğunu assert edelim...
        assertEquals(jsonPath.getInt("id[0]"),1);

        //beşinci elemanın id sinin 33 olduğunu assert edelim
        assertEquals(jsonPath.getInt("id[4]"),33);

        //dördüncü elemanın adının wilini3845@once olduğunu assert edelim.
        assertEquals(jsonPath.getString("name[3]"),"wilini3845@once");

        //bütün idleri alalım ve assert edelim [1, 5, 24, 29, 33]
        List<Integer> expectedIDs=new ArrayList<>(Arrays.asList(1,5,24,29,33));

        List<Integer> allIds = jsonPath.getList("id");
        System.out.println("allIds = " + allIds);
        assertEquals(allIds,expectedIDs);
        System.out.println("----------------------------");

        //2.yol path method ile
        List<Integer> allIds_2=response.path("id");
        System.out.println("allIds_2 = " + allIds_2);
        assertEquals(allIds_2,expectedIDs);
        System.out.println("----------------------------");

        //ilk elemanın skillerini alalım ve assert edelim
        List<String> expectedUserOneSkills=new ArrayList<>(Arrays.asList("PHP","Java"));
        List<String> userOneSkills = jsonPath.getList("skills[0]");
        assertEquals(userOneSkills,expectedUserOneSkills);
        System.out.println("userOneSkills = " + userOneSkills);
        System.out.println("----------------------------");

        //beşinci elemanın skillerinin ikincisinin "Cucumber" olduğunu assert edelim
        List<String > actualUserFiveSkills=jsonPath.getList("skills[4]");
        String actualUserFiveSecondSkill=actualUserFiveSkills.get(1);
        String expectedUserFiveSecondSkill="Cucumber";
        assertEquals(actualUserFiveSecondSkill,expectedUserFiveSecondSkill);
        System.out.println("actualUserFiveSecondSkill = " + actualUserFiveSecondSkill);
        System.out.println("----------------------------");

        //2.yol
        String actualUserFiveSecondSkill_1 = jsonPath.getString("skills[4][1]");
        assertEquals(actualUserFiveSecondSkill_1,expectedUserFiveSecondSkill);
        System.out.println("actualUserFiveSecondSkill_1 = " + actualUserFiveSecondSkill_1);
        System.out.println("----------------------------");

        //3.yol path method ile
        String actualUserFiveSecondSkill_2 = response.path("skills[4][1]");
        assertEquals(actualUserFiveSecondSkill_2,expectedUserFiveSecondSkill);
        System.out.println("actualUserFiveSecondSkill_2 = " + actualUserFiveSecondSkill_2);
        System.out.println("----------------------------");

        //bütün elemanların bütün skillerini alalım (List of List)
        List<List<String>> allUserAllSkills=jsonPath.getList("skills");
        System.out.println("allUserAllSkills = " + allUserAllSkills);
        System.out.println("----------------------------");

        //2.yol  path method ile
        List<List<String>> allUserAllSkills_2=response.path("skills");
        System.out.println("allUserAllSkills_2 = " + allUserAllSkills_2);
        System.out.println("----------------------------");

        //ilk elemanın eğitimlerinden ilkinin scholl'unun "School or Bootcamp" olduğunu assert edelim.
        //1.yol map ile
        Map<String, Object> userOneFirstEducationMap = jsonPath.getMap("education[0][0]");
        String actualUserOneFirstEducationSchool = (String) userOneFirstEducationMap.get("school");
        String expectedUserOneFirstEducationSchool = "School or Bootcamp";
        assertEquals(actualUserOneFirstEducationSchool,expectedUserOneFirstEducationSchool);
        System.out.println("actualUserOneFirstEducationSchool = " + actualUserOneFirstEducationSchool);
        System.out.println("----------------------------");

        //path metot ile yapalım---->gene map ile
        Map<String, Object> userOneFirstEducationMap_1=response.path("education[0][0]");
        String actualUserOneFirstEducationSchool_1= (String) userOneFirstEducationMap_1.get("school");
        assertEquals(actualUserOneFirstEducationSchool_1,expectedUserOneFirstEducationSchool);
        System.out.println("actualUserOneFirstEducationSchool_1 = " + actualUserOneFirstEducationSchool_1);
        System.out.println("----------------------------");

        //2.yol (bunu görmediler)
        String actualUserOneFirstEducationSchool_2=jsonPath.get("education[0][0].school");
        assertEquals(actualUserOneFirstEducationSchool_2,expectedUserOneFirstEducationSchool);
        System.out.println("actualUserOneFirstEducationSchool_2 = " + actualUserOneFirstEducationSchool_2);
        System.out.println("----------------------------");

        //3.yol
        String actualUserOneFirstEducationSchool_3=jsonPath.get("education[0].school[0]");
        assertEquals(actualUserOneFirstEducationSchool_3,expectedUserOneFirstEducationSchool);
        System.out.println("actualUserOneFirstEducationSchool_3 = " + actualUserOneFirstEducationSchool_3);
        System.out.println("----------------------------");

        //4.yol
        String actualUserOneFirstEducationSchool_4=jsonPath.get("[0].education.school[0]");
        assertEquals(actualUserOneFirstEducationSchool_4,expectedUserOneFirstEducationSchool);
        System.out.println("actualUserOneFirstEducationSchool_4 = " + actualUserOneFirstEducationSchool_4);
        System.out.println("----------------------------");

        //5.yol
        String actualUserOneFirstEducationSchool_5=jsonPath.get("[0].education[0].school");
        assertEquals(actualUserOneFirstEducationSchool_5,expectedUserOneFirstEducationSchool);
        System.out.println("actualUserOneFirstEducationSchool_5 = " + actualUserOneFirstEducationSchool_5);
        System.out.println("----------------------------");

        //path metotu ile yapalım
        System.out.println("response.path(\"education[0].school[0]\") = " + response.path("education[0].school[0]"));
        System.out.println("----------------------------");

        //list of map ile yapalım
        List<Map<String,Object>> userOneAllEducationInfoMap=jsonPath.getList("education[0]");
        System.out.println("userOneAllEducationInfoMap.get(0).get(\"school\") = " + userOneAllEducationInfoMap.get(0).get("school"));
        System.out.println("----------------------------");

        //list of map'i path metodu ile yapalım
        List<Map<String,Object>> userOneAllEducationInfoMap_2=response.path("education[0]");
        System.out.println("userOneAllEducationInfoMap_2.get(0).get(\"school\") = " + userOneAllEducationInfoMap_2.get(0).get("school"));
        System.out.println("----------------------------");

        //ilk elemanın ikinci education'nın school'unu alalım (çeşitli örnekler)
        System.out.println("jsonPath.get(\"education[0][1].school\") = " + jsonPath.get("education[0][1].school"));
        //veya
        System.out.println("jsonPath.get(\"education[0].school[1]\") = " + jsonPath.get("education[0].school[1]"));
       //veya path metot ile
        System.out.println("response.path(\"education[0][1].school\") = " + response.path("education[0][1].school"));

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");

        List<List<String>> education = jsonPath.getList("education");
        System.out.println("education = " + education);
        List<List<Map<String ,Object>>> allEducations=jsonPath.getList("education");
        System.out.println("allEducations.get(0).get(0).get(\"school\") = " + allEducations.get(0).get(0).get("school"));


    }
    /**
     * query paramsı map ile girmek.....
     */
}
