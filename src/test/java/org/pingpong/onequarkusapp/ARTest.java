package org.pingpong.onequarkusapp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pingpong.onequarkusapp.dominio.NormalItemAR;
import org.pingpong.onequarkusapp.dominio.OrdenAR;
import org.pingpong.onequarkusapp.dominio.UsuariaAR;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ARTest {

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
        NormalItemAR elixir = NormalItemAR.findById("Elixir of the Mongoose");
        Assertions.assertThat(elixir).isNotNull();
        Assertions.assertThat(elixir.getNombre()).isEqualTo("Elixir of the Mongoose");
		Assertions.assertThat(elixir.getQuality()).isEqualTo(7);
        Assertions.assertThat(elixir.getTipo()).isEqualTo("NormalItem");
    }

	// Completa la definicion y el mapping
	// de la clase Usuaria a la tabla t_users
	@Test
	public void test_mapping_user() {
		UsuariaAR elfo = UsuariaAR.findById("Doobey");
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
		OrdenAR pedido = OrdenAR.findById(1L);
        Assertions.assertThat(pedido).isNotNull();
        Assertions.assertThat(pedido.getUser().getNombre()).isEqualTo("Doobey");
		Assertions.assertThat(pedido.getItem().getNombre()).isEqualTo("Elixir of the Mongoose");
		}
    
}
