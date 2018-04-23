/**
 * 
 */
package com.software.estudialo.dao;

import java.util.List;
import java.util.Set;

import com.software.estudialo.entities.Estado;


// TODO: Auto-generated Javadoc
/**
 * The Interface EstadoDao.
 *
 * @author LUIS
 */
public interface EstadoDao {
	
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
