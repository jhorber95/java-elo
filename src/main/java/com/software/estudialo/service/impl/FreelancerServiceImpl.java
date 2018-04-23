/**
 * 
 */
package com.software.estudialo.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.software.estudialo.dao.FreelancerDao;
import com.software.estudialo.dao.UsuarioDao;
import com.software.estudialo.entities.Freelancer;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Usuario;
import com.software.estudialo.exception.DAOException;
import com.software.estudialo.exception.ObjectAlreadyExistException;
import com.software.estudialo.exception.ObjectNotFoundException;
import com.software.estudialo.exception.ValueNotPermittedException;
import com.software.estudialo.service.FreelancerService;

// TODO: Auto-generated Javadoc
/**
 * The Class FreelancerServiceImpl.
 *
 * @author LUIS
 */
@Service("freelancerService")
public class FreelancerServiceImpl implements FreelancerService{
	
	/** The logger. */
	private Logger logger = Logger.getLogger(FreelancerServiceImpl.class);
	
	/** The categoriao dao. */
	@Autowired
	FreelancerDao freelancerDao;
	
	/** The usuario dao. */
	@Autowired
	UsuarioDao usuarioDao;
	

	/* (non-Javadoc)
	 * @see com.software.estudialo.service.FreelancerService#obtenerFreelancer(int)
	 */
	@Override
	public Freelancer obtenerFreelancer(int id) {
		logger.debug("obtenerFreelancer -- obtener freelancer");
		int idFreelancer = freelancerDao.obtenerIdFreelancerConIdUsuario(id);
		Freelancer freelancer = new Freelancer();
		freelancer = freelancerDao.obtenerFreelancer(idFreelancer);
		return freelancer;
	}
	
	
	/* (non-Javadoc)
	 * @see com.software.estudialo.service.FreelancerService#agregarFreelancer(int)
	 */
	@Override
	public void agregarFreelancer(int id) {
		
		logger.debug("--- EN AGREGAR Freelancer -----");

		logger.debug("Iniciando Validaciones de adicion de freelancer");

		if (id <= 0) {
			throw new ValueNotPermittedException("El id de usuario no existe");
		} else {
			
			Boolean existeUsuario = usuarioDao.buscarUsuario(id);
						
			if (!existeUsuario) {
				throw new ObjectAlreadyExistException("El usuario No existe");
			} else {
				
				//Buscamos si el usuario ya existe como freelancer
				Boolean existeFreelancer = freelancerDao.buscarFreelancerPorIdUsuario(id);
					
				if (existeFreelancer) {
					throw new ObjectAlreadyExistException("El usuario Ya es un freelancer");
				}else{					
					// Aqui ya podemos guardar el freelancer
					try {
						boolean returnInsercion = freelancerDao.agregarFreelancer(id);

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
	
	
	@Override
	public void agregarFreelancerCompleto(Usuario usuario) {
		
		logger.debug("--- EN AGREGAR Freelancer completo desde parte publica -----");
		
		logger.debug("Validando valores vacios");
		if (usuario.getNombres() == null || usuario.getApellidos() == null  || usuario.getEmail() == null || usuario.getPassword() == null) {
			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			// Buscamos que el usuario exista
			Boolean existeUsuario = usuarioDao.buscarUsuario(usuario);

			if (existeUsuario) {
				throw new ObjectAlreadyExistException("Ya existe el usuario");
			} else {

				// Aqui ya podemos guardar el usuario
				try {
					boolean returnInsercion = freelancerDao.agregarFreelancerCompleto(usuario);

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
	 * @see com.software.estudialo.service.FreelancerService#listarFreelancers(java.lang.String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarFreelancers(String search, int start, int length, int draw, int posicion, String direccion) {
		logger.debug("listarFreelancer -- listar freelancers");
		JSONRespuesta listaFreelancer = freelancerDao.listarFreelancers(search, start, length, draw, posicion, direccion);
		return listaFreelancer;
	}


	@Override
	public JSONRespuesta listarFreelancersAdmin(String search, int start, int length, int draw, int posicion,
			String direccion) {
		logger.debug("listarFreelancersAdmin -- listar freelancers");
		JSONRespuesta listaFreelancer = freelancerDao.listarFreelancersAdmin(search, start, length, draw, posicion, direccion);
		return listaFreelancer;
	}


	
	

}
