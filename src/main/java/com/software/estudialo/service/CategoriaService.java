package com.software.estudialo.service;

import java.util.List;

import com.software.estudialo.entities.Categoria;

// TODO: Auto-generated Javadoc
/**
 * The Interface CategoriaService.
 */
public interface CategoriaService {
	
	/**
	 * Listar las Categorias disponibles.
	 *
	 * @return tipos de categorias
	 */
	public List<Categoria> obtenerCategorias();	
	
}
