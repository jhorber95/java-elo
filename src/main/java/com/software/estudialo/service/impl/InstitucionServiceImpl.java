/**
 * 
 */
package com.software.estudialo.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.software.estudialo.dao.InstitucionDao;
import com.software.estudialo.entities.Institucion;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.exception.DAOException;
import com.software.estudialo.exception.ObjectAlreadyExistException;
import com.software.estudialo.exception.ObjectNotFoundException;
import com.software.estudialo.exception.ValueNotPermittedException;
import com.software.estudialo.service.InstitucionService;

// TODO: Auto-generated Javadoc
/**
 * The Class InstitucionServiceImpl.
 *
 * @author LUIS
 */
@Service("institucionService")
public class InstitucionServiceImpl implements InstitucionService{
	
	/** The logger. */
	private Logger logger = Logger.getLogger(InstitucionServiceImpl.class);
	
	/** The categoriao dao. */
	@Autowired
	InstitucionDao institucionDao;

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.InstitucionService#obtenerInstitucion(int)
	 */
	@Override
	public Institucion obtenerInstitucion(int id) {
		logger.debug("obtenerInstitucion -- obtener institucion");
		Institucion institucion = new Institucion();
		institucion = institucionDao.obtenerInstitucion(id);
		return institucion;
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.InstitucionService#agregarInstitucion(com.software.estudialo.entities.Institucion)
	 */
	@Override
	public void agregarInstitucion(Institucion institucion) { 
		
		logger.debug("--- EN AGREGAR Institucion -----");

		logger.debug("Iniciando Validaciones de adicion de institucion");

		if (institucion.getNombre() == null || institucion.getNit() == null || institucion.getLatitud() == null || institucion.getLongitud() == null 
				|| institucion.getDireccion() == null || institucion.getTelefono() == null || institucion.getUrl() == null || institucion.getDescripcion() == null 
				|| institucion.getEmail() == null || institucion.getEmail() == null || institucion.getPassword() == null || institucion.getTipoInstitucion() == null) {
			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			// Buscamos que el institucion exista por nombre y coordenada
			//Boolean existeInstitucion = institucionDao.buscarInstitucion(institucion);
			
			Boolean existeInstitucion = false;
			
			
			if (existeInstitucion) {
				throw new ObjectAlreadyExistException("Ya existe la institucion");
			} else {

				// Aqui ya podemos guardar el institucion
				try {
					boolean returnInsercion = institucionDao.agregarInstitucion(institucion);

					if (!returnInsercion) {
						throw new DAOException("Ocurrio un inconveniente al insertar el registro en la base de datos");
					}

				} catch (DAOException daoe) {
					daoe.printStackTrace();
					throw new DAOException("Ocurrio un inconveniente al insertar el registro en la base de datos");
				}

			}

		}		
		
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.InstitucionService#modificarInstitucion(int, com.software.estudialo.entities.Institucion)
	 */
	@Override
	public void modificarInstitucionAdmin(int id, Institucion institucion) {
		
		logger.debug("--- EN MODIFICAR institucion por el administrador -----");

		logger.debug("Iniciando Validaciones de MODIFICAR de institucion");

		if (institucion.getId() == 0 || institucion.getNombre() == null || institucion.getNit() == null || institucion.getLatitud() == null || institucion.getLongitud() == null 
				|| institucion.getDireccion() == null || institucion.getTelefono() == null || institucion.getUrl() == null || institucion.getDescripcion() == null 
				|| institucion.getEmail() == null || institucion.getEstado() == null || institucion.getTipoInstitucion() == null) {
			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			// Buscamos que el institucion exista por nombre
			Boolean existeInstitucion = institucionDao.buscarInstitucion(institucion.getId());

			if (!existeInstitucion) {
				throw new ObjectAlreadyExistException("La institucion NO existe");
			} else {

				// Aqui ya podemos MODIFICAR el institucion
				try {
					boolean returnModificacion = institucionDao.modificarInstitucionAdmin(id, institucion);

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
	public void modificarInstitucion(int id, Institucion institucion) {
		
		logger.debug("--- EN MODIFICAR institucion desde la misma institucion-----");

		logger.debug("Iniciando Validaciones de MODIFICAR de institucion");

		if (institucion.getId() == 0 || institucion.getNombre() == null || institucion.getNit() == null || institucion.getLatitud() == null || institucion.getLongitud() == null 
				|| institucion.getDireccion() == null || institucion.getTelefono() == null || institucion.getUrl() == null || institucion.getDescripcion() == null 
				|| institucion.getEmail() == null || institucion.getTipoInstitucion() == null) {
			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			// Buscamos que el institucion exista por nombre
			Boolean existeInstitucion = institucionDao.buscarInstitucion(institucion.getId());

			if (!existeInstitucion) {
				throw new ObjectAlreadyExistException("El institucion NO existe");
			} else {

				// Aqui ya podemos MODIFICAR el institucion
				try {
					boolean returnModificacion = institucionDao.modificarInstitucion(id, institucion);

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

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.InstitucionService#eliminarInstitucion(int)
	 */
	@Override
	public void eliminarInstitucion(int id) {
		
		 logger.debug("--- EN ELIMINAR institucion -----");
		  
		 if (id > 0) {
		  
			 Boolean existeInstitucionPorId = institucionDao.buscarInstitucion(id);
			 
			 if (!existeInstitucionPorId) { 
				 throw new ObjectNotFoundException("La institucion no se puede eliminar, pues no existe"); 
			 } else {
				 
				 try { 
					 boolean returnInsercion = institucionDao.eliminarInstitucion(id);
					 
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
		 
		 logger.debug("--- SALIR DE ELIMINAR institucion -----");
		
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.InstitucionService#listarInstituciones(java.lang.String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarInstituciones(String search, int start, int length, int draw, int posicion,
			String direccion) {
		
		logger.debug("listarInstituciones -- listar instituciones");
		JSONRespuesta listaInstituciones = institucionDao.listarInstituciones(search, start, length, draw, posicion, direccion);
		return listaInstituciones; 
		
	}

	@Override
	public JSONRespuesta listarInstitucionesFinancieras(String search, int start, int length, int draw, int posicion,
			String direccion) {
		logger.debug("listarInstitucionesFinancieras --  listarInstitucionesFinancieras");
		JSONRespuesta listaInstituciones = institucionDao.listarInstitucionesFinancieras(search, start, length, draw, posicion, direccion);
		return listaInstituciones; 	
	}

	@Override
	public JSONRespuesta listarInstitucionesAdmin(String search, int start, int length, int draw, int posicion,
			String direccion) {
		logger.debug("listarInstituciones Admin -- listar instituciones por el admin");
		JSONRespuesta listaInstituciones = institucionDao.listarInstitucionesAdmin(search, start, length, draw, posicion, direccion); 
		return listaInstituciones; 
	}
	
}
