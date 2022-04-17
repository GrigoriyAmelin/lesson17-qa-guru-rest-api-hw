package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class SelenoidTests {

    // make request to https://selenoid.autotests.cloud/status
    // total is 20

    @Test
    void checkTotal() {

        given()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .body("total", is(20));

    }
}
