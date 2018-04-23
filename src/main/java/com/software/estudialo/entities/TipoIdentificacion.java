/**
 * 
 */
package com.software.estudialo.entities;

// TODO: Auto-generated Javadoc
/**
 * The Class TipoIdentificacion.
 *
 * @author LUIS
 */
public class TipoIdentificacion {
	
	/** The id. */
	private int id;
	
	/** The nombre. */
	private String nombre;
	
	/** The acronimo. */
	private String acronimo;
	
	/**
	 * Instantiates a new tipo identificacion.
	 */
	public TipoIdentificacion() {
		super();
	}

	/**
	 * Instantiates a new tipo identificacion.
	 *
	 * @param id the id
	 * @param nombre the nombre
	 * @param acronimo the acronimo
	 */
	public TipoIdentificacion(int id, String nombre, String acronimo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.acronimo = acronimo;
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
	 * Gets the acronimo.
	 *
	 * @return the acronimo
	 */
	public String getAcronimo() {
		return acronimo;
	}

	/**
	 * Sets the acronimo.
	 *
	 * @param acronimo the new acronimo
	 */
	public void setAcronimo(String acronimo) {
		this.acronimo = acronimo;
	}	
	

}
