/**
 * 
 */
package com.software.estudialo.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.software.estudialo.dao.EstudianteDao;
import com.software.estudialo.dao.FreelancerDao;
import com.software.estudialo.dao.InscripcionOfertaFreelancerDao;
import com.software.estudialo.dao.OfertaDao;
import com.software.estudialo.dao.UsuarioDao;
import com.software.estudialo.entities.Freelancer;
import com.software.estudialo.entities.InscripcionOfertaFreelancer;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.exception.DAOException;
import com.software.estudialo.exception.ObjectAlreadyExistException;
import com.software.estudialo.exception.ObjectNotFoundException;
import com.software.estudialo.exception.ValueNotPermittedException;
import com.software.estudialo.service.InscripcionOfertaFreelancerService;
import com.software.estudialo.util.Constants;

import ch.qos.logback.classic.util.LoggerNameUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class InscripcionOfertaFreelancerServiceImpl.
 *
 * @author LUIS
 */
@Service("inscripcionOfertaFreelancerService")
public class InscripcionOfertaFreelancerServiceImpl implements InscripcionOfertaFreelancerService {

	/** The logger. */
	private Logger logger = Logger.getLogger(InscripcionOfertaFreelancerServiceImpl.class);

	/** The categoriao dao. */
	@Autowired
	InscripcionOfertaFreelancerDao inscripcionOfertaFreelancerDao;

	/** The usuario dao. */
	@Autowired
	UsuarioDao usuarioDao;

	/** The estudiante dao. */
	@Autowired
	EstudianteDao estudianteDao;

	/** The oferta dao. */
	@Autowired
	OfertaDao ofertaDao;

	/** The freelancer dao. */
	@Autowired
	FreelancerDao freelancerDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.service.InscripcionOfertaFreelancerService#
	 * obtenerinscripcionOfertaFreelancer(int)
	 */
	@Override
	public InscripcionOfertaFreelancer obtenerinscripcionOfertaFreelancer(int id) {
		logger.debug("obtenerinscripcionOfertaFreelancer -- obtener  Inscripcion Oferta Freelancer");
		InscripcionOfertaFreelancer inscripcionOfertaFreelancer = new InscripcionOfertaFreelancer();
		inscripcionOfertaFreelancer = inscripcionOfertaFreelancerDao.obtenerinscripcionOfertaFreelancer(id);
		return inscripcionOfertaFreelancer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.service.InscripcionOfertaFreelancerService#
	 * inscribirEstudiante(int, int)
	 */
	@Override
	public void inscribirEstudiante(int idOferta, int idUsuario) {

		logger.debug("--- EN INSCRIBIR ESTUDIANTE A UNA OFERTA DE UN FREELANCER  -----");

		logger.debug("Iniciando Validaciones de inscripcion de estudiante a una oferta freelancer");

		if (idOferta <= 0 || idUsuario <= 0) {
			throw new ValueNotPermittedException("El id de usuario u oferta no existe");
		} else {

			Boolean existeEstudiante = estudianteDao.buscarEstudiantePorIdUsuario(idUsuario);
			Boolean existeOferta = ofertaDao.buscarOferta(idOferta);

			if (!existeEstudiante || !existeOferta) {
				throw new ObjectAlreadyExistException("El estudiante u oferta No existen");
			} else {

				// Freelancer freelancer =
				// freelancerDao.obtenerFreelancerPorOferta(idOferta);
				int idFreelancer = freelancerDao.obtenerIdFreelancerPorIdOferta(idOferta);

				// Obtenemos el id de la tabla oferta freelancer por el id de la
				// oferta y el id del freelancer
				int idOfertaFreelancer = inscripcionOfertaFreelancerDao.obtenerIdOfertaFreelancer(idOferta,
						idFreelancer);

				int idEstudiante = estudianteDao.obtenerIdEstudiantePorIdUsuario(idUsuario);

				if (idOfertaFreelancer <= 0 || idEstudiante <= 0) {
					throw new ObjectNotFoundException("El estudiante o el Freelancer no fueron encontrados");
				} else {

					// Buscamos si ya esta inscrito
					Boolean existeInscripcion = inscripcionOfertaFreelancerDao
							.buscarInscripcionOfertaFreelancer(idEstudiante, idOfertaFreelancer);

					if (existeInscripcion) {
						throw new ObjectAlreadyExistException("Ya se encuentra inscrito a la oferta!");
					} else {

						// Aqui ya podemos preinscribir al estudiante
						try {
							boolean returnInsercion = inscripcionOfertaFreelancerDao
									.inscribirEstudiante(idOfertaFreelancer, idEstudiante);

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.service.InscripcionOfertaFreelancerService#
	 * listarInscripcionOfertaFreelancers(java.lang.String, int, int, int, int,
	 * java.lang.String)
	 */
	@Override
	public JSONRespuesta listarInscripcionOfertaFreelancers(String search, int start, int length, int draw,
			int posicion, String direccion) {
		logger.debug("listarInscripcionOfertaFreelancers -- listar Inscripciones Ofertas Freelancers");
		JSONRespuesta listaInscripcionesOfertasFreelancers = inscripcionOfertaFreelancerDao
				.listarInscripcionOfertaFreelancers(search, start, length, draw, posicion, direccion);
		return listaInscripcionesOfertasFreelancers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.service.InscripcionOfertaFreelancerService#
	 * listarInscripcionOfertaFreelancer(int, java.lang.String, int, int, int,
	 * int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarInscripcionOfertaFreelancer(int idUsuario, String search, int start, int length,
			int draw, int posicion, String direccion) {
		logger.debug("listarInscripcionOfertaFreelancer -- listar Inscripciones Ofertas Freelancer");

		Boolean existeFreelancer = freelancerDao.buscarFreelancerPorIdUsuario(idUsuario);

		if (!existeFreelancer) {
			throw new ObjectNotFoundException("El usuario no es un freelancer");
		} else {

			int idFreelancer = freelancerDao.obtenerIdFreelancerConIdUsuario(idUsuario);
			logger.debug("El ID DEL FREELANCER ES: " + idFreelancer);

			JSONRespuesta listaInscripcionesOfertasFreelancer = inscripcionOfertaFreelancerDao
					.listarInscripcionOfertaFreelancer(idFreelancer, search, start, length, draw, posicion, direccion);
			return listaInscripcionesOfertasFreelancer;

		}

	}

	@Override
	public void confirmarInscripcionEstudiante(int idOferta, int idUsuario) {

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

				Boolean existeInscripcionOfertaFreelancerConEstudiante = inscripcionOfertaFreelancerDao
						.buscarInscripcionOfertaFreelancerConEstudiante(idOferta, idEstudiante);

				if (!existeInscripcionOfertaFreelancerConEstudiante) {
					throw new ObjectNotFoundException(Constants.INCRIPCION_NO_ENCONTRADA);
				} else {

					/**
					 * Antes de buscar si ya esta calificado validamos si la
					 * inscripcion se encuentra validada en estado INSCRIPCION Y
					 * NO PREINSCRICION
					 * 
					 */
					int idInscripcionOfertaFreelancer = inscripcionOfertaFreelancerDao
							.obtenerIdInscripcionOfertaFreelancer(idOferta, idEstudiante);

					Boolean estudianteEstadoInscritoEnInscripcionOfertaInstitucion = ofertaDao
							.estudianteEstadoInscritoOfertaInstitucion(idInscripcionOfertaFreelancer);

					if (estudianteEstadoInscritoEnInscripcionOfertaInstitucion) {
						throw new ObjectAlreadyExistException(Constants.INCRIPCION_YA_CONFIRMADA_POR_DOCENTE);
					} else {

						// Aqui ya podemos confirmar la inscripcion
						try {
							boolean returnInsercion = inscripcionOfertaFreelancerDao
									.confirmarInscripcionEstudiante(idInscripcionOfertaFreelancer);

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

	@Override
	public JSONRespuesta listarPreInscripcionesOfertaFreelancer(int idUsuario, String search, int start, int length,
			int draw, int posicion, String direccion) {

		logger.debug("listarPreInscripcionesOfertaFreelancer -- listar Inscripciones Ofertas Freelancer");

		Boolean existeFreelancer = freelancerDao.buscarFreelancerPorIdUsuario(idUsuario);

		if (!existeFreelancer) {
			throw new ObjectNotFoundException("El usuario no es un freelancer");
		} else {

			int idFreelancer = freelancerDao.obtenerIdFreelancerConIdUsuario(idUsuario);
			logger.debug("El ID DEL FREELANCER ES: " + idFreelancer);

			JSONRespuesta listaPreInscripcionesOfertasFreelancer = inscripcionOfertaFreelancerDao
					.listarPreInscripcionesOfertaFreelancer(idFreelancer, search, start, length, draw, posicion,
							direccion);
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

				Boolean existeInscripcionOfertaFreelancerConEstudiante = inscripcionOfertaFreelancerDao
						.buscarInscripcionOfertaFreelancerConEstudiante(idOferta, idEstudiante);

				if (!existeInscripcionOfertaFreelancerConEstudiante) {
					throw new ObjectNotFoundException(Constants.INCRIPCION_NO_ENCONTRADA);
				} else {

					
					int idInscripcionOfertaFreelancer = inscripcionOfertaFreelancerDao
							.obtenerIdInscripcionOfertaFreelancer(idOferta, idEstudiante);

					// Aqui ya podemos confirmar la inscripcion
					try {
						boolean returnInsercion = inscripcionOfertaFreelancerDao
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
