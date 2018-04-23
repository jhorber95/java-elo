/**
 * 
 */
package com.software.estudialo.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.software.estudialo.dao.RolDao;
import com.software.estudialo.dao.UsuarioDao;
import com.software.estudialo.entities.Rol;
import com.software.estudialo.exception.DAOException;
import com.software.estudialo.exception.ObjectAlreadyExistException;
import com.software.estudialo.exception.ObjectNotFoundException;
import com.software.estudialo.exception.ValueNotPermittedException;
import com.software.estudialo.service.RolService;

/**
 * @author LUIS
 *
 */
@Service("rolService")
public class RolServiceImpl implements RolService {
	
	/** The logger. */
	private Logger logger = Logger.getLogger(RolServiceImpl.class);
	
	/** The categoriao dao. */
	@Autowired
	UsuarioDao usuarioDao;

	@Autowired
	RolDao rolDao;

	@Override
	public List<Rol> obtenerRoles() {
		logger.debug("obtenerRoles -- obteniendo roles");
		List<Rol> roles = rolDao.obtenerRoles();
		return roles;
	}

	@Override
	public void agregarRolUsuario(int idUsuario, int idRol) {
		
		logger.debug("--- EN AGREGAR rol Usuario -----");

		logger.debug("Iniciando Validaciones de adicion de usuario");		
		
		if (idUsuario <= 0 || idRol <= 0) {
			throw new ValueNotPermittedException("Proporcione los datos para poder adicionar rol al usuario");
		} else {

			// Buscamos que el usuario por id
			Boolean existeUsuario = usuarioDao.buscarUsuario(idUsuario);

			if (!existeUsuario) {
				throw new ObjectNotFoundException("No existe el usuario");
			} else {
				
				Boolean usuarioYaTieneRol = rolDao.buscarUsuarioRolExiste(idUsuario, idRol);
				
				if (usuarioYaTieneRol) {
					throw new ObjectAlreadyExistException("El usuario ya tiene el rol asignado");
				}else{
					
					// Aqui ya podemos guardar el usuario
					try {
						boolean returnInsercion = rolDao.agregarRolUsuario(idUsuario, idRol);

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
	
	
}
