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
import com.software.estudialo.entities.Evento;
import com.software.estudialo.entities.Financiacion;
import com.software.estudialo.entities.Institucion;
import com.software.estudialo.entities.Institucion;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Respuesta;
import com.software.estudialo.entities.RespuestaGeneral;
import com.software.estudialo.service.InstitucionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;



// TODO: Auto-generated Javadoc
/**
 * The Class InstitucionRestController.
 *
 * @author LUIS
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Api(value="instituciones del sistema", description="Operaciones pertenecientes a las instituciones")
public class InstitucionRestController {
	
	/** The Constant url. */
	private static final String url = "/institucion";
	
	/** The logger. */
	private Logger logger = Logger.getLogger(InstitucionRestController.class);

	/** The categoria service. */
	@Autowired
	InstitucionService institucionService;
	
	
	/**
	 * Listar datos de instituciones seg�n la estructura que requiere DataTable de JQuery .
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
	@ApiOperation(value = "obtiene las lista de instituciones con paginacion para datatables" ) 
	//@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	public ResponseEntity<JSONRespuesta> getInstituciones(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {
		
		logger.debug("getInstituciones --- listando los instituciones");
		JSONRespuesta listInstituciones = new JSONRespuesta();
		listInstituciones = institucionService.listarInstituciones(search, start, length, draw, posicion, direccion);
		return new ResponseEntity<JSONRespuesta>(listInstituciones, HttpStatus.OK);
	}
	
	/**
	 * Obtener institucion.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@GetMapping(url + "/todas")
	@ApiOperation(value = "obtiene todas las instituciones" )	
    public ResponseEntity<RespuestaGeneral> listaInstituciones() {
		logger.debug("listaInstituciones -- Obtener instituciones");
		 
		
		List<Institucion> instituciones = institucionService.listaIntituciones();
		
		RespuestaGeneral rg = new RespuestaGeneral();
		rg.setExito(true);
		rg.setCodigo(200);
		rg.setData(instituciones);
        return new ResponseEntity<RespuestaGeneral>(rg, HttpStatus.OK);
    }
	
	
	@PostMapping(url + "/dataTables")
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	@ApiOperation(value = "obtiene las lista de instituciones con paginacion para datatables" )
	public ResponseEntity<JSONRespuesta> getInstitucionesPost(@RequestBody DataTableJson dataTableJson) {
		
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
		
		
		logger.debug("getInstitucionesPost --- listando los instituciones");
		JSONRespuesta listUsuarios = new JSONRespuesta();
		listUsuarios = institucionService.listarInstitucionesAdmin(search, start, length, draw, posicion, "asc");
		return new ResponseEntity<JSONRespuesta>(listUsuarios, HttpStatus.OK);
	}	
	
	
	
	/**
	 * Obtener institucion.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@GetMapping(url + "/{id}")
	@ApiOperation(value = "obtiene la institucion con el id" )	
    public ResponseEntity<RespuestaGeneral> obtenerInstitucion(@PathVariable int id) {
		logger.debug("obtenerInstitucion -- Obtener institucion");
		Institucion institucion = institucionService.obtenerInstitucion(id);   
		
		List<Institucion> instituciones = new ArrayList<>();
		instituciones.add(institucion);
		RespuestaGeneral rg = new RespuestaGeneral();
		rg.setExito(true);
		rg.setCodigo(200);
		rg.setData(instituciones);
        return new ResponseEntity<RespuestaGeneral>(rg, HttpStatus.OK);
    }	
	
		
	/**
	 * Adicionar institucion.
	 *
	 * @param institucion the institucion
	 * @return the response entity
	 */
	@ApiOperation(value = "Adiciona institucion")
	@PostMapping(url)
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	public ResponseEntity<Respuesta> adicionarInstitucion(@RequestBody Institucion institucion) {				
		institucionService.agregarInstitucion(institucion);
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true); 
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}

	/**
	 * Modificar institucion.
	 *
	 * @param id the id
	 * @param institucion the institucion
	 * @return the response entity
	 */
	@ApiOperation(value = "El admin Modifica una institucion - proporciona el id y el objeto institucion")
	@PutMapping(url + "/admin/{id}")	
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	public ResponseEntity<Respuesta> modificarInstitucionAdmin(@PathVariable int id, @RequestBody Institucion institucion) {		
		institucionService.modificarInstitucionAdmin(id, institucion);		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		
	}
	
	
	@ApiOperation(value = "La institucion Modifica sus datos - proporciona el id y el objeto institucion")
	@PutMapping(url + "/{id}")	
	@PreAuthorize("hasAuthority('ROLE_INSTITUCION')")
	public ResponseEntity<Respuesta> modificarInstitucion(@PathVariable int id, @RequestBody Institucion institucion) {		
		institucionService.modificarInstitucion(id, institucion);		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		
	}
	
	/**
	 * Eliminar institucion.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@ApiOperation(value = "Elimina una institucion (Cambio de estado a eliminado) - proporciona el id")
	@DeleteMapping(url + "/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	public ResponseEntity<Respuesta> eliminarInstitucion(@PathVariable int id) {

		institucionService.eliminarInstitucion(id);
		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);	
		
	}
	
	
	
	
	@GetMapping(url + "/financieras")
	@ApiOperation(value = "obtiene las lista de instituciones financieras con paginacion para carrousel" ) 
	public ResponseEntity<JSONRespuesta> getInstitucionesFinancieras(@RequestParam(value = "search", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {
		
		logger.debug("getInstitucionesFinancieras --- listando los instituciones financieras");
		JSONRespuesta listInstituciones = new JSONRespuesta();
		listInstituciones = institucionService.listarInstitucionesFinancieras(search, start, length, draw, posicion, direccion);
		return new ResponseEntity<JSONRespuesta>(listInstituciones, HttpStatus.OK);
	}
	
	
}
