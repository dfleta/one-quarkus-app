package org.pingpong.onequarkusapp;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

import javax.inject.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pingpong.onequarkusapp.dominio.Item;
import org.pingpong.onequarkusapp.dominio.Orden;
import org.pingpong.onequarkusapp.dominio.Usuaria;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ServiceTest {

    @PersistenceContext
    EntityManager em;

    @Inject
    ServiceOlli servicio;

    /**
	 * MAPPINGS de la entidades a las tablas de la BBDD.
	 * 
	 * Observa el esquema de la base de datos que espera 
	 * la aplicacion en el fichero:
	 * src/main/resources/schema.sql
	 */
	
	/**
	 * Completa la definicion y el mapping
	 * de la clase NormalItem a la tabla t_items
	 */
    @Test
    public void test_mapping_normalItem() {
        Item elixir = em.find(Item.class, "Elixir of the Mongoose");
        Assertions.assertThat(elixir).isNotNull();
        Assertions.assertThat(elixir.getNombre()).isEqualTo("Elixir of the Mongoose");
		Assertions.assertThat(elixir.getQuality()).isEqualTo(7);
        Assertions.assertThat(elixir.getTipo()).isEqualTo("NormalItem");
    }

	/**
	 * Completa la definicion y el mapping
	 * de la clase Usuaria a la tabla t_users
	 */
	@Test
	public void test_mapping_usuaria() {
		Usuaria elfo = em.find(Usuaria.class, "Doobey");
        Assertions.assertThat(elfo).isNotNull();
        Assertions.assertThat(elfo.getNombre()).isEqualTo("Doobey");
        Assertions.assertThat(elfo.getDestreza()).isEqualTo(15);
	}

    /**
	 * Completa la definicion y el mapping
	 * de la clase Orden a la tabla t_ordenes
	 * El id de esta clase ha de seguir una estrategia Identity
	 */
	@Test 
	public void test_mapping_orden() {
		Orden pedido = em.find(Orden.class, 1L);
        Assertions.assertThat(pedido).isNotNull();
        Assertions.assertThat(pedido.getUser().getNombre()).isEqualTo("Doobey");
		Assertions.assertThat(pedido.getItem().getNombre()).isEqualToIgnoringCase("Elixir of the Mongoose");
	}

	/** SERVICIO */

    /**
	 * Crea una clase llamada ServiceItem e indica
	 * que es una dependencia Quarkus 
	 */

    @Test
	public void test_inyeccion_servicio() {
		Assertions.assertThat(servicio).isNotNull();
	}

	/**
	 * Implementa el metodo cargaUsuaria del servicio.
	 * Devuelve la usuaria con el nombre indicado, si existe.
	 * Si no existe, devuelve un objeto usuaria con sus propiedades
     * y valores como se indica en los casos test.
	 */
	@Test
	public void test_carga_usuaria() {
		Assertions.assertThat(servicio).isNotNull();
		Usuaria elfo = servicio.cargaUsuaria("Doobey");
		Assertions.assertThat(elfo).isNotNull();
		Assertions.assertThat(elfo.getNombre()).isEqualTo("Doobey");
        Assertions.assertThat(elfo.getDestreza()).isEqualTo(15);
	}

    @Test
	public void test_carga_usuaria_no_existe() {
		Assertions.assertThat(servicio).isNotNull();
		Usuaria profesor = servicio.cargaUsuaria("Severus");
		Assertions.assertThat(profesor).isNotNull();
		Assertions.assertThat(profesor.getNombre()).isEmpty();
        Assertions.assertThat(profesor.getDestreza()).isZero();
	}

    /**
	 * Implementa el metodo cargaItem del servicio.
	 * Devuelve el item con el nombre indicado, si existe.
     * Si no existe, devuelve un objeto usuaria con sus propiedades
     * y valores como se indica en los casos test.
	 */

    @Test
	public void test_carga_item() {
        Assertions.assertThat(servicio).isNotNull();
		Item item = servicio.cargaItem("Elixir of the Mongoose");
		Assertions.assertThat(item).isNotNull();
		Assertions.assertThat(item.getNombre()).isEqualTo("Elixir of the Mongoose");
		Assertions.assertThat(item.getQuality()).isEqualTo(7);
	}
    
    @Test
	public void test_carga_item_no_existe() {
        Assertions.assertThat(servicio).isNotNull();
		Item item = servicio.cargaItem("Reliquias de la muerte");
		Assertions.assertThat(item).isNotNull();
		Assertions.assertThat(item.getNombre()).isEmpty();
		Assertions.assertThat(item.getQuality()).isZero();
	}

	/**
	 * Implementa el metodo cargaOrden del servicio.
	 * Devuelve una lista con los pedidos de la usuaria 
	 * con el nombre indicado, si existe.
     * Si no existe, devuelve una lista vacía.
	 */

    @Test
	public void test_carga_orden() {
        Assertions.assertThat(servicio).isNotNull();
		List<Orden> ordenes = servicio.cargaOrden("Hermione");
		Assertions.assertThat(ordenes).isNotNull();
		Assertions.assertThat(ordenes).hasSize(1);
		Assertions.assertThat(ordenes.get(0).getUser().getNombre()).isEqualToIgnoringCase("Hermione");
		Assertions.assertThat(ordenes.get(0).getItem().getNombre()).isEqualTo("+5 Dexterity Vest");
		// Assertions.assertThat(ordenes).allMatch(orden -> orden.getUser().getNombre().equalsIgnoreCase("Hermione"));
		// Assertions.assertThat(ordenes).allMatch(orden -> orden.getItem().getNombre().equalsIgnoreCase("+5 Dexterity Vest"));
	}
    
    @Test
	public void test_carga_orden_no_existe() {
        Assertions.assertThat(servicio).isNotNull();
		List<Orden> ordenes = servicio.cargaOrden("Severus");
		Assertions.assertThat(ordenes).isNotNull();
		Assertions.assertThat(ordenes).isEmpty();
	}

    /**
     * Implementa el metodo "comanda" del servicio
	 * que permite a una usuaria pedir un item.
     * La usuaria y el item ya existen en la bbdd (NO has de crearlos).
	 * 
	 * Guarda esta orden en su tabla en la base de datos.
	 * 
     * El metodo devuelve la orden de tipo Orden creada.
	 */
	@Test
	@Transactional
	public void test_comanda_ok() {
        Assertions.assertThat(servicio).isNotNull();
		Orden orden = servicio.comanda("Hermione", "AgedBrie");
		Assertions.assertThat(orden).isNotNull();
		Assertions.assertThat(orden.getId()).isNotZero();
		Assertions.assertThat(orden.getUser().getNombre()).isEqualTo("Hermione");
		Assertions.assertThat(orden.getItem().getNombre()).isEqualTo("AgedBrie");

		TypedQuery<Orden> query = em.createQuery("select orden from Orden orden join orden.user user where user.nombre = 'Hermione'", Orden.class);
		List<Orden> pedidos = query.getResultList();
		
        Assertions.assertThat(pedidos).isNotNull();
		Assertions.assertThat(pedidos).hasSize(2);
        Assertions.assertThat(pedidos.get(1).getUser().getNombre()).isEqualTo("Hermione");
		Assertions.assertThat(pedidos.get(1).getItem().getNombre()).isEqualToIgnoringCase("AgedBrie");
		em.find(Orden.class, pedidos.get(1).getId()).delete();
	}

	/**
     * Implementa el metodo comanda del servicio
	 * para que NO permita generar pedidos de productos
	 * si no existe la usuaria en la base de datos.
	 */
	@Test
	public void test_comanda_no_user() {
		Assertions.assertThat(servicio).isNotNull();
		Orden orden = servicio.comanda("Severus", "+5 Dexterity Vest");
		Assertions.assertThat(orden).isNull();
		Usuaria profesor = servicio.cargaUsuaria("Severus");
		Assertions.assertThat(profesor).isNotNull();
		Assertions.assertThat(profesor.getNombre()).isEmpty();
        Assertions.assertThat(profesor.getDestreza()).isZero();

		Orden pedido = em.find(Orden.class, 3L);
        Assertions.assertThat(pedido).isNull();
	}
    
	/**
     * Implementa el metodo comanda del servicio
	 * para que NO permita generar pedidos de productos
	 * si no existe el item en la base de datos.
	 */
	@Test
	public void test_comanda_no_item() {
		Assertions.assertThat(servicio).isNotNull();
		Orden orden = servicio.comanda("Hermione", "Reliquias de la muerte");
		Assertions.assertThat(orden).isNull();
		Item item = (Item) servicio.cargaItem("Reliquias de la muerte");
		Assertions.assertThat(item).isNotNull();
		Assertions.assertThat(item.getNombre()).isEmpty();
		Assertions.assertThat(item.getQuality()).isZero();

		Orden pedido = em.find(Orden.class, 3L);
        Assertions.assertThat(pedido).isNull();
	}

	/**
	 * Modifica el metodo comanda para que 
	 * NO permita generar pedidos de productos
	 * cuando la destreza de la usuaria sea menor
	 * que la calidad del Item.
	 */
	@Test
	public void test_comanda_item_sin_pro() {
		Assertions.assertThat(servicio).isNotNull();
		Orden orden = servicio.comanda("Doobey", "+5 Dexterity Vest");
		Assertions.assertThat(orden).isNull();

		Orden pedido = em.find(Orden.class, 3L);
        Assertions.assertThat(pedido).isNull();
	}

	/**
	 * Implementa el metodo comandaMultiple para que una usuaria
	 * pueda ordenar más de un Item a la vez.
	 * Guarda las ordenes en la base de datos.
	 * 
	 * El metodo devuelve las ordenes creadas.
	 * 
	 * No se crean ordenes si la usuaria no existe previamente
	 * en la base de datos.
	 * 
	 * No se ordenan items que no existan en la base de datos.
	 */

	@Test
	@Transactional
	public void test_ordenar_multiples_items_ok() {
		Assertions.assertThat(servicio).isNotNull();
		List<Orden> ordenes = servicio.comandaMultiple("Hermione", Arrays.asList("AgedBrie", "Elixir of the Mongoose"));
		Assertions.assertThat(ordenes).isNotEmpty();
		Assertions.assertThat(ordenes).size().isEqualTo(2);

		TypedQuery<Orden> query = em.createQuery("select orden from Orden orden join orden.user user where user.nombre = 'Hermione'", Orden.class);
		List<Orden> pedidos = query.getResultList();
		
        Assertions.assertThat(pedidos).isNotNull();
		Assertions.assertThat(pedidos).hasSize(3);
        Assertions.assertThat(pedidos.get(1).getUser().getNombre()).isEqualTo("Hermione");
		Assertions.assertThat(pedidos.get(1).getItem().getNombre()).isEqualToIgnoringCase("AgedBrie");
		Assertions.assertThat(pedidos.get(2).getItem().getNombre()).isEqualToIgnoringCase("Elixir of the Mongoose");
		em.find(Orden.class, pedidos.get(2).getId()).delete();
		em.find(Orden.class, pedidos.get(1).getId()).delete();
	}

	// No se permiten ordenes si el usuario no existe en la base de datos
	@Test
	@Transactional
	public void test_ordenar_multiples_items_no_user() {
		Assertions.assertThat(servicio).isNotNull();
		List<Orden> ordenes = servicio.comandaMultiple("Severus", Arrays.asList("+5 Dexterity Vest", "Elixir of the Mongoose"));
		Assertions.assertThat(ordenes).isEmpty();
	}

	// No se ordenan items que no existan en la base de datos
	@Test
	@Transactional
	public void test_ordenar_multiples_items_no_item() {
		Assertions.assertThat(servicio).isNotNull();
		List<Orden> ordenes = servicio.comandaMultiple("Hermione", Arrays.asList("Guardapelo Salazar", "Reliquias de la Muerte"));
		Assertions.assertThat(ordenes).isEmpty();
	}
	}
