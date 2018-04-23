/**
 * 
 */
package com.software.estudialo.entities;

/**
 * @author LUIS
 *
 */
public class Publicidad {

	private int id;
	private String imagen;
	private String url;

	public Publicidad() {
		super();
	}

	public Publicidad(int id, String imagen, String url) {
		super();
		this.id = id;
		this.imagen = imagen;
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
