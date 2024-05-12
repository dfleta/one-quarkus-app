package org.pingpong.onequarkusapp.dominio;

import java.util.List;
import java.util.stream.Collectors;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="t_ordenes")
public class Orden extends PanacheEntityBase {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ord_id")
    private Long id; 
    
    @OneToOne
	@JoinColumn(name="ord_user")
	private Usuaria user;
	
	@OneToOne
	@JoinColumn(name="ord_item")
	private Item item;

	public Orden() {}

	public Orden(Usuaria user, Item item) {
		this.user = user;
		this.item = item;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Usuaria getUser() {
		return this.user;
	}
	public void setUser(Usuaria user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return this.getUser().getNombre() + " " + this.getItem().getNombre();
	}

	// contenido min: loop, if-else, colecciones.
	public static List<Orden> findByUserName(String name) {
		List<Orden> ordenes = Orden.listAll();
		List<Orden> ordenesByName = ordenes
										.stream()
										.filter(o -> o.getUser().getNombre().equalsIgnoreCase(name))
										.collect(Collectors.toList());
		return ordenesByName.isEmpty()? List.of(): ordenesByName;
	}
}
