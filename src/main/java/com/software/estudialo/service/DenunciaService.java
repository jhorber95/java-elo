/**
 * 
 */
package com.software.estudialo.service;

import com.software.estudialo.entities.Denuncia;
import com.software.estudialo.entities.JSONRespuesta;

/**
 * @author LUIS
 *
 */
public interface DenunciaService {
	
	public void agregarDenuncia(Denuncia denuncia);
	
	public void  modificarDenuncia(int id, Denuncia denuncia);
	
	public JSONRespuesta listarDenuncias(String search, int start, int length, int draw, int posicion, String direccion);
	
}
