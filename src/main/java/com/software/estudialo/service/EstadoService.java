/**
 * 
 */
package com.software.estudialo.service;

import java.util.List;


import com.software.estudialo.entities.Estado;



// TODO: Auto-generated Javadoc
/**
 * The Interface EstadoService.
 *
 * @author LUIS
 */
public interface EstadoService {
	
	
	/**
	 * Listar estados.
	 *
	 * @return the list
	 */
	public List<Estado> listarEstados();
	

	/**
	 * Buscar estados por entidad.
	 *
	 * @param entidad the entidad
	 * @return the list
	 */
	public List<Estado> listarEstadosPorEntidad(String entidad);
	

	/**
	 * Buscar estado.
	 *
	 * @param id the id
	 * @return the estado
	 */
	public Estado obtenerEstado(int id);	
	
}
