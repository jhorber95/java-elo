/**
 * 
 */
package com.software.estudialo.entities;

// TODO: Auto-generated Javadoc
/**
 * The Class Publicador.
 *
 * @author LUIS
 */
public class Publicador extends Usuario {

	/** The id. */
	private int id;
	
	/** The institucion. */
	private Institucion institucion;

	/**
	 * Instantiates a new publicador.
	 */
	public Publicador() {
		super();
	}

	/**
	 * Instantiates a new publicador.
	 *
	 * @param id the id
	 * @param institucion the institucion
	 */
	public Publicador(int id, Institucion institucion) {
		super();
		this.id = id;
		this.institucion = institucion;
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

	/**
	 * Gets the institucion.
	 *
	 * @return the institucion
	 */
	public Institucion getInstitucion() {
		return institucion;
	}

	/**
	 * Sets the institucion.
	 *
	 * @param institucion the new institucion
	 */
	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

}
