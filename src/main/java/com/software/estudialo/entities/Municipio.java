/**
 * 
 */
package com.software.estudialo.entities;

// TODO: Auto-generated Javadoc
/**
 * The Class Municipio.
 *
 * @author LUIS
 */
public class Municipio {
	
	
	/** The id. */
	private int id;
	
	/** The nombre. */
	private String nombre;
	
	/** The departamento. */
	private Departamento departamento;
	
	/**
	 * Instantiates a new municipio.
	 */
	public Municipio() {
		super();
	}
	
	/**
	 * Instantiates a new municipio.
	 *
	 * @param id the id
	 * @param nombre the nombre
	 * @param departamento the departamento
	 */
	public Municipio(int id, String nombre, Departamento departamento) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.departamento = departamento;
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
	 * Gets the departamento.
	 *
	 * @return the departamento
	 */
	public Departamento getDepartamento() {
		return departamento;
	}

	/**
	 * Sets the departamento.
	 *
	 * @param departamento the new departamento
	 */
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
	

}
