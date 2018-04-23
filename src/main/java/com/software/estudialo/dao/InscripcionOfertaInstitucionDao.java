/**
 * 
 */
package com.software.estudialo.dao;

import java.util.List;

import com.software.estudialo.entities.InscripcionOfertaInstitucion;
import com.software.estudialo.entities.JSONRespuesta;

// TODO: Auto-generated Javadoc
/**
 * The Interface InscripcionOfertaInstitucionDao.
 *
 * @author LUIS
 */
public interface InscripcionOfertaInstitucionDao {
	
	/**
	 * Obtenerinscripcion oferta institucion.
	 *
	 * @param id the id
	 * @return the inscripcion oferta institucion
	 */
	public InscripcionOfertaInstitucion  obtenerinscripcionOfertaInstitucion(int id);
	
	
	/**
	 * Inscribir estudiante.
	 *
	 * @param idOfertaInstitucion the id oferta institucion
	 * @param idEstudiante the id estudiante
	 * @return the boolean
	 */
	public Boolean inscribirEstudiante(int idOfertaInstitucion, int idEstudiante);
	
	
	public Boolean confirmarInscripcionEstudiante(int idInscripcionOfertaInstitucion);
	
	public Boolean rechazarInscripcionEstudiante(int idInscripcionOfertaInstitucion);
	
	/**
	 * Obtener id oferta institucion.
	 *
	 * @param idOferta the id oferta
	 * @param idInstitucion the id institucion
	 * @return the int
	 */
	public int obtenerIdOfertaInstitucion(int idOferta, int idInstitucion);
	
	
	
	/**
	 * Buscar inscripcion oferta institucion.
	 *
	 * @param idEstudiante the id estudiante
	 * @param idOfertaInstitucion the id oferta institucion
	 * @return the boolean
	 */
	public Boolean buscarInscripcionOfertaInstitucion(int idEstudiante, int idOfertaInstitucion);
	
	
	/**
	 * Listar inscripcion oferta instituciones.
	 *
	 * @param search the search
	 * @param start the start
	 * @param length the length
	 * @param draw the draw
	 * @param posicion the posicion
	 * @param direccion the direccion
	 * @return the JSON respuesta
	 */
	public JSONRespuesta listarInscripcionOfertaInstituciones(String search, int start, int length, int draw, int posicion, String direccion);
	
	
	/**
	 * Listar inscripcion oferta institucion.
	 *
	 * @param idInstitucion the id institucion
	 * @param search the search
	 * @param start the start
	 * @param length the length
	 * @param draw the draw
	 * @param posicion the posicion
	 * @param direccion the direccion
	 * @return the JSON respuesta
	 */
	public JSONRespuesta listarInscripcionOfertaInstitucion(int idInstitucion, String search, int start, int length, int draw, int posicion, String direccion);

	
	public JSONRespuesta listarPreInscripcionesOfertaInstitucion(int idInstitucion, String search, int start, int length,
			int draw, int posicion, String direccion);
	
	
	public Boolean buscarInscripcionOfertaInstitucionConEstudiante(int idOferta, int idEstudiante);
	
	public int obtenerIdInscripcionOfertaInstitucion(int idOferta, int idEstudiante);
	
	public List<InscripcionOfertaInstitucion> obtenerInscripcionOfertaInstitucionDeEstudiante(int idEstudiante);
	
	
	
}
