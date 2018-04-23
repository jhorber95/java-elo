/**
 * 
 */
package com.software.estudialo.entities;

/**
 * @author LUIS
 *
 */
public class Calificacion {

	private double puntaje;
	private double porcentaje;

	public Calificacion() {
		super();
	}

	public Calificacion(double puntaje, double porcentaje) {
		super();
		this.puntaje = puntaje;
		this.porcentaje = porcentaje;
	}

	public double getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(double puntaje) {
		this.puntaje = puntaje;
	}

	public double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(double porcentaje) {
		this.porcentaje = porcentaje;
	}

}
