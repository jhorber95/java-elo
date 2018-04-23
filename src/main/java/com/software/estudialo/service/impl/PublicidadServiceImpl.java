/**
 * 
 */
package com.software.estudialo.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.software.estudialo.dao.PublicidadDao;
import com.software.estudialo.entities.Categoria;
import com.software.estudialo.entities.Publicidad;
import com.software.estudialo.service.PublicidadService;

/**
 * @author LUIS
 *
 */
@Service("publicidadService")
public class PublicidadServiceImpl implements PublicidadService{
	
	private Logger logger = Logger.getLogger(PublicidadServiceImpl.class);
	
	@Autowired
	PublicidadDao publicidadDao;

	@Override
	public List<Publicidad> listarPublicidad() {
		logger.debug("listarPublicidad -- obteniendo publicidad");
		List<Publicidad> publicidad = publicidadDao.listarPublicidad();
		return publicidad;
	}
	
	
	

}
