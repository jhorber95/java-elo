/**
 * 
 */
package com.software.estudialo.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.software.estudialo.controller.MunicipioRestController;
import com.software.estudialo.dao.CategoriaDao;
import com.software.estudialo.entities.Categoria;
import com.software.estudialo.service.CategoriaService;

// TODO: Auto-generated Javadoc
/**
 * The Class CategoriaServiceImpl.
 *
 * @author LUIS
 */

@Service("categoriaService")
public class CategoriaServiceImpl implements CategoriaService {
	
	/** The logger. */
	private Logger logger = Logger.getLogger(CategoriaServiceImpl.class);
	
	/** The categoriao dao. */
	@Autowired
	CategoriaDao categoriaoDao;

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.CategoriaService#obtenerCategorias()
	 */
	@Override
	public List<Categoria> obtenerCategorias() {
		logger.debug("obtenerCategorias -- obteniendo categorias");
		List<Categoria> categorias = categoriaoDao.obtenerCategorias();
		return categorias;
	}
	
	
	

}
