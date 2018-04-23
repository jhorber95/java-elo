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

import com.software.estudialo.entities.DataTableJson;
import com.software.estudialo.entities.Financiacion;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Publicador;
import com.software.estudialo.entities.Respuesta;
import com.software.estudialo.entities.RespuestaGeneral;
import com.software.estudialo.service.FinanciacionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// TODO: Auto-generated Javadoc
/**
 * The Class FinanciacionRestController.
 *
 * @author LUIS
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Api(value="financiacion", description="Operaciones pertenecientes a los financiaciones")
public class FinanciacionRestController {	
	
/** The Constant url. */
private static final String url = "/financiacion";
	
	/** The logger. */
	private Logger logger = Logger.getLogger(FinanciacionRestController.class);

	
	/** The financiacion service. */
	@Autowired
	FinanciacionService financiacionService; 
	
	
	@GetMapping(url + "/financiera/{idInstitucion}")
	@ApiOperation(value = "obtiene el financiacion con el id" )
    public ResponseEntity<RespuestaGeneral> obtenerFinanciacionPorInstitucion(@PathVariable int idInstitucion) {
		logger.debug("obtenerFinanciacionPorInstitucion -- Obtener financiacion por institucion");
		
		Financiacion financiacion = financiacionService.obtenerFinanciacionPorInstitucion(idInstitucion); 
		
		List<Financiacion> financiaciones = new ArrayList<>();
		financiaciones.add(financiacion);
		RespuestaGeneral rg = new RespuestaGeneral();
		rg.setExito(true);
		rg.setCodigo(200);
		rg.setData(financiaciones);
        return new ResponseEntity<RespuestaGeneral>(rg, HttpStatus.OK);
    }
	
	
	
	/**
	 * Obtener financiacion.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@GetMapping(url + "/{id}")
	@ApiOperation(value = "obtiene el financiacion con el id" )
    public ResponseEntity<RespuestaGeneral> obtenerFinanciacion(@PathVariable int id) {
		logger.debug("obtenerFinanciacion -- Obtener financiacion");
		Financiacion financiacion = financiacionService.obtenerFinanciacion(id);      
		List<Financiacion> financiaciones = new ArrayList<>();
		financiaciones.add(financiacion);
		RespuestaGeneral rg = new RespuestaGeneral();
		rg.setExito(true);
		rg.setCodigo(200);
		rg.setData(financiaciones);
        return new ResponseEntity<RespuestaGeneral>(rg, HttpStatus.OK);
    }	
	
	
	/**
	 * Listar datos de financiaciones seg�n la estructura que requiere DataTable de JQuery .
	 *
	 * @param search            parametro a buscar en la consulta
	 * @param start            limite de inicio de los registros
	 * @param length            limite de fin hasta donde se consultaran los registros
	 * @param draw            n�mero de veces que se realiza la consulta
	 * @param posicion            posici�n de la columna con la cual se quiere ordenar los
	 *            registros
	 * @param direccion            orden en que se quieren ordenar lso registros ascendente o
	 *            descendente
	 * @return retorna la lista de financiaciones segun los parametros enviados
	 */
	@GetMapping(url)
	@ApiOperation(value = "obtiene las lista de financiaciones con paginacion para datatables" ) 
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	public ResponseEntity<JSONRespuesta> getFinanciaciones(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {
		
		logger.debug("getFinanciaciones --- listando los financiaciones");
		JSONRespuesta listFinanciaciones = new JSONRespuesta();
		listFinanciaciones = financiacionService.listarFinanciaciones(search, start, length, draw, posicion, direccion);
		return new ResponseEntity<JSONRespuesta>(listFinanciaciones, HttpStatus.OK);
	}
	
	
	@PostMapping(url + "/dataTables")
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	@ApiOperation(value = "obtiene las lista de financiaciones con paginacion para datatables" )
	public ResponseEntity<JSONRespuesta> getFinanciacionesPost(@RequestBody DataTableJson dataTableJson) {
		
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
		
		
		logger.debug("getUsuarios --- listando los usuarios");
		JSONRespuesta listUsuarios = new JSONRespuesta();
		listUsuarios = financiacionService.listarFinanciacionesAdmin(search, start, length, draw, posicion, "asc");
		return new ResponseEntity<JSONRespuesta>(listUsuarios, HttpStatus.OK);
	}
	
	
	
	
	/**
	 * Gets the financiacions buscador.
	 *
	 * @param search the search
	 * @param start the start
	 * @param length the length
	 * @param draw the draw
	 * @param posicion the posicion
	 * @param direccion the direccion
	 * @return the financiacions buscador
	 */
	@ApiOperation(value = "Lista los financiaciones a traves de la variable search (Campo de Busqueda)")
	@GetMapping(url + "/buscador")
	public ResponseEntity<JSONRespuesta> getFinanciacionsBuscador(@RequestParam(value = "search", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {
		
		logger.debug("getFinanciacionsBuscador --- listando los financiaciones");
		JSONRespuesta listaFinanciacions = new JSONRespuesta();
		listaFinanciacions = financiacionService.listarFinanciacionBuscador(search, start, length, draw, posicion, direccion);
		return new ResponseEntity<JSONRespuesta>(listaFinanciacions, HttpStatus.OK);
	}
	
	
	/**
	 * Adicionar financiacion.
	 *
	 * @param financiacion the financiacion
	 * @return the response entity
	 */
	@ApiOperation(value = "Adiciona financiacion")
	@PostMapping(url)
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	public ResponseEntity<Respuesta> adicionarFinanciacion(@RequestBody Financiacion financiacion) {				
		financiacionService.agregarFinanciacion(financiacion);
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}

	/**
	 * Modificar financiacion.
	 *
	 * @param id the id
	 * @param financiacion the financiacion
	 * @return the response entity
	 */
	@ApiOperation(value = "Modifica una financiacion - proporciona el id y el objeto financiacion")
	@PutMapping(url + "/{id}")	
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	public ResponseEntity<Respuesta> modificarFinanciacion(@PathVariable int id, @RequestBody Financiacion financiacion) {		
		financiacionService.modificarFinanciacion(id, financiacion);		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		
	}
	
	/**
	 * Eliminar financiacion.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@ApiOperation(value = "Elimina una financiacion (Cambio de estado a eliminado) - proporciona el id")
	@DeleteMapping(url + "/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	public ResponseEntity<Respuesta> eliminarFinanciacion(@PathVariable int id) {

		financiacionService.eliminarFinanciacion(id);
		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);	
		
	}
	
	

}
