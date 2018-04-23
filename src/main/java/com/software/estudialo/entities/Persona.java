/**
 * 
 */
package com.software.estudialo.entities;



// TODO: Auto-generated Javadoc
/**
 * The Class Persona.
 *
 * @author LUIS
 */
public class Persona {
	
	/** The id. */
	private int id;
	
	/** The tipo identificacion. */
	private TipoIdentificacion tipoIdentificacion;
	
	/** The identificacion. */
	private String identificacion;
	
	/** The nombres. */
	private String nombres;
	
	/** The apellidos. */
	private String apellidos;
	
	/** The municipio. */
	private Municipio municipio;
	
	/** The telefono. */
	private String telefono;
	
	/** The genero. */
	private Genero genero;
		
	
	/**
	 * Instantiates a new persona.
	 */
	public Persona() {
		super();
	}


	/**
	 * Instantiates a new persona.
	 *
	 * @param id the id
	 * @param tipoIdentificacion the tipo identificacion
	 * @param identificacion the identificacion
	 * @param nombres the nombres
	 * @param apellidos the apellidos
	 * @param municipio the municipio
	 * @param telefono the telefono
	 * @param genero the genero
	 */
	public Persona(int id, TipoIdentificacion tipoIdentificacion, String identificacion, String nombres,
			String apellidos, Municipio municipio, String telefono, Genero genero) {
		super();
		this.id = id;
		this.tipoIdentificacion = tipoIdentificacion;
		this.identificacion = identificacion;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.municipio = municipio;
		this.telefono = telefono;
		this.genero = genero;
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
	 * Gets the tipo identificacion.
	 *
	 * @return the tipo identificacion
	 */
	public TipoIdentificacion getTipoIdentificacion() {
		return tipoIdentificacion;
	}


	/**
	 * Sets the tipo identificacion.
	 *
	 * @param tipoIdentificacion the new tipo identificacion
	 */
	public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}


	/**
	 * Gets the identificacion.
	 *
	 * @return the identificacion
	 */
	public String getIdentificacion() {
		return identificacion;
	}


	/**
	 * Sets the identificacion.
	 *
	 * @param identificacion the new identificacion
	 */
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}


	/**
	 * Gets the nombres.
	 *
	 * @return the nombres
	 */
	public String getNombres() {
		return nombres;
	}


	/**
	 * Sets the nombres.
	 *
	 * @param nombres the new nombres
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}


	/**
	 * Gets the apellidos.
	 *
	 * @return the apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}


	/**
	 * Sets the apellidos.
	 *
	 * @param apellidos the new apellidos
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}


	/**
	 * Gets the municipio.
	 *
	 * @return the municipio
	 */
	public Municipio getMunicipio() {
		return municipio;
	}


	/**
	 * Sets the municipio.
	 *
	 * @param municipio the new municipio
	 */
	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}


	/**
	 * Gets the telefono.
	 *
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}


	/**
	 * Sets the telefono.
	 *
	 * @param telefono the new telefono
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	/**
	 * Gets the genero.
	 *
	 * @return the genero
	 */
	public Genero getGenero() {
		return genero;
	}


	/**
	 * Sets the genero.
	 *
	 * @param genero the new genero
	 */
	public void setGenero(Genero genero) {
		this.genero = genero;
	}
	
}
