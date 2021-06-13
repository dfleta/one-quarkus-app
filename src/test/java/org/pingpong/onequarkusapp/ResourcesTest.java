package org.pingpong.onequarkusapp;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

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

    /**
     * La peticion 
     *      /usuaria/<nombre>
     * ha de retornar el nombre y la destreza de la persona 
	 * indicada de la base de datos.
     */
    @Test
    public void test_get_persona() {

        // Si exite la usuaria la respuesta es 200
        given()
            .pathParam("nombre", "Doobey")
        .when()
            .get("/usuaria/{nombre}")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("nombre", equalTo("Doobey"),
                  "destreza", equalTo(15));

        // Si no existe la usuaria la respuesta es 404
        given()
            .pathParam("nombre", "Severus")
        .when()
            .get("/usuaria/{nombre}")
        .then()
            .statusCode(404);
	}

    /**
     * Ordena un pedido empleando el método POST en la url
     *      /ordena
     * Los parametros necesarios son "usuaria" con el nombre de la persona
	 * e "item" con el nombre del objeto.
     * La peticion ha de retornar la orden de pedido y 201
     * si ha sido generada y 404 en caso contrario.
     */
	@Test
    public void test_post_ok() {

		given()
            .body("{\"user\": {\"nombre\": \"Hermione\"}, \"item\": {\"nombre\": \"AgedBrie\"}}")
            .header("Content-Type", MediaType.APPLICATION_JSON)
		.when()
            .post("/ordena")
        .then()
            .statusCode(201)
            .contentType(ContentType.JSON)
            .body("user.nombre", equalTo("Hermione"),
                  "item.nombre", equalTo("AgedBrie"));
	}

    // Si la usuaria o el item no existen el controlador devuelve 404
    @Test
    public void test_post_ko() {
        given()
            .body("{\"user\": {\"nombre\": \"Severus\"}, \"item\": {\"nombre\": \"AgedBrie\"}}")
            .header("Content-Type", MediaType.APPLICATION_JSON)
		.when()
            .post("/ordena")
        .then()
            .statusCode(404);
    }
    
}
