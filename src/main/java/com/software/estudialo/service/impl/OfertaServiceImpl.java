/**
 * 
 */
package com.software.estudialo.service.impl;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.software.estudialo.dao.EstudianteDao;
import com.software.estudialo.dao.FreelancerDao;
import com.software.estudialo.dao.InscripcionOfertaFreelancerDao;
import com.software.estudialo.dao.InscripcionOfertaInstitucionDao;
import com.software.estudialo.dao.InstitucionDao;
import com.software.estudialo.dao.OfertaDao;
import com.software.estudialo.dao.UsuarioDao;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Oferta;
import com.software.estudialo.exception.DAOException;
import com.software.estudialo.exception.ObjectAlreadyExistException;
import com.software.estudialo.exception.ObjectNotFoundException;
import com.software.estudialo.exception.ValueNotPermittedException;
import com.software.estudialo.service.OfertaService;
import com.software.estudialo.util.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class OfertaServiceImpl.
 *
 * @author LUIS
 */
@Service("ofertaService")
public class OfertaServiceImpl implements OfertaService {

	/** The logger. */
	private Logger logger = Logger.getLogger(OfertaServiceImpl.class);

	/** The categoriao dao. */
	@Autowired
	OfertaDao ofertaDao;

	@Autowired
	EstudianteDao estudianteDao;

	@Autowired
	UsuarioDao usuarioDao;
	
	@Autowired
	FreelancerDao freelancerDao;

	@Autowired
	InscripcionOfertaFreelancerDao inscripcionOfertaFreelancerDao;
	
	@Autowired
	InscripcionOfertaInstitucionDao inscripcionOfertaInstitucionDao;
	
	@Autowired
	InstitucionDao institucionDao;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.service.OfertaService#obtenerOferta(int)
	 */
	@Override
	public Oferta obtenerOferta(int id) {
		logger.debug("obtenerOferta -- obtener oferta");
		Oferta oferta = new Oferta();
		oferta = ofertaDao.obtenerOferta(id);
		return oferta;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.software.estudialo.service.OfertaService#listarOfertaBuscador(java.
	 * lang.String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarOfertaBuscador(String search, int start, int length, int draw, int posicion,
			String direccion) {
		logger.debug("listarOfertaBuscador -- listar ofertas por el buscador");
		JSONRespuesta listaOfertas = ofertaDao.listarOfertaBuscador(search, start, length, draw, posicion, direccion);
		return listaOfertas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.software.estudialo.service.OfertaService#listarOfertaFiltros(int,
	 * int, int, int, java.lang.String, int, int, int, int)
	 */
	@Override
	public JSONRespuesta listarOfertaFiltros(int start, int length, int draw, int posicion, String direccion,
			int categoria, int municipio, int tipoOfrece, int tipoOferta, int precioMinimo, int precioMaximo, String nombreOferta) {
		
		logger.debug("listarOfertaFiltros -- listar ofertas por filtros");
		JSONRespuesta listaOfertas = ofertaDao.listarOfertaFiltros(start, length, draw, posicion, direccion, categoria,
				municipio, tipoOfrece, tipoOferta, precioMinimo, precioMaximo, nombreOferta);
		return listaOfertas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.software.estudialo.service.OfertaService#agregarOferta(com.software.
	 * estudialo.entities.Oferta)
	 */
	@Override
	public int agregarOferta(Oferta oferta) {

		logger.debug("--- EN AGREGAR Oferta -----");

		logger.debug("Iniciando Validaciones de adicion de oferta");

		//|| oferta.getMunicipio() == null || oferta.getTipoOferta() == null || oferta.getIdOfrece() == 0 || oferta.getSubcategorias() == null) 
		/*
		 * 07/05/2018  Jhorkman Bernal
		 * 	
		 * Para el registro de la oferta no va  ser obligatorio la modalidad y la jornada, se va a poner por defecto no disponible
		 * */
		if (oferta.getTitulo() == null || oferta.getDescripcion() == null || oferta.getPrecio().equals("") 
				|| oferta.getTelefono() == null || oferta.getTipoOfrece() == null || oferta.getCategoria() == null
				|| oferta.getMunicipio() == null || oferta.getTipoOferta() == null || oferta.getIdOfrece() == 0 
				|| oferta.getJornada() == null || oferta.getModalidad() == null) {				
			
			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			// Buscamos que el oferta exista
			Boolean existeOferta = ofertaDao.buscarOferta(oferta);

			if (existeOferta) {
				throw new ObjectAlreadyExistException("Ya existe el oferta");
			} else {

				// Aqui ya podemos guardar el oferta
				try {
										
					logger.debug("Precio: " + oferta.getPrecio());
					String precioLimpio = oferta.getPrecio().replace(".", "");
					logger.debug("Precio: " + precioLimpio);
					
					oferta.setPrecio(precioLimpio);
					
					int idOferta = ofertaDao.agregarOferta(oferta);
					
					return idOferta;
					
				} catch (DAOException daoe) {
					daoe.printStackTrace();
					throw new DAOException("Ocurrio un inconveniente al insertar el registro en la base de datos");
				}

			}

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.service.OfertaService#modificarOferta(int,
	 * com.software.estudialo.entities.Oferta)
	 */
	@Override
	public void modificarOferta(int id, Oferta oferta) {

		logger.debug("--- EN MODIFICAR oferta para admin-----");

		logger.debug("Iniciando Validaciones de MODIFICAR de oferta");

//		if (oferta.getId() == 0 || oferta.getTitulo() == null || oferta.getDescripcion() == null
//				|| oferta.getTelefono() == null || oferta.getTipoOfrece() == null
//				|| oferta.getCategoria() == null || oferta.getMunicipio() == null || oferta.getTipoOferta() == null
//				|| oferta.getEstado() == null || oferta.getDestacada() == null) {

		
		if (oferta.getId() == 0 || oferta.getEstado() == null || oferta.getDestacada() == null) {
			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			// Buscamos que el oferta exista por nombre
			Boolean existeOferta = ofertaDao.buscarOferta(oferta.getId());

			if (!existeOferta) {
				throw new ObjectAlreadyExistException("El oferta NO existe");

			} else {

				// Aqui ya podemos MODIFICAR el oferta
				try {
					boolean returnModificacion = ofertaDao.modificarOferta(id, oferta);

					if (!returnModificacion) {
						throw new DAOException("Ocurrio un inconveniente al insertar el registro en la base de datos");
					}

				} catch (DAOException daoe) {
					daoe.printStackTrace();
					throw new DAOException("Ocurrio un inconveniente al insertar el registro en la base de datos");
				}

			}

		}

	}
	
	
	@Override
	public void modificarOfertaGeneral(int id, Oferta oferta) {
		
		logger.debug("--- EN MODIFICAR oferta para usuarios -----");

		logger.debug("Iniciando Validaciones de MODIFICAR de oferta");

		if (oferta.getId() == 0 || oferta.getTitulo() == null || oferta.getDescripcion() == null
				|| oferta.getTelefono() == null || oferta.getTipoOfrece() == null
				|| oferta.getCategoria() == null || oferta.getMunicipio() == null || oferta.getTipoOferta() == null || oferta.getJornada() == null 
				|| oferta.getModalidad() == null) {

			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			// Buscamos que el oferta exista por nombre
			Boolean existeOferta = ofertaDao.buscarOferta(oferta.getId());

			if (!existeOferta) {
				throw new ObjectAlreadyExistException("El oferta NO existe");

			} else {

				// Aqui ya podemos MODIFICAR el oferta
				try {
					boolean returnModificacion = ofertaDao.modificarOfertaGeneral(id, oferta);

					if (!returnModificacion) {
						throw new DAOException("Ocurrio un inconveniente al insertar el registro en la base de datos");
					}

				} catch (DAOException daoe) {
					daoe.printStackTrace();
					throw new DAOException("Ocurrio un inconveniente al insertar el registro en la base de datos");
				}

			}

		}
		
	}
	
	
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.service.OfertaService#eliminarOferta(int)
	 */
	@Override
	public void eliminarOferta(int id) {

		logger.debug("--- EN ELIMINAR oferta -----");

		if (id > 0) {

			Boolean existeOfertaPorId = ofertaDao.buscarOferta(id);

			if (!existeOfertaPorId) {
				throw new ObjectNotFoundException("La oferta no se puede eliminar, pues no existe");
			} else {

				try {
					boolean returnInsercion = ofertaDao.eliminarOferta(id);

					if (!returnInsercion) {
						throw new DAOException("Ocurrio un inconveniente al eliminar el registro en la base de datos");
					}

				} catch (DAOException daoe) {
					daoe.printStackTrace();
					throw new DAOException("Ocurrio un inconveniente al eliminar el registro en la base de datos");
				}
			}

		} else {
			throw new ValueNotPermittedException("El id no esta permitido");
		}

		logger.debug("--- SALIR DE ELIMINAR oferta -----");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.software.estudialo.service.OfertaService#listarOfertas(java.lang.
	 * String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarOfertas(String search, int start, int length, int draw, int posicion, String direccion) {
		logger.debug("listarOfertas -- listar ofertas");
		JSONRespuesta listaOfertas = ofertaDao.listarOfertas(search, start, length, draw, posicion, direccion);
		return listaOfertas;
	}

	@Override
	public Boolean calificacionOfertaFreelancerRealizada(int idOferta, int idUsuario) {

		logger.debug("calificacionOfertaFreelancerRealizada -- verificar la calificacion de una oferta freelancer");

		Boolean existeUsuario = usuarioDao.buscarUsuario(idUsuario);

		if (!existeUsuario) {
			throw new ObjectNotFoundException(Constants.USUARIO_NO_ENCONTRADO);
		} else {

			int idEstudiante = estudianteDao.obtenerIdEstudiantePorIdUsuario(idUsuario);

			Boolean existeInscripcionOfertaFreelancerConEstudiante = inscripcionOfertaFreelancerDao
					.buscarInscripcionOfertaFreelancerConEstudiante(idOferta, idEstudiante);

			if (!existeInscripcionOfertaFreelancerConEstudiante) {
				throw new ObjectNotFoundException(Constants.INCRIPCION_NO_ENCONTRADA);
			} else {

				Boolean realizado = ofertaDao.calificacionOfertaFreelancerRealizada(idOferta, idEstudiante);
				return realizado;
			}

		}

	}

	@Override
	public void calificarOfertaFreelancer(int idOferta, int idUsuario, double calificacion, String comentario) {

		logger.debug("calificarOfertaFreelancer -- calificar una oferta freelancer");

		Boolean existeUsuario = usuarioDao.buscarUsuario(idUsuario);

		if (!existeUsuario) {
			throw new ObjectNotFoundException(Constants.USUARIO_NO_ENCONTRADO);
		} else {
			int idEstudiante = estudianteDao.obtenerIdEstudiantePorIdUsuario(idUsuario);

			Boolean existeInscripcionOfertaFreelancerConEstudiante = inscripcionOfertaFreelancerDao
					.buscarInscripcionOfertaFreelancerConEstudiante(idOferta, idEstudiante);

			if (!existeInscripcionOfertaFreelancerConEstudiante) {
				throw new ObjectNotFoundException(Constants.INCRIPCION_NO_ENCONTRADA);
			} else {
				
				/**
				 * Antes de buscar si ya esta calificado validamos si la inscripcion se encuentra validada en estado INSCRIPCION Y NO PREINSCRICION
				 * 
				 */
				int idInscripcionOfertaFreelancer = inscripcionOfertaFreelancerDao
						.obtenerIdInscripcionOfertaFreelancer(idOferta, idEstudiante);
				
				Boolean estudianteEstadoInscritoEnInscripcionOfertaFreelancer = ofertaDao.estudianteEstadoInscritoOfertaFreelancer(idInscripcionOfertaFreelancer);
				
				if (!estudianteEstadoInscritoEnInscripcionOfertaFreelancer) {
					throw new ObjectNotFoundException(Constants.INCRIPCION_NO_CONFIRMADA_POR_DOCENTE);
				}else{
					
					Boolean realizado = ofertaDao.calificacionOfertaFreelancerRealizada(idOferta, idEstudiante);

					if (realizado) {
						throw new ObjectAlreadyExistException(Constants.CALIFICACION_EXISTENTE);
					} else {				

						// Aqui ya podemos guardar el oferta
						try {
							boolean returnInsercion = ofertaDao.calificarOfertaFreelancer(idInscripcionOfertaFreelancer,
									calificacion, comentario);

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
	public Boolean calificacionOfertaInstitucionRealizada(int idOferta, int idUsuario) {
		
		logger.debug("calificacionOfertaInstitucionRealizada -- verificar la calificacion de una oferta institucion");

		Boolean existeUsuario = usuarioDao.buscarUsuario(idUsuario);

		if (!existeUsuario) {
			throw new ObjectNotFoundException(Constants.USUARIO_NO_ENCONTRADO);
		} else {

			int idEstudiante = estudianteDao.obtenerIdEstudiantePorIdUsuario(idUsuario);

			Boolean existeInscripcionOfertaInstitucionConEstudiante = inscripcionOfertaInstitucionDao
					.buscarInscripcionOfertaInstitucionConEstudiante(idOferta, idEstudiante);

			if (!existeInscripcionOfertaInstitucionConEstudiante) {
				throw new ObjectNotFoundException(Constants.INCRIPCION_NO_ENCONTRADA);
			} else {

				Boolean realizado = ofertaDao.calificacionOfertaInstitucionRealizada(idOferta, idEstudiante);
				return realizado;
			}

		}
		
	}

	@Override
	public void calificarOfertaInstitucion(int idOferta, int idUsuario, double calificacion, String comentario) {

		logger.debug("calificarOfertaInstitucion -- calificar una oferta institucion");

		Boolean existeUsuario = usuarioDao.buscarUsuario(idUsuario);

		if (!existeUsuario) {
			throw new ObjectNotFoundException(Constants.USUARIO_NO_ENCONTRADO);
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
				
				if (!estudianteEstadoInscritoEnInscripcionOfertaInstitucion) {
					throw new ObjectNotFoundException(Constants.INCRIPCION_NO_CONFIRMADA_POR_DOCENTE);
				}else{
					
					Boolean realizado = ofertaDao.calificacionOfertaInstitucionRealizada(idOferta, idEstudiante);

					if (realizado) {
						throw new ObjectAlreadyExistException(Constants.CALIFICACION_EXISTENTE);
					} else {				

						// Aqui ya podemos guardar el oferta
						try {
							boolean returnInsercion = ofertaDao.calificarOfertaInstitucion(idInscripcionOfertaInstitucion,
									calificacion, comentario);

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
	public List<Oferta> listarOfertasDestacadas() {
		logger.debug("listarOfertasDestacadas -- listar ofertas destacadas");
		List<Oferta> listaOfertasDestacadas = ofertaDao.listarOfertasDestacadas();
		return listaOfertasDestacadas;
	}

	@Override
	public List<Oferta> getOfertasOfrecidasInstitucion(int idInstitucion) {
		logger.debug("getOfertasOfrecidasInstitucion -- obteniendo ofertas ofrecidas por una institucion");
		List<Oferta> ofertas = ofertaDao.getOfertasOfrecidasInstitucion(idInstitucion);
		return ofertas;
	}

	@Override
	public List<Oferta> getOfertasOfrecidasFreelancer(int idUsuarioFreelancer) {
		logger.debug("getOfertasOfrecidasInstitucion -- obteniendo ofertas ofrecidas por una freelancer");
		
		//Buscamos si el usuario ya existe como freelancer
		Boolean existeFreelancer = freelancerDao.buscarFreelancerPorIdUsuario(idUsuarioFreelancer);
		
		if (!existeFreelancer) {
			throw new ObjectNotFoundException("El usuario no es freelancer");
		}else{
			int idFreelancer = freelancerDao.obtenerIdFreelancerConIdUsuario(idUsuarioFreelancer);
			
			List<Oferta> ofertas = ofertaDao.getOfertasOfrecidasFreelancer(idFreelancer);
			return ofertas;
		}
		
		
	}

	@Override
	public JSONRespuesta listarOfertasAdmin(String search, int start, int length, int draw, int posicion,
			String direccion) {
		logger.debug("listarOfertasAdmin -- listar ofertas por admin");
		JSONRespuesta listaOfertas = ofertaDao.listarOfertasAdmin(search, start, length, draw, posicion, direccion);
		return listaOfertas;
	}

	@Override
	public void eliminarOfertaFreelancer(int idOferta, int idUsuario) {
		
		logger.debug("--- EN ELIMINAR oferta -----");

		if (idOferta > 0) {

			Boolean existeOfertaPorId = ofertaDao.buscarOferta(idOferta);

			if (!existeOfertaPorId) {
				throw new ObjectNotFoundException("La oferta no se puede eliminar, pues no existe");
			} else {
				
				Boolean existeFreelancer = freelancerDao.buscarFreelancerPorIdUsuario(idUsuario);
				
				if (!existeFreelancer) {
					throw new ObjectNotFoundException("El usuario no es un freelancer");
				} else {
					
					int idFreelancer = freelancerDao.obtenerIdFreelancerConIdUsuario(idUsuario);
					
					//validar si le pertenece la oferta
					Boolean pertenece = ofertaDao.ofertaPerteneceFreelancer(idOferta, idFreelancer);
					
					if (!pertenece) {
						throw new ObjectNotFoundException("La oferta no le pertenece al freelancer");
					} else{
						
						try {
							boolean returnInsercion = ofertaDao.eliminarOferta(idOferta);

							if (!returnInsercion) {
								throw new DAOException("Ocurrio un inconveniente al eliminar el registro en la base de datos");
							}

						} catch (DAOException daoe) {
							daoe.printStackTrace();
							throw new DAOException("Ocurrio un inconveniente al eliminar el registro en la base de datos");
						}
						
					}					
					
				}
				
			}

		} else {
			throw new ValueNotPermittedException("El id no esta permitido");
		}

		logger.debug("--- SALIR DE ELIMINAR oferta -----");
		
	}

	@Override
	public void eliminarOfertaInstitucion(int idOferta, int idInstitucion) {
		
		
		logger.debug("--- EN ELIMINAR oferta -----");

		if (idOferta > 0) {

			Boolean existeOfertaPorId = ofertaDao.buscarOferta(idOferta);

			if (!existeOfertaPorId) {
				throw new ObjectNotFoundException("La oferta no se puede eliminar, pues no existe");
			} else {
				
				Boolean existeInstitucion = institucionDao.buscarInstitucion(idInstitucion);
				
				if (!existeInstitucion) {
					throw new ObjectNotFoundException("La institucion no existe");
				} else {
						
					
					//validar si le pertenece la oferta
					Boolean pertenece = ofertaDao.ofertaPerteneceInstitucion(idOferta, idInstitucion);
					
					if (!pertenece) {
						throw new ObjectNotFoundException("La oferta no le pertenece a la institucion");
					} else{
						
						try {
							boolean returnInsercion = ofertaDao.eliminarOferta(idOferta);

							if (!returnInsercion) {
								throw new DAOException("Ocurrio un inconveniente al eliminar el registro en la base de datos");
							}

						} catch (DAOException daoe) {
							daoe.printStackTrace();
							throw new DAOException("Ocurrio un inconveniente al eliminar el registro en la base de datos");
						}
						
					}					
					
				}
				
			}

		} else {
			throw new ValueNotPermittedException("El id no esta permitido");
		}

		logger.debug("--- SALIR DE ELIMINAR oferta -----");
		
	}

	@Override
	public JSONRespuesta listarOfertas() {
		logger.debug("-- JB listarOfertas -- listando ofertas");
		JSONRespuesta listaOfertas = ofertaDao.listarOfertas();
		return listaOfertas;
	}

	

	

}
