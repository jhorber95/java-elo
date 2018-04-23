/**
 * 
 */
package com.software.estudialo.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.software.estudialo.dao.CategoriaDao;
import com.software.estudialo.dao.InteresDao;
import com.software.estudialo.entities.Categoria;
import com.software.estudialo.entities.Interes;
import com.software.estudialo.service.InteresService;

/**
 * @author LUIS
 *
 */
@Service("interesService")
public class InteresServiceImpl implements InteresService{

	/** The logger. */
	private Logger logger = Logger.getLogger(InteresServiceImpl.class);
	
	/** The categoriao dao. */
	@Autowired
	InteresDao interesDao;
	
	
	
	@Override
	public List<Interes> obtenerIntereses() {
		logger.debug("obtenerIntereses -- obteniendo intereses");
		List<Interes> intereses = interesDao.obtenerIntereses();
		return intereses;
	}
	
	
	
	
	
	
}
