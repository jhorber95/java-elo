/**
 * 
 */
package com.software.estudialo.dao;

import java.util.List;

import com.software.estudialo.entities.Categoria;

// TODO: Auto-generated Javadoc
/**
 * The Interface CategoriaDao.
 *
 * @author LUIS
 */
public interface CategoriaDao {
	
	/**
	 * Agrega un nuevo Categoria.
	 *
	 * @param categoria the categoria
	 * @return the boolean
	 */
	public Boolean agregarCategoria(Categoria categoria);
	
	/**
	 * Modificar una Categoria.
	 *
	 * @param categoria the categoria
	 * @return the boolean
	 */
	public Boolean modificarCategoria(Categoria categoria);
	
	/**
	 * Categoria segun el id.
	 *
	 * @param id the id
	 * @return Categoria segun el id
	 */
	public Categoria obtenerCategoria(int id);
	
	
	/**
	 * Categorias disponibles.
	 *
	 * @return Categorias disponibles
	 */
	public List<Categoria> obtenerCategorias();

}
