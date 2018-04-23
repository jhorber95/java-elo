/**
 * 
 */
package com.software.estudialo.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.software.estudialo.dao.TipoOfreceDao;
import com.software.estudialo.entities.TipoOferta;
import com.software.estudialo.entities.TipoOfrece;
import com.software.estudialo.service.TipoOfreceService;

// TODO: Auto-generated Javadoc
/**
 * The Class TipoOfreceServiceImpl.
 *
 * @author LUIS
 */
@Service("tipoOfreceService")
public class TipoOfreceServiceImpl implements TipoOfreceService {

	/** The logger. */
	private Logger logger = Logger.getLogger(TipoOfreceServiceImpl.class);
	
	
	/** The tipo ofrece dao. */
	@Autowired
	TipoOfreceDao tipoOfreceDao;
	
	
	/* (non-Javadoc)
	 * @see com.software.estudialo.service.TipoOfreceService#obtenerTiposOfrece()
	 */
	@Override
	public List<TipoOfrece> obtenerTiposOfrece() {
		logger.debug("obtenerTiposOfrece -- obteniendo tipos ofrece");
		List<TipoOfrece> tiposOfrece = tipoOfreceDao.obtenerTiposOfrece();
		return tiposOfrece;
	}

	
	
	
}
