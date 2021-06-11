package org.pingpong.onequarkusapp;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pingpong.onequarkusapp.dominio.NormalItem;
import org.pingpong.onequarkusapp.dominio.Orden;
import org.pingpong.onequarkusapp.dominio.Usuaria;
import org.pingpong.onequarkusapp.dominio.UsuariaAR;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ServiceTest {

    @PersistenceContext
    EntityManager em;

	@javax.inject.Inject
	ServiceItem servicio;

    /**
	 * Tests sobre los mappings
	 * 
	 * Observa el esquema de la base de datos que espera 
	 * la aplicacion en el fichero:
	 * src/main/resources/schema.sql
	 */
	
	// Completa la definicion y el mapping
	// de la clase NormalItem a la tabla t_items
    @Test
    public void test_mapping_normalItem() {
        NormalItem elixir = em.find(NormalItem.class, "Elixir of the Mongoose");
        Assertions.assertThat(elixir).isNotNull();
        Assertions.assertThat(elixir.getNombre()).isEqualTo("Elixir of the Mongoose");
		Assertions.assertThat(elixir.getQuality()).isEqualTo(7);
        Assertions.assertThat(elixir.getTipo()).isEqualTo("NormalItem");
    }

    // Completa la definicion y el mapping
	// de la clase Usuaria a la tabla t_users
	@Test
	public void test_mapping_user() {
		Usuaria elfo = em.find(Usuaria.class, "Doobey");
        Assertions.assertThat(elfo).isNotNull();
        Assertions.assertThat(elfo.getNombre()).isEqualTo("Doobey");
        Assertions.assertThat(elfo.getDestreza()).isEqualTo(15);
	}

    // Completa la definicion y el mapping
	// de la clase Orden a la tabla t_ordenes
	// El id de esta clase ha de seguir una estrategia
	// Identity
	@Test 
	public void test_mapping_orden() {
		Orden pedido = em.find(Orden.class, 1L);
        Assertions.assertThat(pedido).isNotNull();
        Assertions.assertThat(pedido.getUser().getNombre()).isEqualTo("Doobey");
		Assertions.assertThat(pedido.getItem().getNombre()).isEqualTo("Elixir of the Mongoose");
		}
	
	/**
	 * Crea una clase llamada ServiceItem e indica
	 * que es una dependencia Quarkus 
	 */

	/**
	 * Implementa el metodo cargaUsuaria del servicio.
	 * Devuelve la usuaria con el nombre indicado si existe.
	 * Si no existe, devuelve un objeto usuaria vacío.
	 */
	@Test
	public void test_carga_user() {
		Assertions.assertThat(servicio).isNotNull();
		UsuariaAR elfo = servicio.cargaUsuaria("Doobey");
		Assertions.assertThat(elfo).isNotNull();
		Assertions.assertThat(elfo.getNombre()).isEqualTo("Doobey");
        Assertions.assertThat(elfo.getDestreza()).isEqualTo(15);
	}
}
