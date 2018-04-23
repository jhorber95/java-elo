/**
 * 
 */
package com.software.estudialo.entities;

/**
 * @author LUIS
 *
 */
public class ResultadoVocacional {

	private Inteligencia primero;
	private Inteligencia segundo;
	private Inteligencia tercero;

	public ResultadoVocacional() {
		super();
	}

	public ResultadoVocacional(Inteligencia primero, Inteligencia segundo, Inteligencia tercero) {
		super();
		this.primero = primero;
		this.segundo = segundo;
		this.tercero = tercero;
	}

	public Inteligencia getPrimero() {
		return primero;
	}

	public void setPrimero(Inteligencia primero) {
		this.primero = primero;
	}

	public Inteligencia getSegundo() {
		return segundo;
	}

	public void setSegundo(Inteligencia segundo) {
		this.segundo = segundo;
	}

	public Inteligencia getTercero() {
		return tercero;
	}

	public void setTercero(Inteligencia tercero) {
		this.tercero = tercero;
	}

}
