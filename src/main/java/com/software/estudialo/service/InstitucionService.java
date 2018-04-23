/**
 * 
 */
package com.software.estudialo.service;

import com.software.estudialo.entities.Institucion;
import com.software.estudialo.entities.JSONRespuesta;

// TODO: Auto-generated Javadoc
/**
 * The Interface InstitucionService.
 *
 * @author LUIS
 */
public interface InstitucionService {
	
	/**
	 * Obtener institucion.
	 *
	 * @param id the id
	 * @return the institucion
	 */
	public Institucion  obtenerInstitucion(int id);
	
	
	/**
	 * Agregar institucion.
	 *
	 * @param institucion the institucion
	 */
	public void agregarInstitucion(Institucion institucion);
	
	
	
	public void  modificarInstitucionAdmin(int id, Institucion institucion);
	
	
	/**
	 * Modificar institucion.
	 *
	 * @param id the id
	 * @param institucion the institucion
	 */
	public void  modificarInstitucion(int id, Institucion institucion);
	
	
	/**
	 * Eliminar institucion.
	 *
	 * @param id the id
	 */
	public void  eliminarInstitucion(int id);
	
	/**
	 * Listar instituciones.
	 *
	 * @param search the search
	 * @param start the start
	 * @param length the length
	 * @param draw the draw
	 * @param posicion the posicion
	 * @param direccion the direccion
	 * @return the JSON respuesta
	 */
	public JSONRespuesta listarInstituciones(String search, int start, int length, int draw, int posicion, String direccion);
	
	public JSONRespuesta listarInstitucionesAdmin(String search, int start, int length, int draw, int posicion, String direccion);
	
	
	
	public JSONRespuesta listarInstitucionesFinancieras(String search, int start, int length, int draw, int posicion, String direccion);

}
