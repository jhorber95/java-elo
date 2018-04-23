/**
 * 
 */
package com.software.estudialo.entities;

import java.sql.Timestamp;

// TODO: Auto-generated Javadoc
/**
 * The Class InscripcionOfertaInstitucion.
 *
 * @author LUIS
 */
public class InscripcionOfertaInstitucion {

	/** The id. */
	private int id;
	
	/** The oferta. */
	private Oferta oferta;
	
	/** The estudiante. */
	private Estudiante estudiante;
	
	/** The confirmacion. */
	private boolean confirmacion;
	
	/** The fecha hora. */
	private Timestamp fechaHora;
	
	/** The estado. */
	private Estado estado;

	/**
	 * Instantiates a new inscripcion oferta institucion.
	 */
	public InscripcionOfertaInstitucion() {
		super();
	}

	/**
	 * Instantiates a new inscripcion oferta institucion.
	 *
	 * @param id the id
	 * @param oferta the oferta
	 * @param estudiante the estudiante
	 * @param confirmacion the confirmacion
	 * @param fechaHora the fecha hora
	 * @param estado the estado
	 */
	public InscripcionOfertaInstitucion(int id, Oferta oferta, Estudiante estudiante, boolean confirmacion,
			Timestamp fechaHora, Estado estado) {
		super();
		this.id = id;
		this.oferta = oferta;
		this.estudiante = estudiante;
		this.confirmacion = confirmacion;
		this.fechaHora = fechaHora;
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
	 * Gets the oferta.
	 *
	 * @return the oferta
	 */
	public Oferta getOferta() {
		return oferta;
	}

	/**
	 * Sets the oferta.
	 *
	 * @param oferta the new oferta
	 */
	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}

	/**
	 * Gets the estudiante.
	 *
	 * @return the estudiante
	 */
	public Estudiante getEstudiante() {
		return estudiante;
	}

	/**
	 * Sets the estudiante.
	 *
	 * @param estudiante the new estudiante
	 */
	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	/**
	 * Checks if is confirmacion.
	 *
	 * @return true, if is confirmacion
	 */
	public boolean isConfirmacion() {
		return confirmacion;
	}

	/**
	 * Sets the confirmacion.
	 *
	 * @param confirmacion the new confirmacion
	 */
	public void setConfirmacion(boolean confirmacion) {
		this.confirmacion = confirmacion;
	}

	/**
	 * Gets the fecha hora.
	 *
	 * @return the fecha hora
	 */
	public Timestamp getFechaHora() {
		return fechaHora;
	}

	/**
	 * Sets the fecha hora.
	 *
	 * @param fechaHora the new fecha hora
	 */
	public void setFechaHora(Timestamp fechaHora) {
		this.fechaHora = fechaHora;
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
