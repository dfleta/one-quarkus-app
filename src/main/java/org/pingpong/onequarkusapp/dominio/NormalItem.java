package org.pingpong.onequarkusapp.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_items")
public class NormalItem {
	
	@Id
	@Column(name="item_nom")
	private String nombre;
	
	@Column(name="item_prop")
	private int quality;

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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + quality;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof NormalItem))
			return false;
		
        NormalItem other = (NormalItem) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
        } else if (!nombre.equals(other.nombre)) {
                return false;
        }
		return true;
	}	
}
