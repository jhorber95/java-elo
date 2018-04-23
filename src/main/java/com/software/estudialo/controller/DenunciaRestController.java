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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.software.estudialo.entities.DataTableJson;
import com.software.estudialo.entities.Denuncia;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Respuesta;
import com.software.estudialo.service.DenunciaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author LUIS
 *
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Api(value="denuncias", description="Operaciones pertenecientes a los denuncias")
public class DenunciaRestController {
	
	private static final String url = "/denuncia";
	
	private Logger logger = Logger.getLogger(DenunciaRestController.class);
	
	@Autowired
	DenunciaService denunciaService;
	
	
	@ApiOperation(value = "Adiciona denuncia")
	@PostMapping(url)
	@PreAuthorize("hasAuthority('ROLE_ESTUDIANTE')")
	public ResponseEntity<Respuesta> adicionarDenuncia(@RequestBody Denuncia denuncia) {				
		denunciaService.agregarDenuncia(denuncia);
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);			
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}	
	
	
	@GetMapping(url + "/dataTables")
	@ApiOperation(value = "obtiene las lista de denuncia con paginacion para datatables" ) 
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	public ResponseEntity<JSONRespuesta> getOfertas(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {
		
		logger.debug("getOfertas --- listando los ofertas");
		JSONRespuesta listOfertas = new JSONRespuesta();
		listOfertas = denunciaService.listarDenuncias(search, start, length, draw, posicion, direccion);
		return new ResponseEntity<JSONRespuesta>(listOfertas, HttpStatus.OK);
	}
	
	
	@PostMapping(url + "/dataTables")
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	@ApiOperation(value = "obtiene las lista de denuncias con paginacion para datatables" )
	public ResponseEntity<JSONRespuesta> getDenuncias(@RequestBody DataTableJson dataTableJson) {
		
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
		
		logger.debug("getDenuncias --- listando los denuncias");
		JSONRespuesta listDenuncias = new JSONRespuesta();
		listDenuncias = denunciaService.listarDenuncias(search, start, length, draw, posicion, "asc");
		return new ResponseEntity<JSONRespuesta>(listDenuncias, HttpStatus.OK);
	}
	 
	
	@ApiOperation(value = "Modifica una denuncia - proporciona el id y el objeto denuncia")
	@PutMapping(url + "/{id}")	
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	public ResponseEntity<Respuesta> modificarEvento(@PathVariable int id, @RequestBody Denuncia denuncia) {		
		denunciaService.modificarDenuncia(id, denuncia);		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		
	}
	
	
	
	
	

}
