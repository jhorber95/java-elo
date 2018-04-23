/**
 * 
 */
package com.software.estudialo.dao;

import java.util.List;

import com.software.estudialo.entities.Inteligencia;

/**
 * @author LUIS
 *
 */
public interface InteligenciaDao {
	
	public Inteligencia obtenerInteligencia(int id);
	
	public List<Inteligencia> obtenerInteligenciasOferta(int idOferta);

}
