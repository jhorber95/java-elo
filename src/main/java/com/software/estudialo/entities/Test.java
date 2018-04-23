/**
 * 
 */
package com.software.estudialo.entities;

import java.util.List;

/**
 * @author LUIS
 *
 */
public class Test {

	private int id;
	private String nombre;
	private List<Fase> fases;

	public Test() {
		super();
	}

	public Test(int id, String nombre, List<Fase> fases) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fases = fases;
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

	public List<Fase> getFases() {
		return fases;
	}

	public void setFases(List<Fase> fases) {
		this.fases = fases;
	}

}
