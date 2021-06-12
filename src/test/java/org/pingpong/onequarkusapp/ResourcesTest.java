package org.pingpong.onequarkusapp;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class ResourcesTest {

    @Inject
    Resources resources;
    /**
	 * Implementa una clase Resources que
	 * añada una API REST a nuestra app.
	 * Injecta el servicio en Resources.
     */

    @Test
    public void test_injeccion() {
        Assertions.assertThat(resources.service).isNotNull();
    }

    /**
     * En el endpoint /wellcome
     * recibimos un TEXT con el mensaje
     * "Wellcome Ollivanders!""
     */

    @Test
    public void test_wellcome() {
        given()
            .contentType(ContentType.TEXT)
        .when()
            .get("/wellcome")
        .then()
            .statusCode(200)
            .body(is("Wellcome Ollivanders!"));
    }


    
}
