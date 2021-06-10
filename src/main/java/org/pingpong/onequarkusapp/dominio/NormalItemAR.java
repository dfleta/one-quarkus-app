package org.pingpong.onequarkusapp.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name="t_items")
public class NormalItemAR extends PanacheEntityBase {

    @Id
	@Column(name="item_nom")
	private String nombre = "";
	
	@Column(name="item_prop")
	private int quality = 0;;

	@Column(name = "item_tipo", insertable = false, updatable = false)
	private String tipo;
	
	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getQuality() {
		return this.quality;
	}
	public void setPropiedad(int edat) {
		this.quality = edat;
	}

	public String getTipo() {
		return this.tipo;
	}    
}
