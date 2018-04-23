/**
 * 
 */
package com.software.estudialo.dao;

import com.software.estudialo.entities.Freelancer;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Usuario;

// TODO: Auto-generated Javadoc
/**
 * The Interface FreelancerDao.
 *
 * @author LUIS
 */
public interface FreelancerDao {
	
	/**
	 * Obtener freelancer.
	 *
	 * @param id the id
	 * @return the freelancer
	 */
	public Freelancer obtenerFreelancer(int id);
	
	/**
	 * Agregar freelancer.
	 *
	 * @param id the id
	 * @return the boolean
	 */
	public Boolean agregarFreelancer(int id);
	
	
	public Boolean agregarFreelancerCompleto(Usuario usuario);
	
	
	/**
	 * Obtener id freelancer por id oferta.
	 *
	 * @param idOferta the id oferta
	 * @return the int
	 */
	public int obtenerIdFreelancerPorIdOferta(int idOferta);
	
	
	/**
	 * Obtener freelancer por oferta.
	 *
	 * @param idOferta the id oferta
	 * @return the freelancer
	 */
	public Freelancer obtenerFreelancerPorOferta(int idOferta);

	/**
	 * Buscar freelancer por id usuario.
	 *
	 * @param idUsuario the id usuario
	 * @return the boolean
	 */
	public Boolean buscarFreelancerPorIdUsuario(int idUsuario);
	
	/**
	 * Obtener id freelancer con id usuario.
	 *
	 * @param idUsuario the id usuario
	 * @return the int
	 */
	public int obtenerIdFreelancerConIdUsuario(int idUsuario);
	
	/**
	 * Listar freelancers.
	 *
	 * @param search the search
	 * @param start the start
	 * @param length the length
	 * @param draw the draw
	 * @param posicion the posicion
	 * @param direccion the direccion
	 * @return the JSON respuesta
	 */
	public JSONRespuesta listarFreelancers(String search, int start, int length, int draw, int posicion, String direccion);
	
	public JSONRespuesta listarFreelancersAdmin(String search, int start, int length, int draw, int posicion, String direccion);
}
