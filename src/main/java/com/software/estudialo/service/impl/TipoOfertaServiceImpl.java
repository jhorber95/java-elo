/**
 * 
 */
package com.software.estudialo.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.software.estudialo.dao.CategoriaDao;
import com.software.estudialo.dao.TipoOfertaDao;
import com.software.estudialo.entities.Categoria;
import com.software.estudialo.entities.TipoOferta;
import com.software.estudialo.service.TipoOfertaService;

// TODO: Auto-generated Javadoc
/**
 * The Class TipoOfertaServiceImpl.
 *
 * @author LUIS
 */
@Service("tipoOfertaService")
public class TipoOfertaServiceImpl implements TipoOfertaService{
	
	/** The logger. */
	private Logger logger = Logger.getLogger(TipoOfertaServiceImpl.class);
	
	/** The categoriao dao. */
	@Autowired
	TipoOfertaDao tipoOfertaDao;


	/* (non-Javadoc)
	 * @see com.software.estudialo.service.TipoOfertaService#obtenerTiposOferta()
	 */
	@Override
	public List<TipoOferta> obtenerTiposOferta() {
		logger.debug("obtenerTiposOferta -- obteniendo tipos oferta");
		List<TipoOferta> tiposOferta = tipoOfertaDao.obtenerTiposOferta();
		return tiposOferta;
	}
	
	

}
