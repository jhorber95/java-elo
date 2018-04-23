/**
 * 
 */
package com.software.estudialo.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.software.estudialo.entities.Estado;
import com.software.estudialo.service.EstadoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


// TODO: Auto-generated Javadoc
/**
 * The Class EstadoRestController.
 *
 * @author LUIS
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Api(value="estados del sistema", description="Operaciones pertenecientes al los estados")
public class EstadoRestController {
	
	/** The Constant url. */
	private static final String url = "/estado";
	
	/** The logger. */
	private Logger logger = Logger.getLogger(EstadoRestController.class);

	
	/** The estado service. */
	@Autowired
	EstadoService estadoService;
	
	
	/**
	 * Listar estados.
	 *
	 * @return the response entity
	 */
	@GetMapping(url)
	@ApiOperation(value = "Lista los estados")
    public ResponseEntity<List<Estado>> listarEstados() {
		logger.debug("listarEstados -- Listando estados");
        List<Estado> estados = estadoService.listarEstados();        
        return new ResponseEntity<List<Estado>>(estados, HttpStatus.OK);
    }	
	
	/**
	 * Buscar estados por entidad.
	 *
	 * @param entidad the entidad
	 * @return the response entity
	 */
	@GetMapping(url + "/entidad")
	@ApiOperation(value = "Lista los estados para cada entidad")
    public ResponseEntity<List<Estado>> buscarEstadosPorEntidad(@RequestParam("entidad") String entidad) {
		logger.debug("listarEstados -- Listando estados");
        List<Estado> estados = estadoService.listarEstadosPorEntidad(entidad); 
        return new ResponseEntity<List<Estado>>(estados, HttpStatus.OK);
    }
	
	
	

}
