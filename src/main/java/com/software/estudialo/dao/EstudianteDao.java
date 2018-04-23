/**
 * 
 */
package com.software.estudialo.dao;

import java.util.List;

import com.software.estudialo.entities.Estudiante;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Oferta;

// TODO: Auto-generated Javadoc
/**
 * The Interface EstudianteDao.
 *
 * @author LUIS
 */
public interface EstudianteDao {
	
	/**
	 * Obtener estudiante.
	 *
	 * @param id the id
	 * @return the estudiante
	 */
	public Estudiante obtenerEstudiante(int id);
	
	/**
	 * Buscar estudiante por id usuario.
	 *
	 * @param idUsuario the id usuario
	 * @return the boolean
	 */
	public Boolean buscarEstudiantePorIdUsuario(int idUsuario);
	
	/**
	 * Obtener id estudiante por id usuario.
	 *
	 * @param idUsuario the id usuario
	 * @return the int
	 */
	public int obtenerIdEstudiantePorIdUsuario(int idUsuario);
	
	/**
	 * Listar inscripciones estudiante.
	 *
	 * @param idEstudiante the id estudiante
	 * @return the list
	 */
	public List<Object> listarInscripcionesEstudiante(int idEstudiante);

}
