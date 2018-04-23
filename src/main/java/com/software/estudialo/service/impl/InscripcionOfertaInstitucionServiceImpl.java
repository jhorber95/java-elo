/**
 * 
 */
package com.software.estudialo.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.software.estudialo.dao.EstudianteDao;
import com.software.estudialo.dao.InstitucionDao;
import com.software.estudialo.dao.InscripcionOfertaInstitucionDao;
import com.software.estudialo.dao.OfertaDao;
import com.software.estudialo.dao.UsuarioDao;
import com.software.estudialo.entities.InscripcionOfertaInstitucion;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.exception.DAOException;
import com.software.estudialo.exception.ObjectAlreadyExistException;
import com.software.estudialo.exception.ObjectNotFoundException;
import com.software.estudialo.exception.ValueNotPermittedException;
import com.software.estudialo.service.InscripcionOfertaInstitucionService;
import com.software.estudialo.util.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class InscripcionOfertaInstitucionServiceImpl.
 *
 * @author LUIS
 */
@Service("inscripcionOfertaInstitucionService")
public class InscripcionOfertaInstitucionServiceImpl implements InscripcionOfertaInstitucionService {

	/** The logger. */
	private Logger logger = Logger.getLogger(InscripcionOfertaInstitucionServiceImpl.class);

	/** The categoriao dao. */
	@Autowired
	InscripcionOfertaInstitucionDao inscripcionOfertaInstitucionDao;

	/** The usuario dao. */
	@Autowired
	UsuarioDao usuarioDao;

	/** The estudiante dao. */
	@Autowired
	EstudianteDao estudianteDao;

	/** The oferta dao. */
	@Autowired
	OfertaDao ofertaDao;

	/** The institucion dao. */
	@Autowired
	InstitucionDao institucionDao;

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.InscripcionOfertaInstitucionService#obtenerinscripcionOfertaInstitucion(int)
	 */
	@Override
	public InscripcionOfertaInstitucion obtenerinscripcionOfertaInstitucion(int id) {
		logger.debug("obtenerinscripcionOfertaInstitucion -- obtener  Inscripcion Oferta Institucion");
		InscripcionOfertaInstitucion inscripcionOfertaInstitucion = new InscripcionOfertaInstitucion();
		inscripcionOfertaInstitucion = inscripcionOfertaInstitucionDao.obtenerinscripcionOfertaInstitucion(id);
		return inscripcionOfertaInstitucion;
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.InscripcionOfertaInstitucionService#inscribirEstudiante(int, int)
	 */
	@Override
	public void inscribirEstudiante(int idOferta, int idUsuario) {

		logger.debug("--- EN INSCRIBIR ESTUDIANTE A UNA OFERTA DE UNA INSTITUCION  -----");

		logger.debug("Iniciando Validaciones de inscripcion de estudiante a una oferta institucion");

		if (idOferta <= 0 || idUsuario <= 0) {
			throw new ValueNotPermittedException("El id de usuario u oferta no existe");
		} else {

			Boolean existeEstudiante = estudianteDao.buscarEstudiantePorIdUsuario(idUsuario);
			Boolean existeOferta = ofertaDao.buscarOferta(idOferta);

			if (!existeEstudiante || !existeOferta) {
				throw new ObjectAlreadyExistException("El estudiante u oferta No existen");
			} else {

				// Institucion institucion =
				// institucionDao.obtenerInstitucionPorOferta(idOferta);
				int idInstitucion = institucionDao.obtenerIdInstitucionPorIdOferta(idOferta);

				// Obtenemos el id de la tabla oferta institucion por el id de
				// la
				// oferta y el id del institucion
				int idOfertaInstitucion = inscripcionOfertaInstitucionDao.obtenerIdOfertaInstitucion(idOferta,
						idInstitucion);

				int idEstudiante = estudianteDao.obtenerIdEstudiantePorIdUsuario(idUsuario);

				if (idOfertaInstitucion <= 0 || idEstudiante <= 0) {
					throw new ObjectNotFoundException("El estudiante o el Institucion no fueron encontrados");
				} else {

					// Buscamos si ya esta inscrito
					Boolean existeInscripcion = inscripcionOfertaInstitucionDao
							.buscarInscripcionOfertaInstitucion(idEstudiante, idOfertaInstitucion);

					if (existeInscripcion) {
						throw new ObjectAlreadyExistException("Ya se encuentra inscrito a la oferta!");
					} else {

						// Aqui ya podemos preinscribir al estudiante
						try {
							boolean returnInsercion = inscripcionOfertaInstitucionDao
									.inscribirEstudiante(idOfertaInstitucion, idEstudiante);

							if (!returnInsercion) {
								throw new DAOException(
										"Ocurrio un inconveniente al insertar el registro en la base de datos");
							}

						} catch (DAOException daoe) {
							daoe.printStackTrace();
							throw new DAOException(
									"Ocurrio un inconveniente al insertar el registro en la base de datos");
						}

					}

				}

			}

		}

	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.InscripcionOfertaInstitucionService#listarInscripcionOfertaInstituciones(java.lang.String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarInscripcionOfertaInstituciones(String search, int start, int length, int draw,
			int posicion, String direccion) {
		logger.debug("listarInscripcionOfertaInstituciones -- listar Inscripciones Ofertas Institucions");
		JSONRespuesta listaInscripcionesOfertasInstitucions = inscripcionOfertaInstitucionDao
				.listarInscripcionOfertaInstituciones(search, start, length, draw, posicion, direccion);
		return listaInscripcionesOfertasInstitucions;
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.InscripcionOfertaInstitucionService#listarInscripcionOfertaInstitucion(int, java.lang.String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarInscripcionOfertaInstitucion(int idInstitucion, String search, int start, int length,
			int draw, int posicion, String direccion) {
		logger.debug("listarInscripcionOfertaInstitucion -- listar Inscripciones Ofertas Institucion");

		Boolean existeInstitucion = institucionDao.buscarInstitucion(idInstitucion);

		if (!existeInstitucion) {
			throw new ObjectNotFoundException("La institucion no es existe");
		} else {

			logger.debug("El ID DE LA INSTITUCION ES: " + idInstitucion);

			JSONRespuesta listaInscripcionesOfertasInstitucion = inscripcionOfertaInstitucionDao
					.listarInscripcionOfertaInstitucion(idInstitucion, search, start, length, draw, posicion,
							direccion);
			return listaInscripcionesOfertasInstitucion;
		}
	}

	@Override
	public void confirmarInscripcionEstudiante(int idOferta, int idUsuario) {
		
		logger.debug("--- CONFIRMAR INSCRIPCION ESTUDIANTE A UNA OFERTA DE UNA INSTITUCION  -----");

		logger.debug("Iniciando Validaciones de VALIDACION INSCRIPCION de estudiante a una oferta institucion");

		if (idOferta <= 0 || idUsuario <= 0) {
			throw new ValueNotPermittedException("El id de usuario u oferta no existe");
		} else {

			Boolean existeEstudiante = estudianteDao.buscarEstudiantePorIdUsuario(idUsuario);
			Boolean existeOferta = ofertaDao.buscarOferta(idOferta);

			if (!existeEstudiante || !existeOferta) {
				throw new ObjectAlreadyExistException("El estudiante u oferta No existen");
			} else {
				
				int idEstudiante = estudianteDao.obtenerIdEstudiantePorIdUsuario(idUsuario);

				Boolean existeInscripcionOfertaInstitucionConEstudiante = inscripcionOfertaInstitucionDao
						.buscarInscripcionOfertaInstitucionConEstudiante(idOferta, idEstudiante);

				if (!existeInscripcionOfertaInstitucionConEstudiante) {
					throw new ObjectNotFoundException(Constants.INCRIPCION_NO_ENCONTRADA);
				} else {
					
					/**
					 * Antes de buscar si ya esta calificado validamos si la inscripcion se encuentra validada en estado INSCRIPCION Y NO PREINSCRICION
					 * 
					 */
					int idInscripcionOfertaInstitucion = inscripcionOfertaInstitucionDao
							.obtenerIdInscripcionOfertaInstitucion(idOferta, idEstudiante);
					
					Boolean estudianteEstadoInscritoEnInscripcionOfertaInstitucion = ofertaDao.estudianteEstadoInscritoOfertaInstitucion(idInscripcionOfertaInstitucion);
					
					if (estudianteEstadoInscritoEnInscripcionOfertaInstitucion) {
						throw new ObjectAlreadyExistException(Constants.INCRIPCION_YA_CONFIRMADA_POR_DOCENTE);
					}else{						

						// Aqui ya podemos confirmar la inscripcion
						try {
							boolean returnInsercion = inscripcionOfertaInstitucionDao.confirmarInscripcionEstudiante(idInscripcionOfertaInstitucion); 

							if (!returnInsercion) {
								throw new DAOException(
										"Ocurrio un inconveniente al insertar el registro en la base de datos");
							}

						} catch (DAOException daoe) {
							daoe.printStackTrace();
							throw new DAOException("Ocurrio un inconveniente al insertar el registro en la base de datos");
						}
											
						
					}				

				}
				
			}

		}
		
	}

	@Override
	public JSONRespuesta listarPreInscripcionesOfertaFreelancer(int idInstitucion, String search, int start, int length,
			int draw, int posicion, String direccion) {
		logger.debug("listarPreInscripcionesOfertaFreelancer -- listar Inscripciones Ofertas Freelancer");
		
		Boolean existeInstitucion = institucionDao.buscarInstitucion(idInstitucion);
		
		if (!existeInstitucion) {
			throw new ObjectNotFoundException("La institucion no existe");
		}else{
					
			JSONRespuesta listaPreInscripcionesOfertasFreelancer = inscripcionOfertaInstitucionDao
					.listarPreInscripcionesOfertaInstitucion(idInstitucion, search, start, length, draw, posicion, direccion);
			return listaPreInscripcionesOfertasFreelancer;
			
		}	
	}

	@Override
	public void rechazarInscripcionEstudiante(int idOferta, int idUsuario) {
		
		logger.debug("--- CONFIRMAR INSCRIPCION ESTUDIANTE A UNA OFERTA DE UNA FREELANCER  -----");

		logger.debug("Iniciando Validaciones de VALIDACION INSCRIPCION de estudiante a una oferta freelancer");

		if (idOferta <= 0 || idUsuario <= 0) {
			throw new ValueNotPermittedException("El id de usuario u oferta no existe");
		} else {

			Boolean existeEstudiante = estudianteDao.buscarEstudiantePorIdUsuario(idUsuario);
			Boolean existeOferta = ofertaDao.buscarOferta(idOferta);

			if (!existeEstudiante || !existeOferta) {
				throw new ObjectAlreadyExistException("El estudiante u oferta No existen");
			} else {

				int idEstudiante = estudianteDao.obtenerIdEstudiantePorIdUsuario(idUsuario);

				Boolean existeInscripcionOfertaInstitucionConEstudiante = inscripcionOfertaInstitucionDao
						.buscarInscripcionOfertaInstitucionConEstudiante(idOferta, idEstudiante);

				if (!existeInscripcionOfertaInstitucionConEstudiante) {
					throw new ObjectNotFoundException(Constants.INCRIPCION_NO_ENCONTRADA);
				} else {

					
					int idInscripcionOfertaFreelancer = inscripcionOfertaInstitucionDao
							.obtenerIdInscripcionOfertaInstitucion(idOferta, idEstudiante);

					// Aqui ya podemos confirmar la inscripcion
					try {
						boolean returnInsercion = inscripcionOfertaInstitucionDao
								.rechazarInscripcionEstudiante(idInscripcionOfertaFreelancer);

						if (!returnInsercion) {
							throw new DAOException(
									"Ocurrio un inconveniente al insertar el registro en la base de datos");
						}

					} catch (DAOException daoe) {
						daoe.printStackTrace();
						throw new DAOException("Ocurrio un inconveniente al insertar el registro en la base de datos");
					}

				}

			}

		}
		
	}

}
