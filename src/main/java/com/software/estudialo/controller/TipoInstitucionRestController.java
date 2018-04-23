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
import org.springframework.web.bind.annotation.RestController;

import com.software.estudialo.dao.TipoInstitucionDao;
import com.software.estudialo.entities.Categoria;
import com.software.estudialo.entities.TipoInstitucion;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author LUIS
 *
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Api(value="tipos institucion del sistema", description="Operaciones pertenecientes al los tipos institucion")
public class TipoInstitucionRestController {
	
	/** The logger. */
	private Logger logger = Logger.getLogger(TipoInstitucionRestController.class);
	
	@Autowired
	TipoInstitucionDao tipoInstitucionDao;
	
	
	@GetMapping("/tipoInstitucion")	
	@ApiOperation(value = "Lista las tipos institucion")
    public ResponseEntity<List<TipoInstitucion>> listarInstituciones() {
		logger.debug("listarInstituciones -- Listando tipos instituciones");
        List<TipoInstitucion> tipoInstituciones = tipoInstitucionDao.obtenerTiposInstitucion();      
        return new ResponseEntity<List<TipoInstitucion>>(tipoInstituciones, HttpStatus.OK);
    }	

}
