/**
 * 
 */
package com.software.estudialo.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.software.estudialo.dao.MunicipioDao;
import com.software.estudialo.entities.Municipio;
import com.software.estudialo.service.MunicipioService;

// TODO: Auto-generated Javadoc
/**
 * The Class MunicipioServiceImpl.
 *
 * @author LUIS
 */
@Service("municipioService")
public class MunicipioServiceImpl implements MunicipioService{
	
	/** The logger. */
	private Logger logger = Logger.getLogger(MunicipioServiceImpl.class);
	
	/** The municipio dao. */
	@Autowired
	MunicipioDao municipioDao;

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.MunicipioService#obtenerMunicipios(int)
	 */
	@Override
	public List<Municipio> obtenerMunicipios(int idDepartamento) {
		logger.debug("obtenerMunicipios -- obteniendo municipios");
		List<Municipio> municipios = municipioDao.obtenerMunicipios(idDepartamento);
		return municipios;
	}	

}
