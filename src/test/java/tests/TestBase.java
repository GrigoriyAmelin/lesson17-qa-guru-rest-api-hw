package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    public static final String name = "gregorrr";
    public static final String firstJob = "QA";
    public static final String secondJob = "PHD";

    @BeforeAll
    static void precondition() {
        RestAssured.baseURI = "https://reqres.in/api";
    }
}
