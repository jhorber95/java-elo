/**
 * 
 */
package com.software.estudialo.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.software.estudialo.entities.InscripcionOfertaInstitucion;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Respuesta;
import com.software.estudialo.service.InscripcionOfertaInstitucionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// TODO: Auto-generated Javadoc
/**
 * The Class InscripcionOfertaInstitucionRestController.
 *
 * @author LUIS
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Api(value="inscripciones de las ofertas de las instituciones del sistema", description="Operaciones pertenecientes a las ofertas de las instituciones")
public class InscripcionOfertaInstitucionRestController {
	
/** The Constant url. */
private static final String url = "/inscripcion/ofertaInstitucion";
	
	/** The logger. */
	private Logger logger = Logger.getLogger(InscripcionOfertaInstitucionRestController.class);

	/** The categoria service. */
	@Autowired
	InscripcionOfertaInstitucionService inscripcionOfertaInstitucionService;
	
	
	/**
	 * Listar datos de inscripciones a las ofertas de los instituciones seg�n la estructura que requiere DataTable de JQuery .
	 *
	 * @param search            parametro a buscar en la consulta
	 * @param start            limite de inicio de los registros
	 * @param length            limite de fin hasta donde se consultaran los registros
	 * @param draw            n�mero de veces que se realiza la consulta
	 * @param posicion            posici�n de la columna con la cual se quiere ordenar los
	 *            registros
	 * @param direccion            orden en que se quieren ordenar lso registros ascendente o
	 *            descendente
	 * @return retorna la lista de inscripciones a las ofertas de los instituciones segun los parametros enviados
	 */
	@GetMapping(url)
	@ApiOperation(value = "obtiene las lista de inscripciones a las ofertas de los institucions con paginacion para datatables" )
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	public ResponseEntity<JSONRespuesta> getInscripcionesOfertasInstituciones(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {
		
		logger.debug("getInscripcionesOfertasInstituciones --- listando las Inscripciones Ofertas Instituciones");
		JSONRespuesta listInscripcionesOfertasInstituciones = new JSONRespuesta();
		listInscripcionesOfertasInstituciones = inscripcionOfertaInstitucionService.listarInscripcionOfertaInstituciones(search, start, length, draw, posicion, direccion);
		return new ResponseEntity<JSONRespuesta>(listInscripcionesOfertasInstituciones, HttpStatus.OK);
	}
	
	
	
	/**
	 * Listar datos de inscripciones a las ofertas de una institucion especifica seg�n la estructura que requiere DataTable de JQuery .
	 *
	 * @param idInstitucion the id institucion
	 * @param search            parametro a buscar en la consulta
	 * @param start            limite de inicio de los registros
	 * @param length            limite de fin hasta donde se consultaran los registros
	 * @param draw            n�mero de veces que se realiza la consulta
	 * @param posicion            posici�n de la columna con la cual se quiere ordenar los
	 *            registros
	 * @param direccion            orden en que se quieren ordenar lso registros ascendente o
	 *            descendente
	 * @return retorna la lista de inscripciones a las ofertas de una institucion especifica segun los parametros enviados
	 */
	@GetMapping(url + "/institucion")
	@ApiOperation(value = "obtiene las lista de inscripciones a las ofertas de una institucion especifica con paginacion para datatables" )
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or hasAuthority('ROLE_INSTITUCION')")
	public ResponseEntity<JSONRespuesta> getInscripcionesOfertasInstitucion(@RequestParam(value = "idInstitucion") int idInstitucion,
			@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {
		
		logger.debug("getInscripcionesOfertasInstitucion --- listando las Inscripciones Ofertas de un Institucion");
		JSONRespuesta listInscripcionesOfertasInstitucion = new JSONRespuesta();
		listInscripcionesOfertasInstitucion = inscripcionOfertaInstitucionService.listarInscripcionOfertaInstitucion(idInstitucion, search, start, length, draw, posicion, direccion);
		return new ResponseEntity<JSONRespuesta>(listInscripcionesOfertasInstitucion, HttpStatus.OK);
	}
	
	
	@PostMapping(url + "/preinscritos/institucion")
	@ApiOperation(value = "obtiene las lista de inscripciones a las ofertas de una institucion especifico con paginacion para datatables" )
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or hasAuthority('ROLE_INSTITUCION')")
	public ResponseEntity<JSONRespuesta> getPreInscripcionesOfertasFreelancer(@RequestParam(value = "idInstitucion") int idInstitucion,
			@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {
		
		logger.debug("getInscripcionesOfertasFreelancer --- listando las Inscripciones Ofertas de un Freelancer");
		JSONRespuesta listInscripcionesOfertasFreelancer = new JSONRespuesta();
		listInscripcionesOfertasFreelancer = inscripcionOfertaInstitucionService.listarPreInscripcionesOfertaFreelancer(idInstitucion, search, start, length, draw, posicion, direccion);
		return new ResponseEntity<JSONRespuesta>(listInscripcionesOfertasFreelancer, HttpStatus.OK);
	}
	
	
	
	
	
	/**
	 * Obtener inscripcion oferta institucion.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@GetMapping(url + "/{id}")
	@ApiOperation(value = "obtiene la inscripcion a una oferta de una institucion con el id" )
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or hasAuthority('ROLE_INSTITUCION')")
    public ResponseEntity<InscripcionOfertaInstitucion> obtenerInscripcionOfertaInstitucion(@PathVariable int id) {
		logger.debug("obtenerInscripcionOfertaInstitucion -- Obtener inscripcion oferta institucion");
		InscripcionOfertaInstitucion inscripcionOfertaInstitucion = inscripcionOfertaInstitucionService.obtenerinscripcionOfertaInstitucion(id);    
        return new ResponseEntity<InscripcionOfertaInstitucion>(inscripcionOfertaInstitucion, HttpStatus.OK);
    }	
	
		
	/**
	 * Adicionar inscripcion.
	 *
	 * @param idOferta the id oferta
	 * @param idUsuario the id usuario
	 * @return the response entity
	 */
	@ApiOperation(value = "inscribe un estudiante a una oferta ofrecida por una institucion - Debe proporcionar el id de la oferta y el id del usuario estudiante")
	@GetMapping(url + "/{idOferta}/{idUsuario}")
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or hasAuthority('ROLE_ESTUDIANTE')")
	public ResponseEntity<Respuesta> adicionarInscripcion(@PathVariable int idOferta, @PathVariable int idUsuario) {				
		inscripcionOfertaInstitucionService.inscribirEstudiante(idOferta, idUsuario);
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true); 
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}
	
	
	
	@ApiOperation(value = "la institucion confirma la inscripcion del usuario estudiante a la oferta de institucion - Debe proporcionar el id de la oferta y el id del usuario estudiante")
	@PostMapping(url + "/confirmar")
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or hasAuthority('ROLE_INSTITUCION')")
	public ResponseEntity<Respuesta> confirmarInscripcion(@RequestParam(value = "idOferta") int idOferta,
			@RequestParam(value = "idUsuario") int idUsuario) {				
		inscripcionOfertaInstitucionService.confirmarInscripcionEstudiante(idOferta, idUsuario);
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true); 
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}
	

	
	@ApiOperation(value = "la institucion rechaza la inscripcion del usuario estudiante a la oferta de institucion - Debe proporcionar el id de la oferta y el id del usuario estudiante")
	@PostMapping(url + "/rechazar")
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or hasAuthority('ROLE_INSTITUCION')")
	public ResponseEntity<Respuesta> rechazarInscripcion(@RequestParam(value = "idOferta") int idOferta,
			@RequestParam(value = "idUsuario") int idUsuario) {				
		inscripcionOfertaInstitucionService.rechazarInscripcionEstudiante(idOferta, idUsuario);
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true); 
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}
	
	
	
	
	

}
