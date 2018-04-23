/**
 * 
 */
package com.software.estudialo.service;

import com.software.estudialo.entities.Financiacion;
import com.software.estudialo.entities.JSONRespuesta;

// TODO: Auto-generated Javadoc
/**
 * The Interface FinanciacionService.
 *
 * @author LUIS
 */
public interface FinanciacionService {
	
	/**
	 * Obtener financiacion.
	 *
	 * @param id the id
	 * @return the financiacion
	 */
	public Financiacion  obtenerFinanciacion(int id);
	
	
	public Financiacion  obtenerFinanciacionPorInstitucion(int id);
	
	
	

	/**
	 * Agregar financiacion.
	 *
	 * @param financiacion the financiacion
	 */
	public void agregarFinanciacion(Financiacion financiacion);
	

	/**
	 * Modificar financiacion.
	 *
	 * @param id the id
	 * @param financiacion the financiacion
	 */
	public void  modificarFinanciacion(int id, Financiacion financiacion);
	
	
	/**
	 * Eliminar financiacion.
	 *
	 * @param id the id
	 */
	public void  eliminarFinanciacion(int id);
	
	
	/**
	 * Listar financiacion buscador.
	 *
	 * @param search the search
	 * @param start the start
	 * @param length the length
	 * @param draw the draw
	 * @param posicion the posicion
	 * @param direccion the direccion
	 * @return the JSON respuesta
	 */
	public JSONRespuesta listarFinanciacionBuscador(String search, int start, int length, int draw, int posicion, String direccion);
		
	/**
	 * Listar financiaciones.
	 *
	 * @param search the search
	 * @param start the start
	 * @param length the length
	 * @param draw the draw
	 * @param posicion the posicion
	 * @param direccion the direccion
	 * @return the JSON respuesta
	 */
	public JSONRespuesta listarFinanciaciones(String search, int start, int length, int draw, int posicion, String direccion);
	
	public JSONRespuesta listarFinanciacionesAdmin(String search, int start, int length, int draw, int posicion, String direccion);
	
	
}
