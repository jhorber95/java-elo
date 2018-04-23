/**
 * 
 */
package com.software.estudialo.dao;

import java.util.List;

import com.software.estudialo.entities.Interes;

/**
 * @author LUIS
 *
 */
public interface InteresDao {
	
	public Interes obtenerInteres(int id);
	
	public List<Interes> obtenerInteresesUsuario(int id);
	
	public void actualizarInteresesUsuario(List<Interes> intereses, int idUsuario);

	List<Interes> obtenerIntereses();

}
