package ru.netology.rest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

class MobileBankApiTest {
    private final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setBasePath("/api/v1")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @Test
    void shouldReturnDemoAccounts() {
        given()
                .spec(requestSpec)
                .when()
                .get("demo/accounts")
                .then()
                .statusCode(200)
                .header("Content-Type", "application/json; charset=UTF-8")
                .contentType(ContentType.JSON)
                .body(matchesJsonSchemaInClasspath("accounts.schema.json"));
    }

    @Test
    void shouldReturnBodySize() {
        given()
                .spec(requestSpec)
                .when()
                .get("demo/accounts")
                .then()
                .body("", hasSize(3));
    }

    @Test
    void shouldReturnCurrencyRUB() {
        given()
                .spec(requestSpec)
                .when()
                .get("demo/accounts")
                .then()
                .body("[0].currency", equalTo("RUB"))
                .body("[0].balance", greaterThanOrEqualTo(0));
    }

    @Test
    void shouldReturnCurrencyUSD() {
        given()
                .spec(requestSpec)
                .when()
                .get("demo/accounts")
                .then()
                .body("[1].currency", equalTo("USD"))
                .body("every{ it.balance >= 0 }", is(true));
    }
}
