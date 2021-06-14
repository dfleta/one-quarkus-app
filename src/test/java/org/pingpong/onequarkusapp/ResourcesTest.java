package org.pingpong.onequarkusapp;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.core.MediaType;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pingpong.onequarkusapp.dominio.Orden;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;

@QuarkusTest
public class ResourcesTest {

    @Inject
    EntityManager em;

    @Inject
    ResourcesOlli resources;
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

        // Si la usuaria existe la respuesta es 200
        given()
            .pathParam("nombre", "Doobey")
        .when()
            .get("/usuaria/{nombre}")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("nombre", equalTo("Doobey"),
                  "destreza", equalTo(15));

        // Si la usuaria NO existe la respuesta es 404
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
	 * e "item" con el nombre del objeto, en un JSON.
     * La peticion ha de retornar la orden de pedido JSON
     * y status code 201 si ha sido generada y 404 en caso contrario.
     */
	@Test
    @Transactional
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
        
        // rollback BBDD
        TypedQuery<Orden> query = em.createQuery("select orden from Orden orden join orden.user user where user.nombre = 'Hermione'", Orden.class);
		List<Orden> pedidos = query.getResultList();
        Assertions.assertThat(pedidos).isNotNull();
		Assertions.assertThat(pedidos).hasSize(2);
        Assertions.assertThat(pedidos.get(1).getUser().getNombre()).isEqualTo("Hermione");
		Assertions.assertThat(pedidos.get(1).getItem().getNombre()).isEqualToIgnoringCase("AgedBrie");
		em.find(Orden.class, pedidos.get(1).getId()).delete();
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

        given()
            .body("{\"user\": {\"nombre\": \"Doobey\"}, \"item\": {\"nombre\": \"Varita de Sauco\"}}")
            .header("Content-Type", MediaType.APPLICATION_JSON)
		.when()
            .post("/ordena")
        .then()
            .statusCode(404);
    }

    /**
     * Obten los pedidos de una usuaria mediante
     * una peticion GET en el endpoint:
     *      /pedidos/{usuaria}
     */

    @Test
    public void test_pedidos_usuaria() {             

        List<Map<String, Object>> pedidos = 
            given()
                .contentType(ContentType.JSON)
            .when()
                .get("/pedidos/{usuaria}", "Hermione")
                .as(new TypeRef<List<Map<String, Object>>>() {});
        
        Assertions.assertThat(pedidos).hasSize(1);
        Assertions.assertThat(pedidos.get(0).get("user")).hasFieldOrPropertyWithValue("nombre", "Hermione");
        Assertions.assertThat(pedidos.get(0).get("item")).hasFieldOrPropertyWithValue("nombre", "+5 Dexterity Vest");
    }

     /**
     * La peticion 
     *      /item/<nombre>
     * ha de retornar el nombre y la calidad
     * del Item indicado de la base de datos.
     */
    @Test
    public void test_get_item() {

        // Si el item existe la respuesta es 200
        given()
            .pathParam("nombre", "AgedBrie")
        .when()
            .get("/item/{nombre}")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("nombre", equalTo("AgedBrie"),
                  "quality", equalTo(10));

        // Si el item no existe la respuesta es 404
        given()
            .pathParam("nombre", "Varita de Sauco")
        .when()
            .get("/item/{nombre}")
        .then()
            .statusCode(404);
	}
}
