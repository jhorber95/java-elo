/**
 * 
 */
package com.software.estudialo.service.impl;

import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.software.estudialo.dao.InstitucionDao;
import com.software.estudialo.dao.UsuarioDao;
import com.software.estudialo.entities.Institucion;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Usuario;
import com.software.estudialo.exception.DAOException;
import com.software.estudialo.exception.ObjectAlreadyExistException;
import com.software.estudialo.exception.ObjectNotFoundException;
import com.software.estudialo.exception.ValueNotPermittedException;
import com.software.estudialo.service.UsuarioService;
import com.software.estudialo.util.Encriptar;

// TODO: Auto-generated Javadoc
/**
 * The Class UsuarioServiceImpl.
 *
 * @author LUIS
 */
@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService{

	/** The logger. */
	private Logger logger = Logger.getLogger(UsuarioServiceImpl.class);
	
	/** The categoriao dao. */
	@Autowired
	UsuarioDao usuarioDao;	
	
	@Autowired
	InstitucionDao institucionDao;
	
	
	/* (non-Javadoc)
	 * @see com.software.estudialo.service.UsuarioService#obtenerUsuario(int)
	 */
	@Override
	public Usuario obtenerUsuario(int id) {
		logger.debug("obtenerUsuario -- obtener usuario");
		Usuario usuario = new Usuario();
		usuario = usuarioDao.obtenerUsuario(id);
		return usuario;
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.UsuarioService#agregarUsuario(com.software.estudialo.entities.Usuario)
	 */
	@Override
	public void agregarUsuario(Usuario usuario) {
		
		logger.debug("--- EN AGREGAR Usuario -----");

		logger.debug("Iniciando Validaciones de adicion de usuario");		
		
		if (usuario.getTipoIdentificacion() == null || usuario.getNombres() == null || usuario.getApellidos() == null || usuario.getMunicipio() == null 
				|| usuario.getTelefono() == null || usuario.getIdentificacion() == null || usuario.getGenero() == null || usuario.getEmail() == null || 
				usuario.getPassword() == null) {
			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			// Buscamos que el usuario exista por nombre
			Boolean existeUsuario = usuarioDao.buscarUsuario(usuario);

			if (existeUsuario) {
				throw new ObjectAlreadyExistException("Ya existe la usuario");
			} else {

				// Aqui ya podemos guardar el usuario
				try {
					boolean returnInsercion = usuarioDao.agregarUsuario(usuario);

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
	 * @see com.software.estudialo.service.UsuarioService#modificarUsuario(int, com.software.estudialo.entities.Usuario)
	 */
	@Override
	public void modificarUsuario(int id, Usuario usuario) {
		
		
		logger.debug("--- EN MODIFICAR usuario -----");

		logger.debug("Iniciando Validaciones de MODIFICAR de usuario");
		
		
		if (usuario.getId() == 0 || usuario.getTipoIdentificacion() == null || usuario.getNombres() == null || usuario.getApellidos() == null || 
				usuario.getMunicipio() == null || usuario.getTelefono() == null || usuario.getIdentificacion() == null || 
				usuario.getGenero() == null || usuario.getEmail() == null || usuario.getEstado() == null) { 
			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			// Buscamos que el usuario exista por nombre
			Boolean existeUsuario = usuarioDao.buscarUsuario(usuario.getId());

			if (!existeUsuario) {
				throw new ObjectAlreadyExistException("El usuario NO existe");
			} else {

				// Aqui ya podemos MODIFICAR el usuario
				try {
					boolean returnModificacion = usuarioDao.modificarUsuario(id, usuario);

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
	public void modificarUsuarioDatosPerfil(int id, Usuario usuario) {
		
		logger.debug("--- EN MODIFICAR datos usuario -----");

		logger.debug("Iniciando Validaciones de MODIFICAR de usuario");
		
		
		if (usuario.getId() == 0 || usuario.getTipoIdentificacion() == null || usuario.getNombres() == null || usuario.getApellidos() == null || 
				usuario.getMunicipio() == null || usuario.getTelefono() == null || usuario.getIdentificacion() == null || 
				usuario.getGenero() == null || usuario.getEmail() == null || usuario.getNivelEducativo() == null || usuario.getIntereses() == null) { 
			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			// Buscamos que el usuario exista por nombre
			Boolean existeUsuario = usuarioDao.buscarUsuario(usuario.getId());

			if (!existeUsuario) {
				throw new ObjectAlreadyExistException("El usuario NO existe");
			} else {
				
				Boolean existeEmail = usuarioDao.buscarExisteEmail(usuario.getEmail(), usuario.getId());
				
				if (existeEmail) {
					throw new ObjectAlreadyExistException("El email a modificar ya existe");
				}else{
					
					// Aqui ya podemos MODIFICAR el usuario
					try {
						boolean returnModificacion = usuarioDao.modificarUsuarioDatosPerfil(id, usuario);

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
	 * @see com.software.estudialo.service.UsuarioService#eliminarUsuario(int)
	 */
	@Override
	public void eliminarUsuario(int id) {
		

		 logger.debug("--- EN ELIMINAR usuario -----");
		  
		 if (id > 0) {
		  
			 Boolean existeUsuarioPorId = usuarioDao.buscarUsuario(id);
			 
			 if (!existeUsuarioPorId) { 
				 throw new ObjectNotFoundException("La usuario no se puede eliminar, pues no existe"); 
			 } else {
				 
				 try { 
					 boolean returnInsercion = usuarioDao.eliminarUsuario(id);
					 
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
		 
		 logger.debug("--- SALIR DE ELIMINAR usuario -----");
		
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.UsuarioService#listarUsuarios(java.lang.String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarUsuarios(String search, int start, int length, int draw, int posicion,
			String direccion) {
		logger.debug("listarUsuarios -- listar usuarios");
		JSONRespuesta listaUsuarios = usuarioDao.listarUsuarios(search, start, length, draw, posicion, direccion);
		return listaUsuarios;
	}

	@Override
	public Object obtenerUsuarioPorUsername(String username) {
		logger.debug("obtenerUsuarioPorUsername -- obtener usuario por username");
		
		/*
		 * Verificamos si el correo le pertene a un usuario o a una institucion
		 * 
		 */
		Boolean existeUsuario = usuarioDao.existenciaUsuarioPorUsername(username);
		Boolean existeInstitucion = institucionDao.existenciaInstitucionPorUsername(username);
		Object objeto = null;
		
		if (existeUsuario) {
			objeto = usuarioDao.obtenerUsuarioPorUsernameCompletoSinPassword(username);
		} else if (existeInstitucion) {
			objeto = institucionDao.obtenerInstitucionPorUsernameCompletoSinPassword(username);
		}
		
		return objeto;
	}

	@Override
	public void signUpUsuario(Usuario usuario) {
		
		logger.debug("--- EN signup Usuario -----");

		logger.debug("Iniciando Validaciones de signup de usuario");
		
		if (usuario.getNombres() == null || usuario.getApellidos() == null || usuario.getEmail() == null || usuario.getPassword() == null) {
			throw new ValueNotPermittedException("Digita los datos requeridos");
		} else {

			// Buscamos que el usuario exista por nombre
			Boolean existeUsuario = usuarioDao.buscarUsuarioParaSignUp(usuario);

			if (existeUsuario) {
				throw new ObjectAlreadyExistException("Ya existe el usuario");
			} else {

				// Aqui ya podemos guardar el usuario
				try {
					boolean returnInsercion = usuarioDao.signUpUsuario(usuario); 

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

	@Override
	public void cambiarPassword(int idUsuario, String oldPassword, String newPassword) {
		
		logger.debug("--- cambiarPassword -----");		
		
		if (idUsuario <= 0 || oldPassword.equalsIgnoreCase("") || newPassword.equalsIgnoreCase("") ) { 
			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			// Buscamos que el usuario exista por nombre
			Boolean existeUsuario = usuarioDao.buscarUsuario(idUsuario);

			if (!existeUsuario) {
				throw new ObjectNotFoundException("El usuario NO existe");
			} else {
				
				//Verificacion de clave antigua
				String oldPasswordEncripted = null;
				try {
					oldPasswordEncripted = Encriptar.toSHA256PasswordEncoder(oldPassword);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("Clave antigua encriptada: " + oldPasswordEncripted);
				Boolean oldPasswordCorrect = usuarioDao.validateOldPassword(idUsuario, oldPasswordEncripted); 
				
				if (!oldPasswordCorrect) {
					throw new ValueNotPermittedException("Las credenciales antiguas no son correctas");
				}else{
					
					String newPasswordEncripted = null;
					try {
						newPasswordEncripted = Encriptar.toSHA256PasswordEncoder(newPassword);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// Aqui ya podemos MODIFICAR el usuario
					try {
						boolean returnModificacion = usuarioDao.cambiarPassword(idUsuario, newPasswordEncripted);

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

	
		
}

	
	
	
	
	
	

