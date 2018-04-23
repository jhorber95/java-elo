/**
 * 
 */
package com.software.estudialo.entities;

// TODO: Auto-generated Javadoc
/**
 * The Class Estudiante.
 *
 * @author LUIS
 */
public class Estudiante extends Usuario {

	/** The id. */
	private int id;

	/**
	 * Instantiates a new estudiante.
	 */
	public Estudiante() {
		super();
	}

	/**
	 * Instantiates a new estudiante.
	 *
	 * @param id the id
	 */
	public Estudiante(int id) {
		super();
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.entities.Usuario#getId()
	 */
	public int getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.entities.Usuario#setId(int)
	 */
	public void setId(int id) {
		this.id = id;
	}

}
