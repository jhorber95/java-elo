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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Oferta;
import com.software.estudialo.service.EstudianteService;
import com.software.estudialo.service.EventoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// TODO: Auto-generated Javadoc
/**
 * The Class EstudianteRestController.
 *
 * @author LUIS
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Api(value="estudiantes", description="Operaciones pertenecientes a los estudiantes")
public class EstudianteRestController {
	
	/** The Constant url. */
	private static final String url = "/estudiante";
	
	/** The logger. */
	private Logger logger = Logger.getLogger(EstudianteRestController.class);

	
	/** The estudiante service. */
	@Autowired
	EstudianteService estudianteService;
	
	
	/**
	 * Gets the inscripciones estudiante.
	 *
	 * @param idUsuario the id usuario
	 * @return the inscripciones estudiante
	 */
	@ApiOperation(value = "Lista las inscripciones del estudiante, debe proporcionar el id del usuario estudiante")
	@GetMapping(url + "/misInscripciones/{idUsuario}")
	@PreAuthorize("hasAuthority('ROLE_ESTUDIANTE')")
	public ResponseEntity<List<Object>> getInscripcionesEstudiante(@PathVariable(value = "idUsuario") int idUsuario) {
		
		logger.debug("getInscripcionesEstudiante --- listando las inscripciones del estudiante");
		List<Object> listaInscripcionesEstudiantes = new ArrayList<Object>();
		listaInscripcionesEstudiantes = estudianteService.listarInscripcionesEstudiante(idUsuario);
		return new ResponseEntity<List<Object>>(listaInscripcionesEstudiantes, HttpStatus.OK);
	}
	
	
	
}
