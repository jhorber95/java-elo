/**
 * 
 */
package com.software.estudialo.entities;

/**
 * @author LUIS
 *
 */
public class ItemOrder {

	private int posicion;
	private String direccion;

	public ItemOrder() {
		super();
	}

	public ItemOrder(int posicion, String direccion) {
		super();
		this.posicion = posicion;
		this.direccion = direccion;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

}
