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
import com.software.estudialo.entities.Departamento;
import com.software.estudialo.service.DepartamentoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


// TODO: Auto-generated Javadoc
/**
 * The Class DepartamentoRestController.
 * 
 * @author LUIS
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Api(value="departamentos del sistema", description="Operaciones pertenecientes al los departamentos")
public class DepartamentoRestController {
	
	/** The logger. */
	private Logger logger = Logger.getLogger(DepartamentoRestController.class);
	

	/** The departamento service. */
	@Autowired
	DepartamentoService departamentoService;
	
	
	/**
	 * Listar departamentos.
	 *
	 * @return the response entity
	 */
	@GetMapping("/departamento")
	@ApiOperation(value = "Lista los departamentos")
    public ResponseEntity<List<Departamento>> listarDepartamentos() {
		logger.debug("listarDepartamentos -- Listando Departamentos");
        List<Departamento> departamentos = departamentoService.obtenerDepartamentos();        
        return new ResponseEntity<List<Departamento>>(departamentos, HttpStatus.OK);
    }

}
