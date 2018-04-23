/**
 * 
 */
package com.software.estudialo.entities;



// TODO: Auto-generated Javadoc
/**
 * The Class Departamento.
 *
 * @author LUIS
 */

public class Departamento {
	
	/** The id. */
	private int id;
	
	/** The nombre. */
	private String nombre;
	
	/** The pais. */
	private Pais pais;
	
		
	/**
	 * Instantiates a new departamento.
	 */
	public Departamento() {
		super();
	}


	/**
	 * Instantiates a new departamento.
	 *
	 * @param id the id
	 * @param nombre the nombre
	 * @param pais the pais
	 */
	public Departamento(int id, String nombre, Pais pais) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.pais = pais;
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


	/**
	 * Gets the pais.
	 *
	 * @return the pais
	 */
	public Pais getPais() {
		return pais;
	}


	/**
	 * Sets the pais.
	 *
	 * @param pais the new pais
	 */
	public void setPais(Pais pais) {
		this.pais = pais;
	}

		
	
}
