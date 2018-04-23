/**
 * 
 */
package com.software.estudialo.dao;

import java.util.List;
import java.util.Set;

import com.software.estudialo.entities.Rol;

/**
 * @author LUIS
 *
 */
public interface RolDao {
	
	/**
	 * Listar los Roles
	 * @return Lista de los Roles
	 */
	public Set<Rol> buscarRolesPorUsername(String nombre);		
	
	/**
	 * Listar los Roles
	 * @return Lista de los Roles
	 */
	public List<Rol> obtenerRoles();	
	
	
	public Boolean agregarRolUsuario(int idUsuario, int idRol);
	
	public Boolean buscarUsuarioRolExiste(int idUsuario, int idRol);	
	
	
}
