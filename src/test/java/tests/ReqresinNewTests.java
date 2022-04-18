package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReqresinNewTests {

    @Test
    @DisplayName("Создание нового пользователя с внутренними проверками")
    void createNewUser() {
        /*
        request: https://reqres.in/api/users

        data: {"name": "gregorrr", "job": "QA"}

        response:
        {
        "name": "gregorrr",
        "job": "QA",
        "id": "912",
        "createdAt": "2022-04-18T15:00:24.175Z"
        }
         */

        String registrationData = "{ \"name\": \"gregorrr\", \"job\": \"QA\" }";

        given()
                .body(registrationData)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .body("name", is("gregorrr"))
                .body("job", is("QA"))
                .body("", hasKey("id"))
                .body("", hasKey("createdAt"));
    }

    @Test
    @DisplayName("Создание нового пользователя с внешними проверками")
    void createNewUserWithOutsideCheck() {

        String registrationData = "{ \"name\": \"gregorrr\", \"job\": \"QA\" }";

        Response response = given()
                .body(registrationData)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .extract().response();

        assertEquals(response.getStatusCode(), 201);
        assertEquals(response.path("name"), "gregorrr");
        assertEquals(response.path("job"), "QA");
        assertTrue(response.getBody().asString().contains("id"));
        assertTrue(response.getBody().asString().contains("createdAt"));

        System.out.println("\n" + "Response: " + response.asString());
        System.out.println("Response code: " + response.getStatusCode());
        System.out.println("New user id: " + response.path("id") + "\n");
    }


    @Test
    @DisplayName("Обновление данных пользователя с внешними проверками")
    void updateNewUserWithOutsideCheck() {

        String registrationData = "{ \"name\": \"gregorrr\", \"job\": \"PHD\" }";

        Response response = given()
                .body(registrationData)
                .contentType(JSON)
                .when()
                .put("https://reqres.in/api/users/60")
                .then()
                .extract().response();

        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.path("name"), "gregorrr");
        assertEquals(response.path("job"), "PHD");
        assertTrue(response.getBody().asString().contains("updatedAt"));

        System.out.println("\n" + "Response: " + response.asString());
        System.out.println("Response code: " + response.getStatusCode());
        System.out.println("New user job: " + response.path("job") + "\n");
    }

    @ValueSource(strings = {"https://reqres.in/api/users?page=1", "https://reqres.in/api/users?page=2"})
    @ParameterizedTest(name = "Получение списка пользователей на странице \"{0}\"")
    @DisplayName("Получение списка пользователей")
    void getListOfUsersWithOutsideCheck(int testData) {

        Response response = given()
                .when()
                .get(String.valueOf(testData))
                .then()
                .extract().response();

        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.path("page"), String.valueOf(testData));


//        System.out.println("\n" + "Response: " + response.asString());
//        System.out.println("Response code: " + response.getStatusCode());
//        System.out.println("New user job: " + response.path("job") + "\n");
    }

    @Test
    @DisplayName("Получение списка пользователей")
    void getListOfUsers() {

        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("page", is(2))
                .body("data.id[0]", is(7));
    }

//    @Test
////    @DisplayName("Получение количества зарегистрированных пользователей")
//    void getListOfUsers() {
//        /*
//        request: https://reqres.in/api/register
//
//        data:
//        {
//        "email": "gregorrr@rmail.ru",
//        "password": "qwerty123"
//        }
//
//        response:
//        {
//        "id": 4,
//        "token": "QpwL5tke4Pnpja7X4"
//        }
//         */
//
//        String authorizedData = "{\"email\": \"eve.holt@reqres.in\", " +
//                "\"password\": \"cityslicka\"}";
//
//        given()
//                .body(authorizedData)
//                .contentType(JSON)
//                .when()
//                .post("https://reqres.in/api/login")
//                .then()
//                .statusCode(200)
//                .body("token", is("QpwL5tke4Pnpja7X4"));
//    }

//    @Test
//    void successfulRegister() {
//        /*
//        request: https://reqres.in/api/register
//
//        data:
//        {
//        "email": "gregorrr@rmail.ru",
//        "password": "qwerty123"
//        }
//
//        response:
//        {
//        "id": 4,
//        "token": "QpwL5tke4Pnpja7X4"
//        }
//         */
//
//        String authorizedData = "{\"email\": \"eve.holt@reqres.in\", " +
//                "\"password\": \"cityslicka\"}";
//
//        given()
//                .body(authorizedData)
//                .contentType(JSON)
//                .when()
//                .post("https://reqres.in/api/login")
//                .then()
//                .statusCode(200)
//                .body("token", is("QpwL5tke4Pnpja7X4"));
//    }

}
