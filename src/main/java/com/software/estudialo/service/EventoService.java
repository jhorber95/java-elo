/**
 * 
 */
package com.software.estudialo.service;

import com.software.estudialo.entities.JSONRespuesta;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.software.estudialo.entities.Evento;
import com.software.estudialo.entities.Evento;

// TODO: Auto-generated Javadoc
/**
 * The Interface EventoService.
 *
 * @author LUIS
 */

public interface EventoService {
	
	public List<Evento> listarEventosInstitucion(int idInstitucion);

	/**
	 * Obtener evento.
	 *
	 * @param id the id
	 * @return the evento
	 */
	public Evento  obtenerEvento(int id);
	
	/**
	 * Agregar evento.
	 *
	 * @param evento the evento
	 */
	public int agregarEvento(Evento evento);
	

	/**
	 * Modificar evento.
	 *
	 * @param id the id
	 * @param evento the evento
	 */
	public void  modificarEvento(int id, Evento evento);
	
	
	/**
	 * Eliminar evento.
	 *
	 * @param id the id
	 */
	public void  eliminarEvento(int id);
	
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
	public JSONRespuesta listarEventoBuscador(String search, int start, int length, int draw, int posicion, String direccion);
	
	
	
	/**
	 * Listar evento filtros.
	 *
	 * @param start the start
	 * @param length the length
	 * @param draw the draw
	 * @param posicion the posicion
	 * @param direccion the direccion
	 * @param tipoEvento the tipo evento
	 * @return the JSON respuesta
	 */
	public JSONRespuesta listarEventoFiltros(int start, int length, int draw, int posicion, String direccion, int tipoEvento);
	
	/**
	 * Listar eventos.
	 *
	 * @param search the search
	 * @param start the start
	 * @param length the length
	 * @param draw the draw
	 * @param posicion the posicion
	 * @param direccion the direccion
	 * @return the JSON respuesta
	 */
	public JSONRespuesta listarEventos(String search, int start, int length, int draw, int posicion, String direccion);
	
	public JSONRespuesta listarEventosAdmin(String search, int start, int length, int draw, int posicion, String direccion);
	
}
