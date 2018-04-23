/**
 * 
 */
package com.software.estudialo.dao;

import java.util.List;

import com.software.estudialo.entities.Departamento;

// TODO: Auto-generated Javadoc
/**
 * The Interface DepartamentoDao.
 *
 * @author LUIS
 */
public interface DepartamentoDao {
	
	/**
	 * Obtener departamento.
	 *
	 * @param id the id
	 * @return the departamento
	 */
	public Departamento obtenerDepartamento(int id);
	
	/**
	 * Obtener departamentos.
	 *
	 * @return the list
	 */
	public List<Departamento> obtenerDepartamentos();	
}
