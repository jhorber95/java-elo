/**
 * 
 */
package com.software.estudialo.service;

import com.software.estudialo.entities.InscripcionOfertaInstitucion;
import com.software.estudialo.entities.JSONRespuesta;

// TODO: Auto-generated Javadoc
/**
 * The Interface InscripcionOfertaInstitucionService.
 *
 * @author LUIS
 */
public interface InscripcionOfertaInstitucionService {
	
	/**
	 * Obtenerinscripcion oferta institucion.
	 *
	 * @param id the id
	 * @return the inscripcion oferta institucion
	 */
	public InscripcionOfertaInstitucion  obtenerinscripcionOfertaInstitucion(int id);
	
	
	/**
	 * Inscribir estudiante.
	 *
	 * @param idOferta the id oferta
	 * @param idUsuario the id usuario
	 */
	public void inscribirEstudiante(int idOferta, int idUsuario);
	
	public void confirmarInscripcionEstudiante(int idOferta, int idUsuario);
	
	
	public void rechazarInscripcionEstudiante(int idOferta, int idUsuario);
	
	/**
	 * Listar inscripcion oferta instituciones.
	 *
	 * @param search the search
	 * @param start the start
	 * @param length the length
	 * @param draw the draw
	 * @param posicion the posicion
	 * @param direccion the direccion
	 * @return the JSON respuesta
	 */
	public JSONRespuesta listarInscripcionOfertaInstituciones(String search, int start, int length, int draw, int posicion, String direccion);
	
	
	/**
	 * Listar inscripcion oferta institucion.
	 *
	 * @param idUsuario the id usuario
	 * @param search the search
	 * @param start the start
	 * @param length the length
	 * @param draw the draw
	 * @param posicion the posicion
	 * @param direccion the direccion
	 * @return the JSON respuesta
	 */
	public JSONRespuesta listarInscripcionOfertaInstitucion(int idUsuario, String search, int start, int length, int draw, int posicion, String direccion);

	public JSONRespuesta listarPreInscripcionesOfertaFreelancer(int idInstitucion, String search, int start, int length,
			int draw, int posicion, String direccion);
	
	/*
	 * Eliminar inscripcion a una oferta independientemente si es una institucion o freelancer
	 * */
	
	
	
	
}
