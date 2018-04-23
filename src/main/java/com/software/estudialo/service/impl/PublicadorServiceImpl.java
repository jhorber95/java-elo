/**
 * 
 */
package com.software.estudialo.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.software.estudialo.dao.InstitucionDao;
import com.software.estudialo.dao.PublicadorDao;
import com.software.estudialo.dao.UsuarioDao;
import com.software.estudialo.entities.Publicador;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Publicador;
import com.software.estudialo.exception.DAOException;
import com.software.estudialo.exception.ObjectAlreadyExistException;
import com.software.estudialo.exception.ValueNotPermittedException;
import com.software.estudialo.service.PublicadorService;

// TODO: Auto-generated Javadoc
/**
 * The Class PublicadorServiceImpl.
 *
 * @author LUIS
 */
@Service("publicadorService")
public class PublicadorServiceImpl implements PublicadorService{
	
	/** The logger. */
	private Logger logger = Logger.getLogger(PublicadorServiceImpl.class);
	
	/** The categoriao dao. */
	@Autowired
	PublicadorDao publicadorDao;
	
	/** The usuario dao. */
	@Autowired
	UsuarioDao usuarioDao;
	
	/** The institucion dao. */
	@Autowired
	InstitucionDao institucionDao;

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.PublicadorService#obtenerPublicador(int)
	 */
	@Override
	public Publicador obtenerPublicador(int id) {
		logger.debug("obtenerPublicador -- obtener publicador");
		Publicador publicador = new Publicador();
		publicador = publicadorDao.obtenerPublicador(id);
		return publicador;
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.PublicadorService#agregarPublicador(int, int)
	 */
	@Override
	public void agregarPublicador(int idUsuario, int idInstitucion) {


		logger.debug("--- EN AGREGAR Publicador -----");

		logger.debug("Iniciando Validaciones de adicion de publicador");

		if (idUsuario <= 0 || idInstitucion <= 0) {
			throw new ValueNotPermittedException("El id de usuario o Institucion no existe");
		} else {
			
			Boolean existeUsuario = usuarioDao.buscarUsuario(idUsuario);
			Boolean existeInstitucion = institucionDao.buscarInstitucion(idInstitucion);
						
			if (!existeUsuario || !existeInstitucion) {
				throw new ObjectAlreadyExistException("El usuario o institucion No existen");
			} else {			
				
				//Buscamos si el usuario ya existe como publicador
				Boolean existePublicadorAsignadoInstitucion = publicadorDao.buscarPublicadorPorIdUsuarioInstitucion(idUsuario, idInstitucion);
					
				if (existePublicadorAsignadoInstitucion) {
					throw new ObjectAlreadyExistException("El usuario Ya es un publicador de dicha institucion");
				}else{					
					// Aqui ya podemos guardar el publicador
					try {
						boolean returnInsercion = publicadorDao.agregarPublicador(idUsuario, idInstitucion);

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
		
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.PublicadorService#listarPublicadores(java.lang.String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarPublicadores(String search, int start, int length, int draw, int posicion,
			String direccion) {
		logger.debug("listarPublicadores -- listar publicadores");
		JSONRespuesta listaPublicadores = publicadorDao.listarPublicadores(search, start, length, draw, posicion, direccion);
		return listaPublicadores;
	}
	

}
