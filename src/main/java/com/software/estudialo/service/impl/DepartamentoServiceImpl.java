/**
 * 
 */
package com.software.estudialo.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.software.estudialo.dao.DepartamentoDao;
import com.software.estudialo.entities.Departamento;
import com.software.estudialo.service.DepartamentoService;

// TODO: Auto-generated Javadoc
/**
 * The Class DepartamentoServiceImpl.
 *
 * @author LUIS
 */
@Service("departamentoService")
public class DepartamentoServiceImpl implements DepartamentoService{
	
	/** The logger. */
	private Logger logger = Logger.getLogger(DepartamentoServiceImpl.class);

	/** The departamento dao. */
	@Autowired
	DepartamentoDao departamentoDao;
	
	/* (non-Javadoc)
	 * @see com.software.estudialo.service.DepartamentoService#obtenerDepartamentos()
	 */
	@Override
	public List<Departamento> obtenerDepartamentos() {
		logger.debug("obtenerDepartamentos -- obteniendo departamentos");
		List<Departamento> departamentos = departamentoDao.obtenerDepartamentos();
		return departamentos;
	}
	
	
}
