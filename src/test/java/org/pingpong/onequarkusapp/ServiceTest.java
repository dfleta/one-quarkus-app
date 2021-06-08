package org.pingpong.onequarkusapp;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pingpong.onequarkusapp.dominio.NormalItem;
import org.pingpong.onequarkusapp.dominio.Usuaria;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ServiceTest {

    @PersistenceContext
    EntityManager em;

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
    
}
