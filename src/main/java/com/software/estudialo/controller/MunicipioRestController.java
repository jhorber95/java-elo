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

import com.software.estudialo.entities.Categoria;
import com.software.estudialo.entities.Municipio;
import com.software.estudialo.service.MunicipioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// TODO: Auto-generated Javadoc
/**
 * The Class MunicipioRestController.
 *
 * @author LUIS
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Api(value="municipios del sistema", description="Operaciones pertenecientes al tipo oferta")
public class MunicipioRestController {
	
	/** The logger. */
	private Logger logger = Logger.getLogger(MunicipioRestController.class);
	
	/** The municipio service. */
	@Autowired
	MunicipioService municipioService;
	
	
	/**
	 * Listar municipios.
	 *
	 * @param idDep the id dep
	 * @return the response entity
	 */
	@GetMapping("/municipio")
	@ApiOperation(value = "Lista los municipios")
    public ResponseEntity<List<Municipio>> listarMunicipios(@RequestParam("idDepartamento") int idDep) {
		logger.debug("listarMunicipios -- Listando Municipios");
        List<Municipio> municipios = municipioService.obtenerMunicipios(idDep);        
        return new ResponseEntity<List<Municipio>>(municipios, HttpStatus.OK);
    }
	
	

}
