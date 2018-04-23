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
import com.software.estudialo.entities.InscripcionOfertaFreelancer;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Respuesta;
import com.software.estudialo.service.InscripcionOfertaFreelancerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// TODO: Auto-generated Javadoc
/**
 * The Class InscripcionOfertaFreelancerRestController.
 *
 * @author LUIS
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Api(value="inscripciones de las ofertas de los freelancers del sistema", description="Operaciones pertenecientes a los ofertas freelancers")
public class InscripcionOfertaFreelancerRestController {
	
	/** The Constant url. */
	private static final String url = "/inscripcion/ofertaFreelancer";
	
	/** The logger. */
	private Logger logger = Logger.getLogger(InscripcionOfertaFreelancerRestController.class);

	/** The categoria service. */
	@Autowired
	InscripcionOfertaFreelancerService inscripcionOfertaFreelancerService;
	
	
	/**
	 * Listar datos de inscripciones a las ofertas de los freelancers seg�n la estructura que requiere DataTable de JQuery .
	 *
	 * @param search            parametro a buscar en la consulta
	 * @param start            limite de inicio de los registros
	 * @param length            limite de fin hasta donde se consultaran los registros
	 * @param draw            n�mero de veces que se realiza la consulta
	 * @param posicion            posici�n de la columna con la cual se quiere ordenar los
	 *            registros
	 * @param direccion            orden en que se quieren ordenar lso registros ascendente o
	 *            descendente
	 * @return retorna la lista de inscripciones a las ofertas de los freelancers segun los parametros enviados
	 */
	@GetMapping(url)
	@ApiOperation(value = "obtiene las lista de inscripciones a las ofertas de los freelancers con paginacion para datatables" )
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	public ResponseEntity<JSONRespuesta> getInscripcionesOfertasFreelancers(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {
		
		logger.debug("getInscripcionesOfertasFreelancers --- listando las Inscripciones Ofertas Freelancers");
		JSONRespuesta listInscripcionesOfertasFreelancers = new JSONRespuesta();
		listInscripcionesOfertasFreelancers = inscripcionOfertaFreelancerService.listarInscripcionOfertaFreelancers(search, start, length, draw, posicion, direccion);
		return new ResponseEntity<JSONRespuesta>(listInscripcionesOfertasFreelancers, HttpStatus.OK);
	}
	
	
	
	/**
	 * Listar datos de inscripciones a las ofertas de un freelancer especifico seg�n la estructura que requiere DataTable de JQuery .
	 *
	 * @param idUsuario the id usuario
	 * @param search            parametro a buscar en la consulta
	 * @param start            limite de inicio de los registros
	 * @param length            limite de fin hasta donde se consultaran los registros
	 * @param draw            n�mero de veces que se realiza la consulta
	 * @param posicion            posici�n de la columna con la cual se quiere ordenar los
	 *            registros
	 * @param direccion            orden en que se quieren ordenar lso registros ascendente o
	 *            descendente
	 * @return retorna la lista de inscripciones a las ofertas de un freelancer especifico segun los parametros enviados
	 */
	@GetMapping(url + "/freelancer")
	@ApiOperation(value = "obtiene las lista de inscripciones a las ofertas de un freelancer especifico con paginacion para datatables" )
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or hasAuthority('ROLE_FREELANCER')")
	public ResponseEntity<JSONRespuesta> getInscripcionesOfertasFreelancer(@RequestParam(value = "idUsuario") int idUsuario,
			@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {
		
		logger.debug("getInscripcionesOfertasFreelancer --- listando las Inscripciones Ofertas de un Freelancer");
		JSONRespuesta listInscripcionesOfertasFreelancer = new JSONRespuesta();
		listInscripcionesOfertasFreelancer = inscripcionOfertaFreelancerService.listarInscripcionOfertaFreelancer(idUsuario, search, start, length, draw, posicion, direccion);
		return new ResponseEntity<JSONRespuesta>(listInscripcionesOfertasFreelancer, HttpStatus.OK);
	}
	
	
	@PostMapping(url + "/preinscritos/freelancer")
	@ApiOperation(value = "obtiene las lista de inscripciones a las ofertas de un freelancer especifico con paginacion para datatables" )
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or hasAuthority('ROLE_FREELANCER')")
	public ResponseEntity<JSONRespuesta> getPreInscripcionesOfertasFreelancer(@RequestParam(value = "idUsuario") int idUsuario,
			@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {
		
		logger.debug("getInscripcionesOfertasFreelancer --- listando las Inscripciones Ofertas de un Freelancer");
		JSONRespuesta listInscripcionesOfertasFreelancer = new JSONRespuesta();
		listInscripcionesOfertasFreelancer = inscripcionOfertaFreelancerService.listarPreInscripcionesOfertaFreelancer(idUsuario, search, start, length, draw, posicion, direccion);
		return new ResponseEntity<JSONRespuesta>(listInscripcionesOfertasFreelancer, HttpStatus.OK);
	}
	
	
	
	
	
	/**
	 * Obtener inscripcion oferta freelancer.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@GetMapping(url + "/{id}")
	@ApiOperation(value = "obtiene la inscripcion a una oferta de un freelancer con el id" )
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or hasAuthority('ROLE_FREELANCER')")
    public ResponseEntity<InscripcionOfertaFreelancer> obtenerInscripcionOfertaFreelancer(@PathVariable int id) {
		logger.debug("obtenerInscripcionOfertaFreelancer -- Obtener inscripcion oferta freelancer");
		InscripcionOfertaFreelancer inscripcionOfertaFreelancer = inscripcionOfertaFreelancerService.obtenerinscripcionOfertaFreelancer(id);    
        return new ResponseEntity<InscripcionOfertaFreelancer>(inscripcionOfertaFreelancer, HttpStatus.OK);
    }	
	
		
	/**
	 * Adicionar inscripcion.
	 *
	 * @param idOferta the id oferta
	 * @param idUsuario the id usuario
	 * @return the response entity
	 */
	@ApiOperation(value = "preinscribe un estudiante a una oferta ofrecida por un freelancer - Debe proporcionar el id de la ofert y el id del usuario estudiante")
	@GetMapping(url + "/{idOferta}/{idUsuario}")
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or hasAuthority('ROLE_ESTUDIANTE')")
	public ResponseEntity<Respuesta> adicionarInscripcion(@PathVariable int idOferta, @PathVariable int idUsuario) {				
		inscripcionOfertaFreelancerService.inscribirEstudiante(idOferta, idUsuario);
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true); 
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "el freelancer confirma la inscripcion del usuario estudiante a la oferta de freelancer - Debe proporcionar el id de la oferta y el id del usuario estudiante")
	@PostMapping(url + "/confirmar")
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or hasAuthority('ROLE_FREELANCER')")
	public ResponseEntity<Respuesta> confirmarInscripcion(@RequestParam(value = "idOferta") int idOferta,
			@RequestParam(value = "idUsuario") int idUsuario) {				
		inscripcionOfertaFreelancerService.confirmarInscripcionEstudiante(idOferta, idUsuario);
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true); 
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "el freelancer rechaza la inscripcion del usuario estudiante a la oferta de freelancer - Debe proporcionar el id de la oferta y el id del usuario estudiante")
	@PostMapping(url + "/rechazar")
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or hasAuthority('ROLE_FREELANCER')")
	public ResponseEntity<Respuesta> rechazarInscripcion(@RequestParam(value = "idOferta") int idOferta,
			@RequestParam(value = "idUsuario") int idUsuario) {				
		inscripcionOfertaFreelancerService.rechazarInscripcionEstudiante(idOferta, idUsuario);
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true); 
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}
	

}
