/**
 * 
 */
package com.software.estudialo.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Oferta.
 *
 * @author LUIS
 */
public class Oferta {

	/** The id. */
	private int id;

	/** The titulo. */
	private String titulo;

	/** The descripcion. */
	private String descripcion;

	/** The precio. */
	private String precio;

	/** The telefono. */
	private String telefono;

	/** The tipo ofrece. */
	private TipoOfrece tipoOfrece;

	/** The categoria. */
	private Categoria categoria;

	/** The municipio. */
	private Municipio municipio;

	/** The tipo oferta. */
	private TipoOferta tipoOferta;

	/** The fecha hora. */
	private Timestamp fechaHora;

	/** The estado. */
	private Estado estado;

	/** The ofrece. */
	private int idOfrece;

	private Calificacion calificacion;

	private String imagenUrl;

	private Boolean destacada;

	private List<Subcategoria> subcategorias;

	private List<Inteligencia> inteligencias;

	private Object ofrece;

	private Jornada jornada;

	private Modalidad modalidad;

	public Oferta() {
		super();
	}

	public Oferta(int id, String titulo, String descripcion, String precio, String telefono, TipoOfrece tipoOfrece,
			Categoria categoria, Municipio municipio, TipoOferta tipoOferta, Timestamp fechaHora, Estado estado,
			int idOfrece, Calificacion calificacion, String imagenUrl, Boolean destacada,
			List<Subcategoria> subcategorias, List<Inteligencia> inteligencias, Object ofrece, Jornada jornada,
			Modalidad modalidad) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.precio = precio;
		this.telefono = telefono;
		this.tipoOfrece = tipoOfrece;
		this.categoria = categoria;
		this.municipio = municipio;
		this.tipoOferta = tipoOferta;
		this.fechaHora = fechaHora;
		this.estado = estado;
		this.idOfrece = idOfrece;
		this.calificacion = calificacion;
		this.imagenUrl = imagenUrl;
		this.destacada = destacada;
		this.subcategorias = subcategorias;
		this.inteligencias = inteligencias;
		this.ofrece = ofrece;
		this.jornada = jornada;
		this.modalidad = modalidad;
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

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public TipoOfrece getTipoOfrece() {
		return tipoOfrece;
	}

	public void setTipoOfrece(TipoOfrece tipoOfrece) {
		this.tipoOfrece = tipoOfrece;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public TipoOferta getTipoOferta() {
		return tipoOferta;
	}

	public void setTipoOferta(TipoOferta tipoOferta) {
		this.tipoOferta = tipoOferta;
	}

	public Timestamp getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Timestamp fechaHora) {
		this.fechaHora = fechaHora;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public int getIdOfrece() {
		return idOfrece;
	}

	public void setIdOfrece(int idOfrece) {
		this.idOfrece = idOfrece;
	}

	public Calificacion getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Calificacion calificacion) {
		this.calificacion = calificacion;
	}

	public String getImagenUrl() {
		return imagenUrl;
	}

	public void setImagenUrl(String imagenUrl) {
		this.imagenUrl = imagenUrl;
	}

	public Boolean getDestacada() {
		return destacada;
	}

	public void setDestacada(Boolean destacada) {
		this.destacada = destacada;
	}

	public List<Subcategoria> getSubcategorias() {
		return subcategorias;
	}

	public void setSubcategorias(List<Subcategoria> subcategorias) {
		this.subcategorias = subcategorias;
	}

	public List<Inteligencia> getInteligencias() {
		return inteligencias;
	}

	public void setInteligencias(List<Inteligencia> inteligencias) {
		this.inteligencias = inteligencias;
	}

	public Object getOfrece() {
		return ofrece;
	}

	public void setOfrece(Object ofrece) {
		this.ofrece = ofrece;
	}

	public Jornada getJornada() {
		return jornada;
	}

	public void setJornada(Jornada jornada) {
		this.jornada = jornada;
	}

	public Modalidad getModalidad() {
		return modalidad;
	}

	public void setModalidad(Modalidad modalidad) {
		this.modalidad = modalidad;
	}

}
