/**
 * 
 */
package com.software.estudialo.service;


import com.software.estudialo.entities.Oferta;

import java.util.List;

import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Oferta;

// TODO: Auto-generated Javadoc
/**
 * The Interface OfertaService.
 *
 * @author LUIS
 */
public interface OfertaService {
	
	//public void agregarOferta(Oferta oferta);
	
	//public void  modificarOferta(int id, Oferta oferta);
	
	//public void  eliminarOferta(int id);
	
	/**
	 * Obtener oferta.
	 *
	 * @param id the id
	 * @return the oferta
	 */
	public Oferta  obtenerOferta(int id);
	
	/**
	 * Agregar oferta.
	 *
	 * @param oferta the oferta
	 */
	public int agregarOferta(Oferta oferta);
	

	/**
	 * Modificar oferta.
	 *
	 * @param id the id
	 * @param oferta the oferta
	 */
	public void  modificarOferta(int id, Oferta oferta);
	
	
	
	public void  modificarOfertaGeneral(int id, Oferta oferta);
	
	
	/**
	 * Eliminar oferta.
	 *
	 * @param id the id
	 */
	public void  eliminarOferta(int id);
	
	public void eliminarOfertaFreelancer(int idOferta, int idUsuario);
	
	public void eliminarOfertaInstitucion(int idOferta, int idInstitucion);
	
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
	 * Listar oferta buscador.
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
	
	public JSONRespuesta listarOfertas();
	
	
	public List<Oferta> listarOfertasDestacadas();
	
	
	
	
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
	public JSONRespuesta listarOfertaFiltros(int start, int length, int draw, int posicion, String direccion, int categoria, int municipio, int tipoOfrece, int tipoOferta, int precioMinimo, int precioMaximo, String nombreOferta);
	
	
	public Boolean calificacionOfertaFreelancerRealizada(int idOferta, int idUsuario);
	
	
	public void calificarOfertaFreelancer(int idOferta, int idUsuario, double calificacion, String comentario);
	
	
	public Boolean calificacionOfertaInstitucionRealizada(int idOferta, int idUsuario);
	
	public void calificarOfertaInstitucion(int idOferta, int idUsuario, double calificacion, String comentario);
	
	
	
	public List<Oferta> getOfertasOfrecidasInstitucion(int idInstitucion);
	
	public List<Oferta> getOfertasOfrecidasFreelancer(int idFreelancer);
	
	
	
	
}
