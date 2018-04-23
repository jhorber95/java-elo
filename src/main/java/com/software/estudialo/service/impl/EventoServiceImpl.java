/**
 * 
 */
package com.software.estudialo.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.software.estudialo.dao.EventoDao;
import com.software.estudialo.dao.InstitucionDao;
import com.software.estudialo.dao.OfertaDao;
import com.software.estudialo.dao.PublicadorDao;
import com.software.estudialo.entities.Evento;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.exception.DAOException;
import com.software.estudialo.exception.ObjectAlreadyExistException;
import com.software.estudialo.exception.ObjectNotFoundException;
import com.software.estudialo.exception.ValueNotPermittedException;
import com.software.estudialo.service.EventoService;

// TODO: Auto-generated Javadoc
/**
 * The Class EventoServiceImpl.
 *
 * @author LUIS
 */
@Service("eventoService")
public class EventoServiceImpl implements EventoService{
	
	/** The logger. */
	private Logger logger = Logger.getLogger(EventoServiceImpl.class);
	
	/** The categoriao dao. */
	@Autowired
	EventoDao eventoDao;
	
	@Autowired
	InstitucionDao institucionDao;
	
	/** The publicador dao. */
	@Autowired
	PublicadorDao publicadorDao;
	
	/* (non-Javadoc)
	 * @see com.software.estudialo.service.EventoService#obtenerEvento(int)
	 */
	@Override
	public Evento obtenerEvento(int id) {
		logger.debug("obtenerEvento -- obtener evento");
		Evento evento = new Evento();
		evento = eventoDao.obtenerEvento(id);
		return evento;
	}
	

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.EventoService#listarEventos(java.lang.String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarEventos(String search, int start, int length, int draw, int posicion, String direccion) {
		logger.debug("listarEventos -- listar eventos");
		JSONRespuesta listaEventos = eventoDao.listarEventos(search, start, length, draw, posicion, direccion);
		return listaEventos; 
	}	
	
	
	/* (non-Javadoc)
	 * @see com.software.estudialo.service.EventoService#agregarEvento(com.software.estudialo.entities.Evento)
	 */
	@Override
	public int agregarEvento(Evento evento) {
		
		logger.debug("--- EN AGREGAR Evento -----");

		logger.debug("Iniciando Validaciones de adicion de evento");

		if (evento.getTitulo() == null || evento.getDescripcion() == null || evento.getInstitucion() == null || evento.getTipoEvento() == null ) {
			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			// Buscamos que el evento exista 
			Boolean existeEvento = eventoDao.buscarEvento(evento); 

			if (existeEvento) {
				throw new ObjectAlreadyExistException("Ya existe el evento");
			} else {
				
				//Boolean existePublicador = publicadorDao.buscarPublicadorPorIdUsuario(evento.getInstitucion().getId());
				
				Boolean existeInstitucion = institucionDao.buscarInstitucion(evento.getInstitucion().getId());
				
				if (!existeInstitucion) {
					throw new ObjectNotFoundException("La institucion no existe");
				}else{
					
					// Aqui ya podemos guardar el evento
					try {
						int idEvento = eventoDao.agregarEvento(evento);

						return idEvento;

					} catch (DAOException daoe) {
						daoe.printStackTrace();
						throw new DAOException("Ocurrio un inconveniente al insertar el registro en la base de datos");
					}
					
				}			

			}

		}	
		
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.EventoService#modificarEvento(int, com.software.estudialo.entities.Evento)
	 */
	@Override
	public void modificarEvento(int id, Evento evento) {
		
		logger.debug("--- EN MODIFICAR evento -----");

		logger.debug("Iniciando Validaciones de MODIFICAR de evento");

		if (evento.getId() == 0 || evento.getTitulo() == null || evento.getDescripcion() == null || evento.getInstitucion() == null || evento.getTipoEvento() == null ||
				evento.getFecha() == null || evento.getEstado() == null) {
			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			// Buscamos que el evento exista por nombre
			Boolean existeEvento = eventoDao.buscarEvento(evento.getId());

			if (!existeEvento) {
				throw new ObjectAlreadyExistException("El evento NO existe");
			} else {

				
				//Boolean existePublicador = publicadorDao.buscarPublicadorPorIdUsuario(evento.getInstitucion().getId());
				Boolean existeInstitucion = institucionDao.buscarInstitucion(evento.getInstitucion().getId());
				
				
				if (!existeInstitucion) {
					throw new ObjectNotFoundException("La institucion no existe");
				}else{
					
					// Aqui ya podemos MODIFICAR el evento
					try {
						boolean returnModificacion = eventoDao.modificarEvento(id, evento);

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
		
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.EventoService#eliminarEvento(int)
	 */
	@Override
	public void eliminarEvento(int id) {
		
		 logger.debug("--- EN ELIMINAR evento -----");
		  
		 if (id > 0) {
		  
			 Boolean existeEventoPorId = eventoDao.buscarEvento(id);
			 
			 if (!existeEventoPorId) { 
				 throw new ObjectNotFoundException("La evento no se puede eliminar, pues no existe"); 
			 } else {
				 
				 try { 
					 boolean returnInsercion = eventoDao.eliminarEvento(id);
					 
					 if (!returnInsercion) { 
						 throw new DAOException("Ocurrio un inconveniente al eliminar el registro en la base de datos"); 
					 }
			  
				 } catch (DAOException daoe) { 
					 daoe.printStackTrace(); 
					 throw new  DAOException("Ocurrio un inconveniente al eliminar el registro en la base de datos"); 
				}			 
			}
		 
		 } else { 
			 throw new ValueNotPermittedException("El id no esta permitido"); 
		 }
		 
		 logger.debug("--- SALIR DE ELIMINAR evento -----");
		
	}
	
	

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.EventoService#listarEventoBuscador(java.lang.String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarEventoBuscador(String search, int start, int length, int draw, int posicion,
			String direccion) {
		logger.debug("listarEventoBuscador -- listar eventos por el buscador");
		JSONRespuesta listaEventos = eventoDao.listarEventoBuscador(search, start, length, draw, posicion, direccion);
		return listaEventos;
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.EventoService#listarEventoFiltros(int, int, int, int, java.lang.String, int)
	 */
	@Override
	public JSONRespuesta listarEventoFiltros(int start, int length, int draw, int posicion, String direccion,
			int tipoEvento) {
		logger.debug("listarEventoFiltro -- listar eventos por filtro");
		JSONRespuesta listaEventos = eventoDao.listarEventoFiltros(start, length, draw, posicion, direccion, tipoEvento);
		return listaEventos;
	}


	@Override
	public JSONRespuesta listarEventosAdmin(String search, int start, int length, int draw, int posicion,
			String direccion) {
		logger.debug("listarEventosAdmin -- listar eventos por admin");
		JSONRespuesta listaEventos = eventoDao.listarEventosAdmin(search, start, length, draw, posicion, direccion);
		return listaEventos; 
	}


	@Override
	public List<Evento> listarEventosInstitucion(int idInstitucion) {
		logger.debug("listarEventosInstitucion -- listar ");
		List<Evento> listarEventosInstitucion = eventoDao.listarEventosInstitucion(idInstitucion);
		return listarEventosInstitucion; 
	}

	
	
	
}
