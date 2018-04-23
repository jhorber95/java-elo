/**
 * 
 */
package com.software.estudialo.entities;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Institucion.
 *
 * @author LUIS
 */
public class Institucion {

	/** The id. */
	private int id;

	/** The nombre. */
	private String nombre;

	/** The nit. */
	private String nit;

	/** The latitud. */
	private String latitud;

	/** The longitud. */
	private String longitud;

	/** The direccion. */
	private String direccion;

	/** The telefono. */
	private String telefono;

	/** The url. */
	private String url;

	/** The descripcion. */
	private String descripcion;

	/** The email. */
	private String email;

	/** The tipo institucion. */
	private TipoInstitucion tipoInstitucion;

	/** The password. */
	private String password;

	/** The estado. */
	private Estado estado;

	private String urlImagen;
	
	private List<Rol> roles;

	/**
	 * Instantiates a new institucion.
	 */
	public Institucion() {
		super();
	}

	public Institucion(int id, String nombre, String nit, String latitud, String longitud, String direccion,
			String telefono, String url, String descripcion, String email, TipoInstitucion tipoInstitucion,
			String password, Estado estado, String urlImagen) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nit = nit;
		this.latitud = latitud;
		this.longitud = longitud;
		this.direccion = direccion;
		this.telefono = telefono;
		this.url = url;
		this.descripcion = descripcion;
		this.email = email;
		this.tipoInstitucion = tipoInstitucion;
		this.password = password;
		this.estado = estado;
		this.urlImagen = urlImagen;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TipoInstitucion getTipoInstitucion() {
		return tipoInstitucion;
	}

	public void setTipoInstitucion(TipoInstitucion tipoInstitucion) {
		this.tipoInstitucion = tipoInstitucion;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getUrlImagen() {
		return urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}

	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}
	
	

}
