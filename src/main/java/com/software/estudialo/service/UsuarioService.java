/**
 * 
 */
package com.software.estudialo.service;

import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Usuario;

// TODO: Auto-generated Javadoc
/**
 * The Interface UsuarioService.
 *
 * @author LUIS
 */
public interface UsuarioService {
	
	/**
	 * Obtener usuario.
	 *
	 * @param id the id
	 * @return the usuario
	 */
	public Usuario  obtenerUsuario(int id);
	
	public void cambiarPassword(int idUsuario, String oldPassword, String newPassword);
	
	
	
	public Object  obtenerUsuarioPorUsername(String username);
	
	
	
	/**
	 * Agregar usuario.
	 *
	 * @param usuario the usuario
	 */
	public void agregarUsuario(Usuario usuario);
	
	public void signUpUsuario(Usuario usuario);
	

	/**
	 * Modificar usuario.
	 *
	 * @param id the id
	 * @param usuario the usuario
	 */
	public void  modificarUsuario(int id, Usuario usuario);
	
	public void  modificarUsuarioDatosPerfil(int id, Usuario usuario);
	
	
	
	/**
	 * Eliminar usuario.
	 *
	 * @param id the id
	 */
	public void  eliminarUsuario(int id);
	
	/**
	 * Listar usuarios.
	 *
	 * @param search the search
	 * @param start the start
	 * @param length the length
	 * @param draw the draw
	 * @param posicion the posicion
	 * @param direccion the direccion
	 * @return the JSON respuesta
	 */
	public JSONRespuesta listarUsuarios(String search, int start, int length, int draw, int posicion, String direccion);
	

}
