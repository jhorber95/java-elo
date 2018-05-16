package com.software.estudialo.dao;

import com.software.estudialo.entities.Usuario;

public interface ResetPasswordDao {
	
	public void createPasswordToken(Usuario usuario);
	
	public boolean existeToken(int idUsuario, String token);
	
	
}
