package com.software.estudialo.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.software.estudialo.dao.UsuarioDao;
import com.software.estudialo.entities.Usuario;
import com.software.estudialo.exception.ObjectAlreadyExistException;
import com.software.estudialo.service.ResetPassword;

public class ResetPasswordImpl implements ResetPassword {
	

	/** The logger. */
	private Logger logger = Logger.getLogger(ResetPasswordImpl.class);
	
	/** The Usuario dao. */
	@Autowired
	UsuarioDao usuarioDao;	

	@Override
	public void resetPassword(String email) {
		Usuario user = usuarioDao.obtenerUsuarioPorUsernameCompletoSinPassword(email);
		
		if(user == null) {
			throw new ObjectAlreadyExistException("El usuario NO existe");
		}else {
			
		}
		
	}

}
