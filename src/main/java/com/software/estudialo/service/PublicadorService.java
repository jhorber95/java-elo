/**
 * 
 */
package com.software.estudialo.service;

import com.software.estudialo.entities.Publicador;
import com.software.estudialo.entities.JSONRespuesta;

// TODO: Auto-generated Javadoc
/**
 * The Interface PublicadorService.
 *
 * @author LUIS
 */
public interface PublicadorService {

	/**
	 * Obtener publicador.
	 *
	 * @param id the id
	 * @return the publicador
	 */
	public Publicador obtenerPublicador(int id);

	/**
	 * Agregar publicador.
	 *
	 * @param idUsuario the id usuario
	 * @param idInstitucion the id institucion
	 */
	public void agregarPublicador(int idUsuario, int idInstitucion);

	/**
	 * Listar publicadores.
	 *
	 * @param search the search
	 * @param start the start
	 * @param length the length
	 * @param draw the draw
	 * @param posicion the posicion
	 * @param direccion the direccion
	 * @return the JSON respuesta
	 */
	public JSONRespuesta listarPublicadores(String search, int start, int length, int draw, int posicion,
			String direccion);

}
