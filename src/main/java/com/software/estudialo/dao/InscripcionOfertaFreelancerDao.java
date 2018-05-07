/**
 * 
 */
package com.software.estudialo.dao;

import java.util.List;

import com.software.estudialo.entities.InscripcionOfertaFreelancer;
import com.software.estudialo.entities.JSONRespuesta;

// TODO: Auto-generated Javadoc
/**
 * The Interface InscripcionOfertaFreelancerDao.
 *
 * @author LUIS
 */
public interface InscripcionOfertaFreelancerDao {
	
	
	/**
	 * Obtenerinscripcion oferta freelancer.
	 *
	 * @param id the id
	 * @return the inscripcion oferta freelancer
	 */
	public InscripcionOfertaFreelancer  obtenerinscripcionOfertaFreelancer(int id);
	
	
	/**
	 * Inscribir estudiante.
	 *
	 * @param idOfertaFreelancer the id oferta freelancer
	 * @param idEstudiante the id estudiante
	 * @return the boolean
	 */
	public Boolean inscribirEstudiante(int idOfertaFreelancer, int idEstudiante);
	
	public Boolean confirmarInscripcionEstudiante(int idInscripcionOfertaFreelancer);
	
	public Boolean rechazarInscripcionEstudiante(int idInscripcionOfertaFreelancer);
	
	
	/**
	 * Obtener id oferta freelancer.
	 *
	 * @param idOferta the id oferta
	 * @param idFreelancer the id freelancer
	 * @return the int
	 */
	public int obtenerIdOfertaFreelancer(int idOferta, int idFreelancer);
	
	
	public int obtenerIdInscripcionOfertaFreelancer(int idOferta, int idEstudiante);
	
	
	
	/**
	 * Buscar inscripcion oferta freelancer.
	 *
	 * @param idEstudiante the id estudiante
	 * @param idOfertaFreelancer the id oferta freelancer
	 * @return the boolean
	 */
	public Boolean buscarInscripcionOfertaFreelancer(int idEstudiante, int idOfertaFreelancer);
	
	
	public Boolean buscarInscripcionOfertaFreelancerConEstudiante(int idOferta, int idEstudiante);
	
	
	
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
	 * @param idFreelancer the id freelancer
	 * @param search the search
	 * @param start the start
	 * @param length the length
	 * @param draw the draw
	 * @param posicion the posicion
	 * @param direccion the direccion
	 * @return the JSON respuesta
	 */
	public JSONRespuesta listarInscripcionOfertaFreelancer(int idFreelancer, String search, int start, int length, int draw, int posicion, String direccion);
	
	
	public JSONRespuesta listarPreInscripcionesOfertaFreelancer(int idFreelancer, String search, int start, int length, int draw, int posicion, String direccion);
	
	
	
	public List<InscripcionOfertaFreelancer> obtenerInscripcionesOfertasFreelancerDeEstudiante(int idEstudiante);
	
	public boolean eliminarInscripcionFreelancer(int idInscripcion);
	
	

}
