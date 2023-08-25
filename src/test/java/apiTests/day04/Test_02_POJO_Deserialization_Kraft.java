package apiTests.day04;

import apiPOJOTemplates.Education;
import apiPOJOTemplates.Experience;
import apiPOJOTemplates.KraftUser1;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.baseURI;

public class Test_02_POJO_Deserialization_Kraft {

    @BeforeClass
    public void beforeClass() {

        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    /**
     * TASK
     * base url = https://www.krafttechexlab.com/sw/api/v1
     * end point /allusers/getbyid/{id}
     * id parameter value is 1
     * send the GET request
     * then status code should be 200
     * get all data into a custom class (POJO) by de-serilization
     */

    @Test
    public void test1() {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .pathParam("id", 1)
                .when()
                .get("/allusers/getbyid/{id}");

        //verify status code
        Assert.assertEquals(response.statusCode(), 200);

        KraftUser1[] kraftUser1 = response.as(KraftUser1[].class);

        //Arrayin boyunu alalım
        System.out.println("kraftUser1.length = " + kraftUser1.length);

        // id'yi alalım
        System.out.println("kraftUser1[0].getId() = " + kraftUser1[0].getId());

        //name'i alalım
        System.out.println("kraftUser1[0].getName() = " + kraftUser1[0].getName());

        //location'ı alalım
        System.out.println("kraftUser1[0].getLocation() = " + kraftUser1[0].getLocation());

        //ilk skilli alalım
        List<String> skills = kraftUser1[0].getSkills();
        System.out.println("skills.get(0) = " + skills.get(0));
        System.out.println("kraftUser1[0].getSkills().get(0) = " + kraftUser1[0].getSkills().get(0));

        //education ile ilgili bilgileri alalım
        List<Education> education = kraftUser1[0].getEducation();

        //ilk education bilgisinin school bilgisini alalım
        Education education1 = education.get(0);
        System.out.println("education1.getSchool() = " + education1.getSchool());

        // ilk education bilgisinin school bilgisini  DIREKT alalım
        System.out.println("kraftUser1[0].getEducation().get(0).getSchool() = " + kraftUser1[0].getEducation().get(0).getSchool());

        //ikinci experience bilgisinin company bilgisini alalım
        List<Experience> experience = kraftUser1[0].getExperience();
        Experience experience1 = experience.get(1);
        System.out.println("experience1.getCompany() = " + experience1.getCompany());

        // ikinci experince bilgisinin company bilgisini  DIREKT alalım
        System.out.println("kraftUser1[0].getExperience().get(1).getCompany() = " + kraftUser1[0].getExperience().get(1).getCompany());
    }

    //**************//****************************************
    /**
     * TASK
     * base url = https://www.krafttechexlab.com/sw/api/v1
     * end point /allusers/getbyid/{id}
     * id parameter value is 62
     * send the GET request
     * then status code should be 200
     * get all data into a custom class (POJO) by de-serilization
     */
    //**************//****************************************
    @Test
    public void test2() {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .pathParam("id", 62)
                .when()
                .get("/allusers/getbyid/{id}");

        //verify status code
        Assert.assertEquals(response.statusCode(), 200);

        KraftUser1[] kraftUser1 = response.as(KraftUser1[].class);  //başlangıcı {} olan json bodylerde [] konulmadan yapılır..

        //Arrayin boyunu alalım
        System.out.println("kraftUser1.length = " + kraftUser1.length);

        // id'yi alalım
        System.out.println("kraftUser1[0].getId() = " + kraftUser1[0].getId());

        //name'i alalım
        System.out.println("kraftUser1[0].getName() = " + kraftUser1[0].getName());

        //location'ı alalım
        System.out.println("kraftUser1[0].getLocation() = " + kraftUser1[0].getLocation());

        //ilk skilli alalım
        List<String> skills = kraftUser1[0].getSkills();
        System.out.println("skills.get(0) = " + skills.get(0));
        System.out.println("kraftUser1[0].getSkills().get(0) = " + kraftUser1[0].getSkills().get(0));

        //education ile ilgili bilgileri alalım
        List<Education> education = kraftUser1[0].getEducation();

        //ilk education bilgisinin school bilgisini alalım
        Education education1 = education.get(0);
        System.out.println("education1.getSchool() = " + education1.getSchool());

        // ilk education bilgisinin school bilgisini  DIREKT alalım
        System.out.println("kraftUser1[0].getEducation().get(0).getSchool() = " + kraftUser1[0].getEducation().get(0).getSchool());

        //ikinci experience bilgisinin company bilgisini alalım
        List<Experience> experience = kraftUser1[0].getExperience();
        Experience experience1 = experience.get(1);
        System.out.println("experience1.getCompany() = " + experience1.getCompany());

        // ikinci experince bilgisinin company bilgisini  DIREKT alalım
        System.out.println("kraftUser1[0].getExperience().get(1).getCompany() = " + kraftUser1[0].getExperience().get(1).getCompany());
    }
}
