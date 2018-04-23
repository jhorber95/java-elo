/**
 * 
 */
package com.software.estudialo.entities;

// TODO: Auto-generated Javadoc
/**
 * The Class Rol.
 *
 * @author LUIS
 */
public class Rol {

	/** The id. */
	private int id;
	
	/** The nombre. */
	private String nombre;

	/**
	 * Instantiates a new rol.
	 */
	public Rol() {
		super();
	}

	/**
	 * Instantiates a new rol.
	 *
	 * @param id the id
	 * @param nombre the nombre
	 */
	public Rol(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the nombre.
	 *
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Sets the nombre.
	 *
	 * @param nombre the new nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
