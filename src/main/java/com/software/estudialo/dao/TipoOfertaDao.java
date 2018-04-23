/**
 * 
 */
package com.software.estudialo.dao;

import java.util.List;


import com.software.estudialo.entities.TipoOferta;

// TODO: Auto-generated Javadoc
/**
 * The Interface TipoOfertaDao.
 *
 * @author LUIS
 */
public interface TipoOfertaDao {
	
	/**
	 * Obtener tipos oferta.
	 *
	 * @return the list
	 */
	public List<TipoOferta> obtenerTiposOferta();
	
	/**
	 * Obtener tipo oferta.
	 *
	 * @param id the id
	 * @return the tipo oferta
	 */
	public TipoOferta obtenerTipoOferta(int id);

}
