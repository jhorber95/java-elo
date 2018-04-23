/**
 * 
 */
package com.software.estudialo.service;

import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Usuario;
import com.software.estudialo.entities.Freelancer;

// TODO: Auto-generated Javadoc
/**
 * The Interface FreelancerService.
 *
 * @author LUIS
 */
public interface FreelancerService {
	
	
	/**
	 * Obtener freelancer.
	 *
	 * @param id the id
	 * @return the freelancer
	 */
	public Freelancer  obtenerFreelancer(int id);
	
	
	/**
	 * Agregar freelancer.
	 *
	 * @param id the id
	 */
	public void agregarFreelancer(int id);
	
	public void agregarFreelancerCompleto(Usuario usuario);
	
	
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
