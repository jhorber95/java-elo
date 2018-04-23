/**
 * 
 */
package com.software.estudialo.entities;

import java.util.List;

/**
 * @author LUIS
 *
 */
public class Fase {

	private int id;
	private String nombre;
	private int numero;
	private List<FaseItem> items;

	public Fase() {
		super();
	}

	public Fase(int id, String nombre, int numero, List<FaseItem> items) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.numero = numero;
		this.items = items;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public List<FaseItem> getItems() {
		return items;
	}

	public void setItems(List<FaseItem> items) {
		this.items = items;
	}

}
