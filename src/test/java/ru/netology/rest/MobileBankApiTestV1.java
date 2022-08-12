package ru.netology.rest;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

class MobileBankApiTestV1 {
    @Test
    void shouldReturnDemoAccounts() {
      // Given - When - Then
      // Предусловия
      given()
          .baseUri("http://localhost:9999/api/v1/")
      // Выполняемые действия
      .when()
          .get("demo/accounts")
      // Проверки
      .then()
          .statusCode(401)
          .body(matchesJsonSchemaInClasspath("accounts.schema.json"));
    }

    @Test
    void shouldReturnBodySize() {
        given()
                .baseUri("http://localhost:9999/api/v1/")
                .when()
                .get("demo/accounts")
                .then()
                .body("", hasSize(3));
    }
    @Test
    void shouldReturnCurrencyRUB() {
        given()
                .baseUri("http://localhost:9999/api/v1/")
                .when()
                .get("demo/accounts")
                .then()
                .body("[0].currency",equalTo("RUR"));
    }
    @Test
    void shouldReturnCurrencyUSD() {
        given()
                .baseUri("http://localhost:9999/api/v1/")
                .when()
                .get("demo/accounts")
                .then()
                .body("[1].currency",equalTo("USD"));
    }
}
