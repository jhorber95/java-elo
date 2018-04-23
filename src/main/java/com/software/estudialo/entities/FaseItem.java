/**
 * 
 */
package com.software.estudialo.entities;

/**
 * @author LUIS
 *
 */
public class FaseItem {

	private int id;
	private String imagen;
	private Inteligencia inteligencia;
	private boolean seleccionado;

	public FaseItem() {
		super();
	}

	public FaseItem(int id, String imagen, Inteligencia inteligencia, boolean seleccionado) {
		super();
		this.id = id;
		this.imagen = imagen;
		this.inteligencia = inteligencia;
		this.seleccionado = seleccionado;
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

	public Inteligencia getInteligencia() {
		return inteligencia;
	}

	public void setInteligencia(Inteligencia inteligencia) {
		this.inteligencia = inteligencia;
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

}
