package com.software.estudialo.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.software.estudialo.dao.ResetPasswordDao;
import com.software.estudialo.dao.UsuarioDao;
import com.software.estudialo.entities.Usuario;
import com.software.estudialo.exception.ObjectAlreadyExistException;
import com.software.estudialo.service.ResetPasswordService;

@Service("ResetPasswordService")
public class ResetPasswordServiceImpl implements ResetPasswordService {
	

	/** The logger. */
	private Logger logger = Logger.getLogger(ResetPasswordServiceImpl.class);
	
	/** The Usuario dao. */
	@Autowired
	UsuarioDao usuarioDao;	
	
	@Autowired
	ResetPasswordDao resetPasswordDao;
	
	@Override
	public void resetPassword(String email) {
		
		logger.debug("----- Entrando resetPasswordService");
		Usuario user = usuarioDao.obtenerUsuarioPorUsernameCompletoSinPassword(email);
		
		if(user == null) {
			throw new ObjectAlreadyExistException("El usuario NO existe");
		}else {
			resetPasswordDao.createPasswordToken(user);
		}
		
	}

	@Override
	public boolean existeToken(int idUsuario, String token) {
		
		logger.debug("----- Service - Entrando verificar token");
		boolean isToken = resetPasswordDao.existeToken(idUsuario, token);
		
		if(isToken) {
			// resetPasswordDao.
			logger.debug("----- Saliendo token existe");
			return true;
		}else {
			throw new ObjectAlreadyExistException("No existe el token asignado ese usuario.");
			// return false;
		}
		
	}

}
