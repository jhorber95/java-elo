/**
 * 
 */
package com.software.estudialo.entities;

/**
 * @author LUIS
 *
 */
public class Interes {

	/** The id. */
	private int id;

	/** The nombre. */
	private String nombre;

	/** The descripcion. */
	private String descripcion;

	public Interes() {
		super();
	}

	public Interes(int id, String nombre, String descripcion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
