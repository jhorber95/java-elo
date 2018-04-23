/**
 * 
 */
package com.software.estudialo.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.software.estudialo.dao.DenunciaDao;
import com.software.estudialo.dao.EventoDao;
import com.software.estudialo.dao.UsuarioDao;
import com.software.estudialo.entities.Denuncia;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.exception.DAOException;
import com.software.estudialo.exception.ObjectAlreadyExistException;
import com.software.estudialo.exception.ObjectNotFoundException;
import com.software.estudialo.exception.ValueNotPermittedException;
import com.software.estudialo.service.DenunciaService;

/**
 * @author LUIS
 *
 */
@Service("denunciaService")
public class DenunciaServiceImpl implements DenunciaService {

	private Logger logger = Logger.getLogger(DenunciaServiceImpl.class);

	@Autowired
	DenunciaDao denunciaDao;

	@Autowired
	UsuarioDao usuarioDao;

	@Override
	public void agregarDenuncia(Denuncia denuncia) {

		logger.debug("--- EN AGREGAR Evento -----");

		logger.debug("Iniciando Validaciones de adicion de denuncia");

		if (denuncia.getMensaje() == null || denuncia.getUsuario() == null) {
			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			// Buscamos que el evento exista
			Boolean existeUsuario = usuarioDao.buscarUsuario(denuncia.getUsuario().getId());

			if (!existeUsuario) {
				throw new ObjectNotFoundException("El usuario no existe!");
			} else {

				try {
					denunciaDao.agregarDenuncia(denuncia);

				} catch (DAOException daoe) {
					daoe.printStackTrace();
					throw new DAOException("Ocurrio un inconveniente al insertar el registro en la base de datos");
				}

			}

		}

	}

	@Override
	public void modificarDenuncia(int id, Denuncia denuncia) {

		logger.debug("--- EN MODIFICAR denuncia -----");

		logger.debug("Iniciando Validaciones de MODIFICAR de denuncia");

		if (denuncia.getId() == 0 || denuncia.getEstado() == null) {
			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			Boolean existeDenuncia = denunciaDao.buscarDenuncia(denuncia.getId());

			if (!existeDenuncia) {
				throw new ObjectNotFoundException("La denuncia no existe!");
			} else {

				try {
					boolean returnModificacion = denunciaDao.modificarDenuncia(id, denuncia);

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
	public JSONRespuesta listarDenuncias(String search, int start, int length, int draw, int posicion,
			String direccion) {
		logger.debug("listarDenuncias -- listar denuncias");
		JSONRespuesta listaDenuncias = denunciaDao.listarDenuncias(search, start, length, draw, posicion, direccion);
		return listaDenuncias;
	}

}
