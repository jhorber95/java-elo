/**
 * 
 */
package com.software.estudialo.entities;


// TODO: Auto-generated Javadoc
/**
 * The Class Pais.
 *
 * @author LUIS
 */

public class Pais {
	
	/** The id. */
	private int id;
	
	/** The nombre. */
	private String nombre;
	
	/**
	 * Instantiates a new pais.
	 */
	public Pais() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Instantiates a new pais.
	 *
	 * @param id the id
	 * @param nombre the nombre
	 */
	public Pais(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getid() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setid(int id) {
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
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	
}
