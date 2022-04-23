package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class DemowebshopTests {
    @Test
    void addToCardTest() {
        String response =
                given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("Nop.customer=4a270881-47e7-42c9-a179-438c242fa564; " +
                        "ARRAffinity=1818b4c81d905377ced20e7ae987703a674897394db6e97dc1316168f754a687")
                .body("product_attribute_72_5_18=53" +
                        "&product_attribute_72_6_19=54" +
                        "&product_attribute_72_3_20=57" +
                        "&addtocart_72.EnteredQuantity=")
                .when()
                .post("http://demowebshop.tricentis.com/addproducttocart/details/72/1")
                .then()
                .extract().response().asString();
        System.out.println(response);

    }
}
