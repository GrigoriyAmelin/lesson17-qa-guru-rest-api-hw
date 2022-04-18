package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReqresinTests {

    @Test
    @DisplayName("Создание нового пользователя (внутренние проверки)")
    void createNewUserCheck() {

//        request: https://reqres.in/api/users

//        data: {"name": "gregorrr", "job": "QA"}

//        response: {"name": "gregorrr", "job": "QA", "id": "912", "createdAt": "2022-04-18T15:00:24.175Z"}

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
    @DisplayName("Создание нового пользователя (внешние проверки)")
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
    @DisplayName("Обновление данных пользователя (внешние проверки)")
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

    static Stream<Arguments> getListOfUsersWithOutsideCheck() {
        return Stream.of(
                Arguments.of(1, 1, "george.bluth@reqres.in"),
                Arguments.of(2, 7, "michael.lawson@reqres.in")
        );
    }

    @MethodSource(value = "getListOfUsersWithOutsideCheck")
    @ParameterizedTest(name = "Получение списка пользователей на странице \"{0}\"")
    @DisplayName("Получение списка пользователей (внешние проверки)")
    void getListOfUsersWithOutsideCheck(int pageNumber, int idNumber, String userEmail) {

        Response response = given()
                .when()
                .get("https://reqres.in/api/users?page=" + pageNumber)
                .then()
                .extract().response();

        assertEquals(response.getStatusCode(), 200);
        assertEquals((Integer) response.path("page"), pageNumber);
        assertEquals((Integer) response.path("data.id[0]"), idNumber);
        assertEquals(response.path("data.email[0]"), userEmail);

        System.out.println("\n" + "Response: " + response.asString());
        System.out.println("Response code: " + response.getStatusCode());
        System.out.println("User email: " + response.path("data.email[0]") + "\n");
    }

    @Test
    @DisplayName("Получение списка пользователей (внутренние проверки)")
    void getListOfUsersCheck() {

        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("page", is(2))
                .body("data.id[0]", is(7))
                .body("data.email[1]", is("lindsay.ferguson@reqres.in"));
    }

    @Test
    @DisplayName("Успешный вход (внутренние проверки)")
    void loginSuccessfulCheck() {

        String userCredentials = "{ \"email\": \"tobias.funke@reqres.in\", \"password\": \"1234\" }";

        given()
                .when()
                .body(userCredentials)
                .contentType(JSON)
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X9"));
    }
}
