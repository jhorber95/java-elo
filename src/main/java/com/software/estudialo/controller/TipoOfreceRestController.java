package com.software.estudialo.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.software.estudialo.entities.TipoOfrece;
import com.software.estudialo.service.TipoOfreceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// TODO: Auto-generated Javadoc
/**
 * The Class TipoOfreceRestController.
 *
 * @author LUIS
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Api(value="tipos de entiedad que ofrece la oferta", description="Operaciones pertenecientes al tipo ofrece")
public class TipoOfreceRestController {
	
	/** The logger. */
	private Logger logger = Logger.getLogger(TipoOfreceRestController.class);


	/** The tipo ofrece service. */
	@Autowired
	TipoOfreceService tipoOfreceService;
	
	/**
	 * Listar tipos ofrece.
	 *
	 * @return the response entity
	 */
	@ApiOperation(value = "Lista los tipos de entidades que ofrecen la oferta")
	@GetMapping("/tipoOfrece")
    public ResponseEntity<List<TipoOfrece>> listarTiposOfrece() {
		logger.debug("listarTiposOfrece -- Listando Tipos Ofrece");
        List<TipoOfrece> tiposOfrece = tipoOfreceService.obtenerTiposOfrece();        
        return new ResponseEntity<List<TipoOfrece>>(tiposOfrece, HttpStatus.OK);
    }	
	
	
}
