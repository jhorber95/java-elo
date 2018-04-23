/**
 * 
 */
package com.software.estudialo.service;
import com.software.estudialo.entities.InscripcionOfertaFreelancer;
import com.software.estudialo.entities.JSONRespuesta;

// TODO: Auto-generated Javadoc
/**
 * The Interface InscripcionOfertaFreelancerService.
 *
 * @author LUIS
 */
public interface InscripcionOfertaFreelancerService {
	
	
	/**
	 * Obtenerinscripcion oferta freelancer.
	 *
	 * @param id the id
	 * @return the inscripcion oferta freelancer
	 */
	public InscripcionOfertaFreelancer  obtenerinscripcionOfertaFreelancer(int id);
	
	public void confirmarInscripcionEstudiante(int idOferta, int idUsuario);
	
	
	public void rechazarInscripcionEstudiante(int idOferta, int idUsuario);
	
	
	/**
	 * Inscribir estudiante.
	 *
	 * @param idOferta the id oferta
	 * @param idUsuario the id usuario
	 */
	public void inscribirEstudiante(int idOferta, int idUsuario);
	
	
	/**
	 * Listar inscripcion oferta freelancers.
	 *
	 * @param search the search
	 * @param start the start
	 * @param length the length
	 * @param draw the draw
	 * @param posicion the posicion
	 * @param direccion the direccion
	 * @return the JSON respuesta
	 */
	public JSONRespuesta listarInscripcionOfertaFreelancers(String search, int start, int length, int draw, int posicion, String direccion);
	
	
	/**
	 * Listar inscripcion oferta freelancer.
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
	public JSONRespuesta listarInscripcionOfertaFreelancer(int idUsuario, String search, int start, int length, int draw, int posicion, String direccion);
	

	public JSONRespuesta listarPreInscripcionesOfertaFreelancer(int idUsuario, String search, int start, int length, int draw, int posicion, String direccion);
	
	
}
