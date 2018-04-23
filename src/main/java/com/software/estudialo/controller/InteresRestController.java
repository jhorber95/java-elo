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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.software.estudialo.entities.Categoria;
import com.software.estudialo.entities.Interes;
import com.software.estudialo.entities.Oferta;
import com.software.estudialo.entities.RespuestaGeneral;
import com.software.estudialo.service.CategoriaService;
import com.software.estudialo.service.InteresService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author LUIS
 *
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Api(value="intereses de usuario", description="Operaciones pertenecientes al los intereses del usuario")
public class InteresRestController {
	
	/** The logger. */
	private Logger logger = Logger.getLogger(InteresRestController.class);

	/** The categoria service. */
	@Autowired
	InteresService interesService;
	
	
	/**
	 * Listar intereses.
	 *
	 * @return the response entity
	 */
	@GetMapping("/interes")	
	@ApiOperation(value = "Lista las intereses")
    public ResponseEntity<RespuestaGeneral> listarIntereses() {
		logger.debug("listarIntereses -- Listando Intereses");
		
		List<Interes> intereses = interesService.obtenerIntereses();  
		RespuestaGeneral rg = new RespuestaGeneral();
		rg.setExito(true);
		rg.setCodigo(200);
		rg.setData(intereses);	        
        return new ResponseEntity<RespuestaGeneral>(rg, HttpStatus.OK);
    }	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
