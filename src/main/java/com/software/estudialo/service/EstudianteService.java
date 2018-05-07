/**
 * 
 */
package com.software.estudialo.service;

import java.util.List;
import com.software.estudialo.entities.Oferta;

// TODO: Auto-generated Javadoc
/**
 * The Interface EstudianteService.
 *
 * @author LUIS
 */
public interface EstudianteService {
	
	/**
	 * Listar inscripciones estudiante.
	 *
	 * @param idUsuario the id usuario
	 * @return the list
	 */
	public List<Object> listarInscripcionesEstudiante(int idUsuario);
	
	public void eliminarInscripcion(int idInscripcion,  int idTipoOfrece);

}
