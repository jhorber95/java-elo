/**
 * 
 */
package com.software.estudialo.dao;

import com.software.estudialo.entities.Institucion;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Usuario;

// TODO: Auto-generated Javadoc
/**
 * The Interface InstitucionDao.
 *
 * @author LUIS
 */
public interface InstitucionDao {
	
	
	/**
	 * Agregar institucion.
	 *
	 * @param institucion the institucion
	 * @return true, if successful
	 */
	public boolean agregarInstitucion(Institucion institucion);
	
	public Boolean institucionActiva(String username);
	
	public Institucion buscarInstitucionPorUsername(String username);
	
	public Institucion  obtenerInstitucionPorUsernameCompletoSinPassword(String username);
	
	/*
	 * Busca la institucion por existencia por username
	 * 
	 */
	public Boolean buscarExisteInstitucionPorUsername(String username);
	
	
	/**
	 * Modificar institucion.
	 *
	 * @param id the id
	 * @param institucion the institucion
	 * @return true, if successful
	 */
	public boolean modificarInstitucion(int id, Institucion institucion);
	
	
	public boolean modificarInstitucionAdmin(int id, Institucion institucion);
	
	/**
	 * Eliminar institucion.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	public boolean eliminarInstitucion(int id);
	
	
	/**
	 * Buscar institucion.
	 *
	 * @param id the id
	 * @return the boolean
	 */
	public Boolean buscarInstitucion(int id);
	
	
	/**
	 * Buscar institucion.
	 *
	 * @param institucion the institucion
	 * @return the boolean
	 */
	public Boolean buscarInstitucion(Institucion institucion);
	
	/**
	 * Obtener id institucion por id oferta.
	 *
	 * @param idOferta the id oferta
	 * @return the int
	 */
	public int obtenerIdInstitucionPorIdOferta(int idOferta);

	
	
	/**
	 * Obtener institucion.
	 *
	 * @param id the id
	 * @return the institucion
	 */
	public Institucion obtenerInstitucion(int id);
	
	/**
	 * Obtener institucion por oferta.
	 *
	 * @param idOferta the id oferta
	 * @return the institucion
	 */
	public Institucion obtenerInstitucionPorOferta(int idOferta);
	
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
	
	
	public boolean modificarImagenInstitucion(int idInstitucion, String newFileName);
	
	public Boolean existenciaInstitucionPorUsername(String username);
	
}
