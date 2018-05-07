/**
 * 
 */
package com.software.estudialo.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.software.estudialo.entities.DataTableJson;
import com.software.estudialo.entities.Evento;
import com.software.estudialo.entities.Financiacion;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Respuesta;
import com.software.estudialo.entities.RespuestaGeneral;
import com.software.estudialo.service.EventoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// TODO: Auto-generated Javadoc
/**
 * The Class EventoRestController.
 *
 * @author LUIS
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Api(value="eventos", description="Operaciones pertenecientes a los eventos")
public class EventoRestController {

	/** The Constant url. */
	private static final String url = "/evento";
	
	/** The logger. */
	private Logger logger = Logger.getLogger(EventoRestController.class);

	
	/** The evento service. */
	@Autowired
	EventoService eventoService;
	
	
	@ApiOperation(value = "Lista las eventos de una institucion, debe proporcionar el id de la institucion")
	@GetMapping(url + "/institucion/{idInstitucion}")
	//@PreAuthorize("hasAuthority('ROLE_INSTITUCION')")
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')  or hasAuthority('ROLE_INSTITUCION')")
	public ResponseEntity<List<Evento>> getEventosInstitucion(@PathVariable(value = "idInstitucion") int idInstitucion) {
		
		logger.debug("getEventosInstitucion --- listando los eventos de una institucion");
		List<Evento> listaEventosInstitucion = new ArrayList<Evento>();
		listaEventosInstitucion = eventoService.listarEventosInstitucion(idInstitucion);
		return new ResponseEntity<List<Evento>>(listaEventosInstitucion, HttpStatus.OK);
	}
	
	
	
	
	/**
	 * Obtener evento.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@GetMapping(url + "/{id}")
	@ApiOperation(value = "obtiene el evento con el id" )	
    public ResponseEntity<RespuestaGeneral> obtenerEvento(@PathVariable int id) {
		logger.debug("obtenerEvento -- Obtener evento");
		Evento evento = eventoService.obtenerEvento(id);   
		
		List<Evento> eventos = new ArrayList<>();
		eventos.add(evento);
		RespuestaGeneral rg = new RespuestaGeneral();
		rg.setExito(true);
		rg.setCodigo(200);
		rg.setData(eventos);
        return new ResponseEntity<RespuestaGeneral>(rg, HttpStatus.OK);
    }	
	
	

	/**
	 * Listar datos de eventos seg�n la estructura que requiere DataTable de JQuery .
	 *
	 * @param search            parametro a buscar en la consulta
	 * @param start            limite de inicio de los registros
	 * @param length            limite de fin hasta donde se consultaran los registros
	 * @param draw            n�mero de veces que se realiza la consulta
	 * @param posicion            posici�n de la columna con la cual se quiere ordenar los
	 *            registros
	 * @param direccion            orden en que se quieren ordenar lso registros ascendente o
	 *            descendente
	 * @return retorna la lista de eventoes segun los parametros enviados
	 */
	@GetMapping(url)
	@ApiOperation(value = "obtiene las lista de eventos con paginacion para datatables" ) 
	//@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	public ResponseEntity<JSONRespuesta> getEventos(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {
		
		logger.debug("getEventos --- listando los eventos");
		JSONRespuesta listEventos = new JSONRespuesta();
		listEventos = eventoService.listarEventos(search, start, length, draw, posicion, direccion);
		return new ResponseEntity<JSONRespuesta>(listEventos, HttpStatus.OK);
	}
	
	
	@PostMapping(url + "/dataTables")
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	@ApiOperation(value = "obtiene las lista de usuarios con paginacion para datatables" )
	public ResponseEntity<JSONRespuesta> getEventosPost(@RequestBody DataTableJson dataTableJson) {
		
		logger.debug("Valores de prueba===> search=" + dataTableJson.getSearch().getValue());
		logger.debug("Valores de prueba===> start=" + dataTableJson.getStart());
		logger.debug("Valores de prueba===> length=" + dataTableJson.getLength());
		logger.debug("Valores de prueba===> draw=" + dataTableJson.getDraw());
		logger.debug("Valores de prueba===> posicion=" + dataTableJson.getOrder().get(0).getPosicion());
		logger.debug("Valores de prueba===> direccion=" + dataTableJson.getOrder().get(0).getDireccion());
		
		String search = dataTableJson.getSearch().getValue();
		int start = dataTableJson.getStart();
		int length = dataTableJson.getLength();
		int draw = dataTableJson.getDraw();
		int posicion = dataTableJson.getOrder().get(0).getPosicion();
		
		
		logger.debug("getEventosPost --- listando los eventos");
		JSONRespuesta listUsuarios = new JSONRespuesta();
		listUsuarios = eventoService.listarEventosAdmin(search, start, length, draw, posicion, "asc");
		return new ResponseEntity<JSONRespuesta>(listUsuarios, HttpStatus.OK);
	}
	
	
	
	/**
	 * Gets the eventos buscador.
	 *
	 * @param search the search
	 * @param start the start
	 * @param length the length
	 * @param draw the draw
	 * @param posicion the posicion
	 * @param direccion the direccion
	 * @return the eventos buscador
	 */
	@ApiOperation(value = "Lista los eventos a traves de la variable search (Campo de Busqueda)")
	@GetMapping(url + "/buscador")
	public ResponseEntity<JSONRespuesta> getEventosBuscador(@RequestParam(value = "search", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {
		
		logger.debug("getEventosBuscador --- listando los eventos");
		JSONRespuesta listaEventos = new JSONRespuesta();
		listaEventos = eventoService.listarEventoBuscador(search, start, length, draw, posicion, direccion);
		return new ResponseEntity<JSONRespuesta>(listaEventos, HttpStatus.OK);
	}

	/**
	 * Gets the eventos filtros.
	 *
	 * @param start the start
	 * @param length the length
	 * @param draw the draw
	 * @param posicion the posicion
	 * @param direccion the direccion
	 * @param tipoEvento the tipo evento
	 * @return the eventos filtros
	 */
	@ApiOperation(value = "Lista las eventos a traves del filtro (tipoEvento)")
	@GetMapping(url + "/filtros")
	public ResponseEntity<JSONRespuesta> getEventosFiltros(
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion,
			@RequestParam(value = "tipoEvento", defaultValue = "0") int tipoEvento) {
		
		logger.debug("getEventosFiltros --- listando los eventos");
		JSONRespuesta listaEventos = new JSONRespuesta();
		listaEventos = eventoService.listarEventoFiltros(start, length, draw, posicion, direccion, tipoEvento);
		return new ResponseEntity<JSONRespuesta>(listaEventos, HttpStatus.OK);
	}
	
	/**
	 * Adicionar evento.
	 *
	 * @param evento the evento
	 * @return the response entity
	 */
	@ApiOperation(value = "Adiciona evento")
	@PostMapping(url)
	@PreAuthorize("hasAuthority('ROLE_INSTITUCION') or hasAuthority('ROLE_ADMINISTRADOR')")
	public ResponseEntity<Respuesta> adicionarEvento(@RequestBody Evento evento) {				
		int idEvento = eventoService.agregarEvento(evento);
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		Evento eventoT = new Evento();
		eventoT.setId(idEvento);
		respuesta.setBody(eventoT);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}

	/**
	 * Modificar evento.
	 *
	 * @param id the id
	 * @param evento the evento
	 * @return the response entity
	 */
	@ApiOperation(value = "Modifica una evento - proporciona el id y el objeto evento")
	@PutMapping(url + "/{id}")	
	@PreAuthorize("hasAuthority('ROLE_INSTITUCION') or hasAuthority('ROLE_ADMINISTRADOR')")
	public ResponseEntity<Respuesta> modificarEvento(@PathVariable int id, @RequestBody Evento evento) {		
		eventoService.modificarEvento(id, evento);		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		
	}
	
	/**
	 * Eliminar evento.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@ApiOperation(value = "Elimina una evento (Cambio de estado a eliminado) - proporciona el id")
	@DeleteMapping(url + "/{id}")
	@PreAuthorize("hasAuthority('ROLE_INSTITUCION') or hasAuthority('ROLE_ADMINISTRADOR')")
	public ResponseEntity<Respuesta> eliminarEvento(@PathVariable int id) {

		eventoService.eliminarEvento(id);
		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);	
		
	}
	
	
	
}
