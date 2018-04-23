/**
 * 
 */
package com.software.estudialo.entities;

import java.sql.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class Evento.
 *
 * @author LUIS
 */
public class Evento {

	/** The id. */
	private int id;

	/** The titulo. */
	private String titulo;

	/** The descripcion. */
	private String descripcion;

	/** The publicador. */
	private Institucion institucion;

	/** The tipo evento. */
	private TipoEvento tipoEvento;

	/** The fecha. */
	private Date fecha;

	/** The estado. */
	private Estado estado;

	private String imagenUrl;

	/**
	 * Instantiates a new evento.
	 */
	public Evento() {
		super();
	}

	public Evento(int id, String titulo, String descripcion, Institucion institucion, TipoEvento tipoEvento, Date fecha,
			Estado estado, String imagenUrl) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.institucion = institucion;
		this.tipoEvento = tipoEvento;
		this.fecha = fecha;
		this.estado = estado;
		this.imagenUrl = imagenUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getImagenUrl() {
		return imagenUrl;
	}

	public void setImagenUrl(String imagenUrl) {
		this.imagenUrl = imagenUrl;
	}

}
