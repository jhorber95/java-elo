/**
 * 
 */
package com.software.estudialo.dao;

import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Publicador;

// TODO: Auto-generated Javadoc
/**
 * The Interface PublicadorDao.
 *
 * @author LUIS
 */
public interface PublicadorDao {
	
	
	/**
	 * Obtener publicador.
	 *
	 * @param id the id
	 * @return the publicador
	 */
	public Publicador obtenerPublicador(int id);
	
	/**
	 * Obtener id publicador con id usuario.
	 *
	 * @param idUsuario the id usuario
	 * @return the int
	 */
	public int obtenerIdPublicadorConIdUsuario(int idUsuario);
	
	/**
	 * Buscar publicador por id usuario.
	 *
	 * @param idUsuario the id usuario
	 * @return the boolean
	 */
	public Boolean buscarPublicadorPorIdUsuario(int idUsuario);
	
	/**
	 * Buscar publicador por id usuario institucion.
	 *
	 * @param idUsuario the id usuario
	 * @param idInstitucion the id institucion
	 * @return the boolean
	 */
	public Boolean buscarPublicadorPorIdUsuarioInstitucion(int idUsuario, int idInstitucion);
	
	/**
	 * Agregar publicador.
	 *
	 * @param idUsuario the id usuario
	 * @param idInstitucion the id institucion
	 * @return the boolean
	 */
	public Boolean agregarPublicador(int idUsuario, int idInstitucion);
	
	/**
	 * Buscar usuario publicador.
	 *
	 * @param idUsuario the id usuario
	 * @return the boolean
	 */
	public Boolean buscarUsuarioPublicador(int idUsuario);
	
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
	public JSONRespuesta listarPublicadores(String search, int start, int length, int draw, int posicion, String direccion);

}
