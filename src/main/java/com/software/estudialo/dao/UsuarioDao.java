/**
 * 
 */
package com.software.estudialo.dao;

import com.software.estudialo.entities.Institucion;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Usuario;

// TODO: Auto-generated Javadoc
/**
 * The Interface UsuarioDao.
 *
 * @author LUIS
 */
public interface UsuarioDao {
	
	
	public Boolean cambiarPassword(int idUsuario, String newPassword);
	
	public Boolean validateOldPassword(int idUsuario, String oldPassword);
	
	
	/**
	 * Agregar usuario.
	 *
	 * @param usuario the usuario
	 * @return true, if successful
	 */
	public boolean agregarUsuario(Usuario usuario);
	
	public Boolean usuarioActivo(String username);
	
	public boolean signUpUsuario(Usuario usuario);
	
	public Usuario  obtenerUsuarioPorUsernameCompletoSinPassword(String username);
	
	/*
	 * Busca el usuario por existencia por username
	 * 
	 */
	public Boolean buscarExisteUsuarioPorUsername(String username);
	
	public Boolean buscarExisteEmail(String email, int idUsuario);
	
	/**
	 * Modificar usuario.
	 *
	 * @param id the id
	 * @param usuario the usuario
	 * @return true, if successful
	 */
	public boolean modificarUsuario(int id, Usuario usuario);
	
	
	public boolean modificarUsuarioDatosPerfil(int id, Usuario usuario);
	
	
	/**
	 * Eliminar usuario.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	public boolean eliminarUsuario(int id);
	
	
	/**
	 * Buscar usuario.
	 *
	 * @param id the id
	 * @return the boolean
	 */
	public Boolean buscarUsuario(int id);
	
	public Usuario buscarUsuarioPorUsername(String username);
	
	
	/**
	 * Buscar usuario.
	 *
	 * @param usuario the usuario
	 * @return the boolean
	 */
	public Boolean buscarUsuario(Usuario usuario);
	
	public Boolean buscarUsuarioParaSignUp(Usuario usuario);
	
	
	/**
	 * Obtener usuario.
	 *
	 * @param id the id
	 * @return the usuario
	 */
	public Usuario obtenerUsuario(int id);
	
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
	
	
	public boolean modificarImagenUsuario(int idUsuario, String newFileName);
	
	public Boolean existenciaUsuarioPorUsername(String username);
	

}
