package com.software.estudialo.service;

public interface ResetPasswordService {
	
	public void resetPassword(String email);
	
	public boolean existeToken (int idUsuario, String token);
	
}
