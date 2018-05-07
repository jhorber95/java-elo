/**
 * 
 */
package com.software.estudialo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.software.estudialo.dao.EstudianteDao;
import com.software.estudialo.dao.InscripcionOfertaFreelancerDao;
import com.software.estudialo.dao.InscripcionOfertaInstitucionDao;
import com.software.estudialo.dao.UsuarioDao;
import com.software.estudialo.entities.InscripcionOfertaFreelancer;
import com.software.estudialo.entities.InscripcionOfertaInstitucion;
import com.software.estudialo.entities.Oferta;
import com.software.estudialo.exception.ObjectNotFoundException;
import com.software.estudialo.service.EstudianteService;
import com.software.estudialo.util.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class EstudianteServiceImpl.
 *
 * @author LUIS
 */
@Service("estudianteService")
public class EstudianteServiceImpl implements EstudianteService {
	
	/** The logger. */
	private Logger logger = Logger.getLogger(EstudianteServiceImpl.class);
	
	/** The categoriao dao. */
	@Autowired
	EstudianteDao estudianteDao;
	
	/** The usuario dao. */
	@Autowired
	UsuarioDao usuarioDao;
	
	@Autowired
	InscripcionOfertaFreelancerDao inscripcionOfertaFreelancerDao;
	
	@Autowired
	InscripcionOfertaInstitucionDao inscripcionOfertaInstitucionDao;
	
	
	/* (non-Javadoc)
	 * @see com.software.estudialo.service.EstudianteService#listarInscripcionesEstudiante(int)
	 */
	@Override
	public List<Object> listarInscripcionesEstudiante(int idUsuario) {
		logger.debug("listarInscripcionesEstudiantes -- listar inscripciones del estudiante");
		Boolean existeUsuario = usuarioDao.buscarUsuario(idUsuario);
		
		if (!existeUsuario) {
			throw new ObjectNotFoundException(Constants.USUARIO_NO_ENCONTRADO);
		}
		
		int idEstudiante = estudianteDao.obtenerIdEstudiantePorIdUsuario(idUsuario);
		//List<Object> listarInscripcionesEstudiante = estudianteDao.listarInscripcionesEstudiante(idEstudiante);
		
		//-----		
		List<Object> inscripciones = new ArrayList<Object>();	
		List<InscripcionOfertaFreelancer> inscripcionOfertasDeFreelancer = inscripcionOfertaFreelancerDao.obtenerInscripcionesOfertasFreelancerDeEstudiante(idEstudiante);
		
		for (InscripcionOfertaFreelancer inscripcionOfertaFreelancer : inscripcionOfertasDeFreelancer) {
			inscripciones.add(inscripcionOfertaFreelancer);
		}
		
		List<InscripcionOfertaInstitucion> inscripcionOfertasDeInstitucion = inscripcionOfertaInstitucionDao.obtenerInscripcionOfertaInstitucionDeEstudiante(idEstudiante);
		
		for (InscripcionOfertaInstitucion inscripcionOfertaInstitucion : inscripcionOfertasDeInstitucion) {
			inscripciones.add(inscripcionOfertaInstitucion);
		}
		
		return inscripciones;
	}


	@Override
	public void eliminarInscripcion(int idInscripcion, int idTipoOfrece) {

		logger.debug("---JB Dentro de eliminarInscripción");
		
		Boolean eliminado = false;
		
		if (idTipoOfrece == 1) {
			logger.debug("Eliminar inscripcion de institucion");
			eliminado = inscripcionOfertaInstitucionDao.eliminarInscripcionInstitucion(idInscripcion);
			
		}else {
			logger.debug("Eliminar inscripcion de freelancer");
			eliminado = inscripcionOfertaFreelancerDao.eliminarInscripcionFreelancer(idInscripcion);
		}
		logger.debug("---JB Saliendo de eliminarInscripción");
	}



	
	
	
	

}
