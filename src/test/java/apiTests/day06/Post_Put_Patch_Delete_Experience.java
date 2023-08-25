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

public class Post_Put_Patch_Delete_Experience {

    Response response;
    int experienceId;

    String email = "sgezer@gmail.com";
    String password = "sg12345678";

    @BeforeClass
    public void beforeClass() {

        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }


    @Test (priority = 0)
    public void addNewExperience() {
        String jsonBody = "{\n" +
                "  \"job\": \"Coğrafyacı\",\n" +
                "  \"company\": \"Aşkabat Lisesi\",\n" +
                "  \"location\": \"Bandırma\",\n" +
                "  \"fromdate\": \"2022-02-16\",\n" +
                "  \"todate\": \"2022-10-17\",\n" +
                "  \"current\": \"false\",\n" +
                "  \"description\": \"İlk işim\"\n" +
                "}";


        response = RestAssured.given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
//                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdXQiOiJhRm0iLCJsaW5rIjoia3JhZnR0ZWNoZXh" +
//                        "sYWIuY29tIiwidXNlcmlkIjo2Miwic3RhcnQiOjE2ODM5NzYwMzQsImVuZHMiOjE2ODQ1ODA4MzR9.zDBfkzOk" +
//                        "UGAT8EeTcsbh-j0xhnPM1TfdgHTDAHbdU6l0_NM4PzxE_WPG_fgs3l4ajkEdttGFRfEceqrplSACzQ")
//                 .header("token",Authorization.getToken())
                .headers(Authorization.getToken(email, password))
                .and()
                .body(jsonBody)
                .when()
                .post("/experience/add")
                .prettyPeek();

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json; charset=UTF-8");

        experienceId = response.path("id");
        System.out.println("experienceId = " + experienceId);

    }

    @Test (priority = 1)
    public void updateExperienceWithPut() {
        /**   //ÖRNEK BODY
         * {
         *   "job": "Fizik Öğretmeni",
         *   "company": "Doğa Koleji",
         *   "location": "İstanbul",
         *   "fromdate": "2021-01-23",
         *   "todate": "2022-12-10",
         *   "current": "false",
         *   "description": "Hard job for a beginner"
         * }
         */
        System.out.println("experienceId = " + experienceId);

        //Map kullanalım...

        Map<String, Object> experienceBody = new HashMap<>();
        experienceBody.put("job", "Fizik Öğretmeni");
        experienceBody.put("company", "Balkan koleji");
        experienceBody.put("location", "İstanbul");
        experienceBody.put("fromdate", "2022-12-11");
        experienceBody.put("todate", "2023-03-03");
        experienceBody.put("current", "false");
        experienceBody.put("description", "Second Job");

        response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
//                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdXQiOiJhRm0iLCJsaW5rIjoia3JhZnR0ZWNoZ" +
//                        "XhsYWIuY29tIiwidXNlcmlkIjo2Miwic3RhcnQiOjE2ODM5NzYwMzQsImVuZHMiOjE2ODQ1ODA4MzR9.zDBfkzOkUG" +
//                        "AT8EeTcsbh-j0xhnPM1TfdgHTDAHbdU6l0_NM4PzxE_WPG_fgs3l4ajkEdttGFRfEceqrplSACzQ")
//                .header("token", Authorization.getToken())
                .headers(Authorization.getToken(email, password))
                .queryParam("id", experienceId)
                .and()
                .body(experienceBody)
                .when().log().all()
                .put("/experience/updateput")
                .prettyPeek();

        Assert.assertEquals(response.statusCode(), 200);

    }

    @Test (priority = 2)
    public void updateExperienceWithPatch() {
        /**   //ÖRNEK BODY
         * {
         *   "job": "Fizik Öğretmeni",
         *   "company": "Doğa Koleji",
         *   "location": "İstanbul",
         *   "fromdate": "2021-01-23",
         *   "todate": "2022-12-10",
         *   "current": "false",
         *   "description": "Hard job for a beginner"
         * }
         */
        System.out.println("experienceId = " + experienceId);

        Map<String, Object> experienceBody = new HashMap<>();
        experienceBody.put("job", "Beden Eğitimi Öğretmeni");
        experienceBody.put("company", "Altay Koleji");
        experienceBody.put("location", "İzmir");


        response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .headers(Authorization.getToken(email, password))
                .pathParam("id", experienceId)
                .and()
                .body(experienceBody)
                .when().log().all()
                .patch("/experience/updatepatch/{id}")
                .prettyPeek();

        Assert.assertEquals(response.statusCode(), 200);

    }

    @Test (priority = 3)
    public void deleteExperience() {


        response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .headers(Authorization.getToken(email, password))
                .pathParam("id", experienceId)
                .and()
                .when().log().all()
                .delete("/experience/delete/{id}")
                .prettyPeek();

        Assert.assertEquals(response.statusCode(), 200);

    }
}
