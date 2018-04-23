/**
 * 
 */
package com.software.estudialo.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.software.estudialo.dao.CategoriaDao;
import com.software.estudialo.dao.EstadoDao;
import com.software.estudialo.entities.Categoria;
import com.software.estudialo.entities.Estado;
import com.software.estudialo.service.EstadoService;

// TODO: Auto-generated Javadoc
/**
 * The Class EstadoServiceImpl.
 *
 * @author LUIS
 */
@Service("estadoService")
public class EstadoServiceImpl implements EstadoService{
	
	/** The logger. */
	private Logger logger = Logger.getLogger(EstadoServiceImpl.class);
	
	/** The categoriao dao. */
	@Autowired
	EstadoDao estadoDao;

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.EstadoService#listarEstados()
	 */
	@Override
	public List<Estado> listarEstados() {
		logger.debug("listarEstados -- obteniendo estados");
		List<Estado> estados = estadoDao.listarEstados();
		return estados;
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.EstadoService#listarEstadosPorEntidad(java.lang.String)
	 */
	@Override
	public List<Estado> listarEstadosPorEntidad(String entidad) {
		logger.debug("listarEstadosPorEntidad -- obteniendo estados por entidad");
		List<Estado> estados = estadoDao.listarEstadosPorEntidad(entidad);
		return estados;
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.EstadoService#obtenerEstado(int)
	 */
	@Override
	public Estado obtenerEstado(int id) {
		logger.debug("obtenerEstado -- obteniendo estados por id");
		Estado estado = estadoDao.obtenerEstado(id);
		return estado;
	}
	
	
	
	
}
