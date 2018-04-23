/**
 * 
 */
package com.software.estudialo.dao;

import java.util.List;

import com.software.estudialo.entities.Publicidad;

/**
 * @author LUIS
 *
 */
public interface PublicidadDao {
	
	public List<Publicidad> listarPublicidad();	
	
	public Boolean modificarPublicidad(int idBanner, String newFileName, String url);

}
