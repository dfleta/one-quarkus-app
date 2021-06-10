package org.pingpong.onequarkusapp.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name="t_users")
public class UsuariaAR extends PanacheEntityBase {

    @Id
	@Column(name="user_nom")
	private String nombre = "";
	
	@Column(name="user_prop")
	private int destreza = 0;
	
	public UsuariaAR() {}

	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getDestreza() {
		return this.destreza;
	}
	public void setDestreza(int valor) {
		this.destreza = valor;
	}    
}
