/**
 * 
 */
package com.software.estudialo.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.software.estudialo.dao.FinanciacionDao;
import com.software.estudialo.entities.Financiacion;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.exception.DAOException;
import com.software.estudialo.exception.ObjectAlreadyExistException;
import com.software.estudialo.exception.ObjectNotFoundException;
import com.software.estudialo.exception.ValueNotPermittedException;
import com.software.estudialo.service.FinanciacionService;

// TODO: Auto-generated Javadoc
/**
 * The Class FinanciacionServiceImpl.
 *
 * @author LUIS
 */
@Service("financiacionService")
public class FinanciacionServiceImpl implements FinanciacionService{
	
	
	/** The logger. */
	private Logger logger = Logger.getLogger(FinanciacionServiceImpl.class);
	
	/** The categoriao dao. */
	@Autowired
	FinanciacionDao financiacionDao;

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.FinanciacionService#obtenerFinanciacion(int)
	 */
	@Override
	public Financiacion obtenerFinanciacion(int id) {
		logger.debug("obtenerFinanciacion -- obtener financiacion");
		Financiacion financiacion = new Financiacion();
		financiacion = financiacionDao.obtenerFinanciacion(id);
		return financiacion;
	}
	
	
	/* (non-Javadoc)
	 * @see com.software.estudialo.service.FinanciacionService#agregarFinanciacion(com.software.estudialo.entities.Financiacion)
	 */
	@Override
	public void agregarFinanciacion(Financiacion financiacion) {
		
		logger.debug("--- EN AGREGAR Financiacion -----");

		logger.debug("Iniciando Validaciones de adicion de financiacion");

		if (financiacion.getInformacion() == null || financiacion.getInstitucion() == null || financiacion.getEstado() == null) {
			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			// Buscamos que el financiacion exista por nombre y coordenada
			Boolean existeFinanciacion = financiacionDao.buscarFinanciacion(financiacion);

			if (existeFinanciacion) {
				throw new ObjectAlreadyExistException("Ya existe la financiacion");
			} else {

				// Aqui ya podemos guardar el financiacion
				try {
					boolean returnInsercion = financiacionDao.agregarFinanciacion(financiacion);

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
	 * @see com.software.estudialo.service.FinanciacionService#modificarFinanciacion(int, com.software.estudialo.entities.Financiacion)
	 */
	@Override
	public void modificarFinanciacion(int id, Financiacion financiacion) {
		
		logger.debug("--- EN MODIFICAR financiacion -----");

		logger.debug("Iniciando Validaciones de MODIFICAR de financiacion");

		if (financiacion.getId() == 0 || financiacion.getInformacion() == null || financiacion.getInstitucion() == null || financiacion.getEstado() == null) {
			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			// Buscamos que el financiacion exista por nombre
			Boolean existeFinanciacion = financiacionDao.buscarFinanciacion(financiacion.getId());

			if (!existeFinanciacion) {
				throw new ObjectAlreadyExistException("El financiacion NO existe");
			} else {

				// Aqui ya podemos MODIFICAR el financiacion
				try {
					boolean returnModificacion = financiacionDao.modificarFinanciacion(id, financiacion);

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
	 * @see com.software.estudialo.service.FinanciacionService#eliminarFinanciacion(int)
	 */
	@Override
	public void eliminarFinanciacion(int id) {
		
		 logger.debug("--- EN ELIMINAR financiacion -----");
		  
		 if (id > 0) {
		  
			 Boolean existeFinanciacionPorId = financiacionDao.buscarFinanciacion(id);
			 
			 if (!existeFinanciacionPorId) { 
				 throw new ObjectNotFoundException("La financiacion no se puede eliminar, pues no existe"); 
			 } else {
				 
				 try { 
					 boolean returnInsercion = financiacionDao.eliminarFinanciacion(id);
					 
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
		 
		 logger.debug("--- SALIR DE ELIMINAR financiacion -----");
		
	}	
	
	

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.FinanciacionService#listarFinanciacionBuscador(java.lang.String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarFinanciacionBuscador(String search, int start, int length, int draw, int posicion,
			String direccion) {
		logger.debug("listarFinanciacionBuscador -- listar financiacions por el buscador");
		JSONRespuesta listaFinanciacion = financiacionDao.listarFinanciacionBuscador(search, start, length, draw, posicion, direccion);
		return listaFinanciacion;
	}


	/* (non-Javadoc)
	 * @see com.software.estudialo.service.FinanciacionService#listarFinanciaciones(java.lang.String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarFinanciaciones(String search, int start, int length, int draw, int posicion,
			String direccion) {
		logger.debug("listarFinanciaciones -- listar financiaciones");
		JSONRespuesta listaFinanciaciones = financiacionDao.listarFinanciaciones(search, start, length, draw, posicion, direccion);
		return listaFinanciaciones; 
	}


	@Override
	public Financiacion obtenerFinanciacionPorInstitucion(int idInstitucion) {
		logger.debug("obtenerFinanciacionPorInstitucion -- obtener financiacion por institucion");
		Financiacion financiacion = new Financiacion();
		financiacion = financiacionDao.obtenerFinanciacionPorInstitucion(idInstitucion);
		return financiacion;
	}


	@Override
	public JSONRespuesta listarFinanciacionesAdmin(String search, int start, int length, int draw, int posicion,
			String direccion) {
		logger.debug("listarFinanciacionesAdmin -- listar financiaciones por el admin");
		JSONRespuesta listaFinanciaciones = financiacionDao.listarFinanciacionesAdmin(search, start, length, draw, posicion, direccion);
		return listaFinanciaciones; 
	}

	
	
	

}
