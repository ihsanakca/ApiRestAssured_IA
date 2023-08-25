package apiTests.day03;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class Test_02_JsonToJavaCollection {
    @BeforeClass
    public void beforeClass() {

        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    //**************
    @Test
    public void allUsersToMap() {
        Response response = given().accept(ContentType.JSON)
                .queryParam("pagesize", 30)
                .and()
                .queryParam("page", 1)
                .when()
                .get("/allusers/alluser");

        assertEquals(response.statusCode(), 200);

        //json body'i java collection'a çevirmeye de-serialize denir... tersi  de olur ona serialize denir.
        List<Map<String, Object>> allUserMap = response.body().as(List.class);
//        System.out.println("allUserMap = " + allUserMap);

        // 10. user'ın adını assert edelim "Selim Gezer"
        String actualName = (String) allUserMap.get(9).get("name");
        assertEquals(actualName, "Selim Gezer");

        //10.user'ın skillerinin ikincisinin Selenium olduğunu verify edelim
        List skills = (List) allUserMap.get(9).get("skills");
        assertEquals(skills.get(1), "Selenium");
        System.out.println("skills.get(1) = " + skills.get(1));

        //DİREKT alabilir miyim?
        String skills1 = (String) ((List<?>) allUserMap.get(9).get("skills")).get(1);
        System.out.println("skills1 = " + skills1);

        //10.user'ın educationın 3.sünün school adının Ankara University olduğunu verify edelim

        List<Map<String, Object>> educationMapOfTenthUser = (List<Map<String, Object>>) allUserMap.get(9).get("education");

//        System.out.println("educationMapOfTenthUser = " + educationMapOfTenthUser);
        String actualSchoolName = (String) educationMapOfTenthUser.get(2).get("school");
        assertEquals(actualSchoolName, "Ankara University");


    }

    //**************
// Fatih Hoca'nın taskı
    @Test
    public void testBookStoreJsonToJava() {
        /**
         * given accep type json
         * request url:https://demoqa.com/Account/v1/User/11
         * then status code 401
         * de-serialize --> json to java collection
         * verify that message is "User not authorized!"
         * verify that code is 1200
         */

        Response response = given().accept(ContentType.JSON).
                and()
                .pathParam("id", 11)
                .when()
                .get("https://demoqa.com/Account/v1/User/{id}");

        assertEquals(response.statusCode(), 401);

        Map<String, Object> jsonMap = response.body().as(Map.class);
        System.out.println("jsonMap = " + jsonMap);

        //verify the message
        String message = (String) jsonMap.get("message");
        System.out.println("message = " + message);
        assertEquals(message, "User not authorized!");

        //verify the code
        String code = (String) jsonMap.get("code");
        System.out.println("code = " + code);
        assertEquals(code, "1200");
    }
}
