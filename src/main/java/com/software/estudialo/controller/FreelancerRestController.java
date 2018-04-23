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

import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Respuesta;
import com.software.estudialo.entities.RespuestaGeneral;
import com.software.estudialo.entities.Usuario;
import com.software.estudialo.entities.DataTableJson;
import com.software.estudialo.entities.Freelancer;
import com.software.estudialo.entities.Institucion;
import com.software.estudialo.service.FreelancerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// TODO: Auto-generated Javadoc
/**
 * The Class FreelancerRestController.
 *
 * @author LUIS
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Api(value="freelancers del sistema", description="Operaciones pertenecientes a los freelancers")
public class FreelancerRestController {
	
	/** The Constant url. */
	private static final String url = "/freelancer";
	
	/** The logger. */
	private Logger logger = Logger.getLogger(FreelancerRestController.class);

	/** The categoria service. */
	@Autowired
	FreelancerService freelancerService;
	
	/**
	 * Listar datos de freelancers seg�n la estructura que requiere DataTable de JQuery .
	 *
	 * @param search            parametro a buscar en la consulta
	 * @param start            limite de inicio de los registros
	 * @param length            limite de fin hasta donde se consultaran los registros
	 * @param draw            n�mero de veces que se realiza la consulta
	 * @param posicion            posici�n de la columna con la cual se quiere ordenar los
	 *            registros
	 * @param direccion            orden en que se quieren ordenar lso registros ascendente o
	 *            descendente
	 * @return retorna la lista de freelancers segun los parametros enviados
	 */
	@GetMapping(url)
	@ApiOperation(value = "obtiene las lista de freelancers con paginacion para datatables" )
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	public ResponseEntity<JSONRespuesta> getFreelancers(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {
		
		logger.debug("getFreelancers --- listando los freelancers");
		JSONRespuesta listFreelancers = new JSONRespuesta();
		listFreelancers = freelancerService.listarFreelancers(search, start, length, draw, posicion, direccion);
		return new ResponseEntity<JSONRespuesta>(listFreelancers, HttpStatus.OK);
	}
	
	
	@PostMapping(url + "/dataTables")
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	@ApiOperation(value = "obtiene las lista de frelancers con paginacion para datatables" )
	public ResponseEntity<JSONRespuesta> getFreelancersPost(@RequestBody DataTableJson dataTableJson) {
		
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
		listUsuarios = freelancerService.listarFreelancersAdmin(search, start, length, draw, posicion, "asc");
		return new ResponseEntity<JSONRespuesta>(listUsuarios, HttpStatus.OK);
	}
	
	
	/**
	 * Obtener freelancer.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@GetMapping(url + "/{id}")
	@ApiOperation(value = "obtiene el freelancer con el id de usuario (idOfrece de oferta)" )
    public ResponseEntity<RespuestaGeneral> obtenerFreelancer(@PathVariable int id) {
		logger.debug("obtenerFreelancer -- Obtener freelancer");
		Freelancer freelancer = freelancerService.obtenerFreelancer(id);    
		
		List<Freelancer> freelancers = new ArrayList<>();
		freelancers.add(freelancer);
		RespuestaGeneral rg = new RespuestaGeneral();
		rg.setExito(true);
		rg.setCodigo(200);
		rg.setData(freelancers);
        return new ResponseEntity<RespuestaGeneral>(rg, HttpStatus.OK);
    }	
	
		
	/**
	 * Adicionar freelancer.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@ApiOperation(value = "Afilia freelancer desde el panel de usuario - Debe proporcionar el id de usuario que quiere ser freelancer")
	@GetMapping(url + "/afiliar/{id}")
	@PreAuthorize("hasAuthority('ROLE_ESTUDIANTE')")
	public ResponseEntity<Respuesta> adicionarFreelancer(@PathVariable int id) {				
		freelancerService.agregarFreelancer(id);
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true); 
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}
	
	
	
	
	@ApiOperation(value = "Adiciona freelancer desde parte publica  - Debe proporcionar el id de usuario que quiere ser freelancer")
	@PostMapping(url + "/signup")
	public ResponseEntity<Respuesta> adicionarFreelancerCompleto(@RequestBody Usuario usuario) {				
		freelancerService.agregarFreelancerCompleto(usuario);
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true); 
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}
	
	
	
	
	
	

}
