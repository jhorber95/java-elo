/**
 * 
 */
package com.software.estudialo.dao;

import java.util.List;


import com.software.estudialo.entities.TipoOfrece;

// TODO: Auto-generated Javadoc
/**
 * The Interface TipoOfreceDao.
 *
 * @author LUIS
 */
public interface TipoOfreceDao {
	
	/**
	 * Obtener tipos ofrece.
	 *
	 * @return the list
	 */
	public List<TipoOfrece> obtenerTiposOfrece();
	
	/**
	 * Obtener tipo ofrece.
	 *
	 * @param id the id
	 * @return the tipo ofrece
	 */
	public TipoOfrece obtenerTipoOfrece(int id);
}
