package org.pingpong.onequarkusapp.dominio;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name="t_ordenes")
public class OrdenAR extends PanacheEntityBase {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ord_id")
    private Long id; 
    
    @OneToOne
	@JoinColumn(name="ord_user")
	private UsuariaAR user;
	
	@OneToOne
	@JoinColumn(name="ord_item")
	private NormalItemAR item;

	public OrdenAR() {}

	public OrdenAR(UsuariaAR user, NormalItemAR item) {
		this.user = user;
		this.item = item;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public NormalItemAR getItem() {
		return item;
	}
	public void setItem(NormalItemAR item) {
		this.item = item;
	}
	public UsuariaAR getUser() {
		return this.user;
	}
	public void setUser(UsuariaAR user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return this.getUser().getNombre() + " " + this.getItem().getNombre();
	}

	public static List<OrdenAR> findByUserName(String name) {
		List<OrdenAR> ordenes = OrdenAR.listAll();
		List<OrdenAR> ordenesByName = ordenes.stream().filter(o -> o.getUser().getNombre().equalsIgnoreCase(name)).collect(Collectors.toList());
		return ordenesByName.isEmpty()? List.of(): ordenesByName;
	}
}
