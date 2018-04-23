/**
 * 
 */
package com.software.estudialo.dao;

import java.util.List;

import com.software.estudialo.entities.TipoInstitucion;

// TODO: Auto-generated Javadoc
/**
 * The Interface TipoInstitucionDao.
 *
 * @author LUIS
 */
public interface TipoInstitucionDao {
	
	/**
	 * Obtener tipo institucion.
	 *
	 * @param id the id
	 * @return the tipo institucion
	 */
	public TipoInstitucion obtenerTipoInstitucion(int id);
	
	/**
	 * Obtener tipos institucion.
	 *
	 * @return the list
	 */
	public List<TipoInstitucion> obtenerTiposInstitucion();

}
