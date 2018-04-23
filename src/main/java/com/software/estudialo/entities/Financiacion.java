/**
 * 
 */
package com.software.estudialo.entities;

// TODO: Auto-generated Javadoc
/**
 * The Class Financiacion.
 *
 * @author LUIS
 */
public class Financiacion {

	/** The id. */
	private int id;
	
	/** The informacion. */
	private String informacion;
	
	/** The institucion. */
	private Institucion institucion;
	
	/** The estado. */
	private Estado estado;

	/**
	 * Instantiates a new financiacion.
	 */
	public Financiacion() {
		super();
	}

	/**
	 * Instantiates a new financiacion.
	 *
	 * @param id the id
	 * @param informacion the informacion
	 * @param institucion the institucion
	 * @param estado the estado
	 */
	public Financiacion(int id, String informacion, Institucion institucion, Estado estado) {
		super();
		this.id = id;
		this.informacion = informacion;
		this.institucion = institucion;
		this.estado = estado;
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
	 * Gets the informacion.
	 *
	 * @return the informacion
	 */
	public String getInformacion() {
		return informacion;
	}

	/**
	 * Sets the informacion.
	 *
	 * @param informacion the new informacion
	 */
	public void setInformacion(String informacion) {
		this.informacion = informacion;
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

	/**
	 * Gets the estado.
	 *
	 * @return the estado
	 */
	public Estado getEstado() {
		return estado;
	}

	/**
	 * Sets the estado.
	 *
	 * @param estado the new estado
	 */
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

}
