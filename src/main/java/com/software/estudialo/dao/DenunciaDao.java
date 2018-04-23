/**
 * 
 */
package com.software.estudialo.dao;

import com.software.estudialo.entities.Denuncia;
import com.software.estudialo.entities.Evento;
import com.software.estudialo.entities.JSONRespuesta;

/**
 * @author LUIS
 *
 */
public interface DenunciaDao {
	
	public Denuncia obtenerDenuncia(int id);
	
	public Boolean agregarDenuncia(Denuncia denuncia);
	
	public Boolean modificarDenuncia(int id, Denuncia denuncia);
	
	public Boolean buscarDenuncia(int idDenuncia);
	
	public JSONRespuesta listarDenuncias(String search, int start, int length, int draw, int posicion, String direccion);

}
