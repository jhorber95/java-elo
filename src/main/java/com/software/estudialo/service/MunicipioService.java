/**
 * 
 */
package com.software.estudialo.service;

import java.util.List;

import com.software.estudialo.entities.Municipio;

// TODO: Auto-generated Javadoc
/**
 * The Interface MunicipioService.
 *
 * @author LUIS
 */
public interface MunicipioService {
	
	/**
	 * Obtener municipios.
	 *
	 * @param idDepartamento the id departamento
	 * @return the list
	 */
	public List<Municipio> obtenerMunicipios(int idDepartamento);	
}
