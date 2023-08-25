package apiTests.day05;

import apiPOJOTemplates.User;
import com.google.gson.Gson;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class Test_01_Gson {

    //de-serialization  -->json to java  --> as() metodu ile

    //serialization  -->java to json

    //****************************************
    @Test
    public void jsonToJava_deSerialization() {
    /**
     {
     "id": 1549066,
     "name": "Leela Kakkar",
     "email": "kakkar_leela@anderson.test",
     "gender": "female",
     "status": "inactive"
     }
     */

        Gson gson=new Gson();

        String userJsonBody="{\n" +
                "     \"id\": 1549066,\n" +
                "     \"name\": \"Leela Kakkar\",\n" +
                "     \"email\": \"kakkar_leela@anderson.test\",\n" +
                "     \"gender\": \"female\",\n" +
                "     \"status\": \"inactive\"\n" +
                "     }";

        //de-serialization  json-->java  map ile
        Map <String,Object> mapBody = gson.fromJson(userJsonBody, Map.class);
        System.out.println("mapBody.get(\"id\") = " + mapBody.get("id"));
        System.out.println("mapBody.get(\"name\") = " + mapBody.get("name"));
        System.out.println("mapBody.get(\"email\") = " + mapBody.get("email"));
        System.out.println("mapBody = " + mapBody);

        //de-serialization  json-->java  custom class ile (User)

        User user = gson.fromJson(userJsonBody, User.class);
        System.out.println("user.getId() = " + user.getId());
        System.out.println("user.getName() = " + user.getName());
        System.out.println("user.getEmail() = " + user.getEmail());



    }

    //****************************************
    @Test
    public void javaToJson_SerializationWithMap() {
        //serilization --> java to json

        //bir tane Java objesi oluşturup, onu uygun yapıdaki bir json'a dönüştürelim...
        /**
         * id:61
         * name:Hasan
         * email: aaa@aa.com
         */

        //gson objesi oluşturalım......dönüştürme işlemi için
        Gson gson=new Gson();

        Map<String,Object> map=new HashMap<>();

        map.put("id",61);
        map.put("name","Hasan");
        map.put("email","aaa@aa.com");

        System.out.println("map = " + map);

        String json = gson.toJson(map);
        System.out.println("json = " + json);


    }

    //****************************************
    @Test
    public void javaToJson_Serialization() {
        //serilization --> java to json

        //bir tane Java objesi oluşturup, onu uygun yapıdaki bir json'a dönüştürelim...
        /**
         * id:61
         * name:Hasan Yaka
         * email: hYaka@gmail.com
         * gender:male
         * status:inactive
         */

        //gson objesi oluşturalım......dönüştürme işlemi için
        Gson gson=new Gson();

        User user=new User();

        user.setId(61);
        user.setName("Hasan Yaka");
        user.setEmail("hYaka@gmail.com");
        user.setGender("male");
        user.setStatus("inactive");

        System.out.println("user = " + user);

        String jsonUser = gson.toJson(user);
        System.out.println("jsonUser = " + jsonUser);


    }

    //****************************************
    @Test
    public void javaToJson_Serialization_2() {
        //serilization --> java to json

        //bir tane Java objesi oluşturup, onu uygun yapıdaki bir json'a dönüştürelim...
        /**
         * id:61
         * name:Hasan Yakalık
         * email: hYakalık@gmail.com
         * gender:male
         * status:inactive
         */
        //gson objesi oluşturalım......dönüştürme işlemi için
        Gson gson=new Gson();

        User user=new User(61,"Hasan Yakalık","hYakalık@gmail.com","male","inactive");

        System.out.println("user = " + user);

        String jsonUser = gson.toJson(user);
        System.out.println("jsonUser = " + jsonUser);

    }
}
