package org.pingpong.onequarkusapp.dominio;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="t_items")
public class Item extends PanacheEntityBase {

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
