/**
 * 
 */
package com.software.estudialo.dao;

import java.util.List;


import com.software.estudialo.entities.Municipio;

// TODO: Auto-generated Javadoc
/**
 * The Interface MunicipioDao.
 *
 * @author LUIS
 */
public interface MunicipioDao {
	
	/**
	 * Obtener municipios.
	 *
	 * @param idDepartamento the id departamento
	 * @return the list
	 */
	public List<Municipio> obtenerMunicipios(int idDepartamento);
	
	/**
	 * Obtener municipio.
	 *
	 * @param id the id
	 * @return the municipio
	 */
	public Municipio obtenerMunicipio(int id);

}
