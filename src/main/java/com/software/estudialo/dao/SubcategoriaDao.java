/**
 * 
 */
package com.software.estudialo.dao;

import java.util.List;

import com.software.estudialo.entities.Subcategoria;

/**
 * @author LUIS
 *
 */
public interface SubcategoriaDao {
	
	public List<Subcategoria> obtenerSubcategorias(int idOferta);

}
