/**
 * 
 */
package com.software.estudialo.dao;


import com.software.estudialo.entities.Oferta;
import com.software.estudialo.entities.ResultadoVocacional;

import java.util.List;

import com.software.estudialo.entities.Calificacion;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Oferta;

// TODO: Auto-generated Javadoc
/**
 * The Interface OfertaDao.
 *
 * @author LUIS
 */
public interface OfertaDao {
	
	
	public Boolean ofertaPerteneceFreelancer(int idOferta, int idFreelancer);
	
	public Boolean ofertaPerteneceInstitucion(int idOferta, int idInstitucion);
	
	/**
	 * Agregar oferta.
	 *
	 * @param oferta the oferta
	 * @return the boolean
	 */
	public int agregarOferta(Oferta oferta);
	
	/**
	 * Modificar oferta.
	 *
	 * @param id the id
	 * @param Oferta the oferta
	 * @return the boolean
	 */
	public Boolean modificarOferta(int id, Oferta Oferta);
	
	
	
	/**
	 * Modificar oferta general para los usuarios que crearon una oferta
	 *
	 * @param id the id
	 * @param Oferta the oferta
	 * @return the boolean
	 */
	public Boolean modificarOfertaGeneral(int id, Oferta Oferta);
	
	/**
	 * Eliminar oferta.
	 *
	 * @param id the id
	 * @return the boolean
	 */
	public Boolean eliminarOferta(int id);
	
	/**
	 * Buscar oferta.
	 *
	 * @param id the id
	 * @return the boolean
	 */
	public Boolean buscarOferta(int id);
	
	
	public Boolean estudianteEstadoInscritoOfertaInstitucion(int idInscripcionOfertaInstitucion);
	
	public Boolean estudianteEstadoInscritoOfertaFreelancer(int idInscripcionOfertaFreelancer);
	
	
	/**
	 * Buscar oferta.
	 *
	 * @param oferta the oferta
	 * @return the boolean
	 */
	public Boolean buscarOferta(Oferta oferta);
	
	
	/**
	 * Listar ofertas.
	 *
	 * @param search the search
	 * @param start the start
	 * @param length the length
	 * @param draw the draw
	 * @param posicion the posicion
	 * @param direccion the direccion
	 * @return the JSON respuesta
	 */
	public JSONRespuesta listarOfertas(String search, int start, int length, int draw, int posicion, String direccion);
	
	public JSONRespuesta listarOfertasAdmin(String search, int start, int length, int draw, int posicion, String direccion);
	
	/**
	 * Obtener oferta.
	 *
	 * @param id the id
	 * @return the oferta
	 */
	public Oferta obtenerOferta(int id);	
	
	
	/**
	 * Obtener id oferta por id oferta freelancer.
	 *
	 * @param idOfertaFreelancer the id oferta freelancer
	 * @return the int
	 */
	public int obtenerIdOfertaPorIdOfertaFreelancer(int idOfertaFreelancer);
	
	/**
	 * Obtener id oferta por id oferta institucion.
	 *
	 * @param idOfertaInstitucion the id oferta institucion
	 * @return the int
	 */
	public int obtenerIdOfertaPorIdOfertaInstitucion(int idOfertaInstitucion);

	
	/**
	 * Calificar oferta freelancer.
	 *
	 * @param idInscripcionOfertaFreelancer the id inscripcion oferta freelancer
	 * @param calificacion the calificacion
	 * @param comentario the comentario
	 * @return the boolean
	 */
	public Boolean calificarOfertaFreelancer(int idInscripcionOfertaFreelancer, double calificacion, String comentario);
	
	/**
	 * Calificar oferta institucion.
	 *
	 * @param idInscripcionOfertaInstitucion the id inscripcion oferta institucion
	 * @param calificacion the calificacion
	 * @param comentario the comentario
	 * @return the boolean
	 */
	public Boolean calificarOfertaInstitucion(int idInscripcionOfertaInstitucion, double calificacion, String comentario);
	
	/**
	 * Listar oferta.
	 *
	 * @param search the search
	 * @param start the start
	 * @param length the length
	 * @param draw the draw
	 * @param posicion the posicion
	 * @param direccion the direccion
	 * @return the JSON respuesta
	 */
	public JSONRespuesta listarOfertaBuscador(String search, int start, int length, int draw, int posicion, String direccion);
	
	
	/**
	 * Listar oferta filtros.
	 *
	 * @param start the start
	 * @param length the length
	 * @param draw the draw
	 * @param posicion the posicion
	 * @param direccion the direccion
	 * @param categoria the categoria
	 * @param municipio the municipio
	 * @param tipoOfrece the tipo ofrece
	 * @param tipoOferta the tipo oferta
	 * @return the JSON respuesta
	 */
	public JSONRespuesta listarOfertaFiltros(int start, int length, int draw, int posicion, String direccion, int categoria, int municipio, int tipoOfrece, int tipoOferta, int precioMinimo, int precioMaximo);

	
	
	/**
	 * Obtener ofertas freelancer del estudiante.
	 *
	 * @param idEstudiante the id estudiante
	 * @return the list
	 */
	public List<Oferta> obtenerOfertasFreelancerDelEstudiante(int idEstudiante);
	
	/**
	 * Obtener ofertas institucion del estudiante.
	 *
	 * @param idEstudiante the id estudiante
	 * @return the list
	 */
	public List<Oferta> obtenerOfertasInstitucionDelEstudiante(int idEstudiante);
	
	/**
	 * Calificacion oferta freelancer realizada.
	 *
	 * @param idOferta the id oferta
	 * @param idEstudiante the id estudiante
	 * @return the boolean
	 */
	public Boolean calificacionOfertaFreelancerRealizada(int idOferta, int idEstudiante);
	
	/**
	 * Obtener calificacion oferta freelancer.
	 *
	 * @param idOferta the id oferta
	 * @return the calificacion
	 */
	public Calificacion obtenerCalificacionOfertaFreelancer(int idOferta);
	
	
	
	/**
	 * Calificacion oferta institucion realizada.
	 *
	 * @param idOferta the id oferta
	 * @param idEstudiante the id estudiante
	 * @return the boolean
	 */
	public Boolean calificacionOfertaInstitucionRealizada(int idOferta, int idEstudiante);
	
	/**
	 * Obtener calificacion oferta institucion.
	 *
	 * @param idOferta the id oferta
	 * @return the calificacion
	 */
	public Calificacion obtenerCalificacionOfertaInstitucion(int idOferta);
	
	
	/**
	 * Existen calificaciones oferta freelancer.
	 *
	 * @param idOferta the id oferta
	 * @return the boolean
	 */
	public Boolean existenCalificacionesOfertaFreelancer(int idOferta);
	
	/**
	 * Existen calificaciones oferta institucion.
	 *
	 * @param idOferta the id oferta
	 * @return the boolean
	 */
	public Boolean existenCalificacionesOfertaInstitucion(int idOferta);
	
	
	/**
	 * Modificar imagen oferta.
	 *
	 * @param idOferta the id oferta
	 * @param newFileName the new file name
	 * @return true, if successful
	 */
	public boolean modificarImagenOferta(int idOferta, String newFileName);
	
	
	public List<Oferta> listarOfertasDestacadas();
	
	public List<Oferta> obtenerOfertasResultadTestVocacional(ResultadoVocacional resultadoVocacional); 
	
	public List<Oferta> getOfertasOfrecidasInstitucion(int idInstitucion);
	
	public List<Oferta> getOfertasOfrecidasFreelancer(int idFreelancer);
	
	

}
