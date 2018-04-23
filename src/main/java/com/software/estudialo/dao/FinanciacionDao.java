/**
 * 
 */
package com.software.estudialo.dao;


import com.software.estudialo.entities.Financiacion;
import com.software.estudialo.entities.JSONRespuesta;

// TODO: Auto-generated Javadoc
/**
 * The Interface FinanciacionDao.
 *
 * @author LUIS
 */
public interface FinanciacionDao {
	
	/**
	 * Obtener evento.
	 *
	 * @param id the id
	 * @return the evento
	 */
	public Financiacion obtenerFinanciacion(int id);	
	
	
	public Financiacion obtenerFinanciacionPorInstitucion(int idInstitucion);	
	
	/**
	 * Agregar financiacion.
	 *
	 * @param financiacion the financiacion
	 * @return true, if successful
	 */
	public boolean agregarFinanciacion(Financiacion financiacion);
	
	
	/**
	 * Modificar financiacion.
	 *
	 * @param id the id
	 * @param financiacion the financiacion
	 * @return true, if successful
	 */
	public boolean modificarFinanciacion(int id, Financiacion financiacion);
	
	
	/**
	 * Eliminar financiacion.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	public boolean eliminarFinanciacion(int id);
	
	
	/**
	 * Buscar financiacion.
	 *
	 * @param id the id
	 * @return the boolean
	 */
	public Boolean buscarFinanciacion(int id);
	
	
	/**
	 * Buscar financiacion.
	 *
	 * @param financiacion the financiacion
	 * @return the boolean
	 */
	public Boolean buscarFinanciacion(Financiacion financiacion);
	
	
	
	
	/**
	 * Listar evento buscador.
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
