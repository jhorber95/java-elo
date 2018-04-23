/**
 * 
 */
package com.software.estudialo.service;

import java.util.List;

import com.software.estudialo.entities.Rol;

/**
 * @author LUIS
 *
 */
public interface RolService {
	
	public List<Rol> obtenerRoles();	
	
	public void agregarRolUsuario(int idUsuario, int idRol);

}
