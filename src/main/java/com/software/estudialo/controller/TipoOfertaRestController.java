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
import com.software.estudialo.entities.TipoOferta;
import com.software.estudialo.service.TipoOfertaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// TODO: Auto-generated Javadoc
/**
 * The Class TipoOfertaRestController.
 *
 * @author LUIS
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Api(value="tipos de ofertas disponibles del sistema", description="Operaciones pertenecientes al tipo oferta")
public class TipoOfertaRestController {
	
	/** The logger. */
	private Logger logger = Logger.getLogger(TipoOfertaRestController.class);

	/** The categoria service. */
	@Autowired
	TipoOfertaService tipoOfertaService;
	
	/**
	 * Listar categorias.
	 *
	 * @return the response entity
	 */
	@ApiOperation(value = "Lista los tipos de oferta que se ofrecen")
	@GetMapping("/tipoOferta")
    public ResponseEntity<List<TipoOferta>> listarTiposOferta() {
		logger.debug("listarTiposOferta -- Listando Tipos Oferta");
        List<TipoOferta> tiposOferta = tipoOfertaService.obtenerTiposOferta();        
        return new ResponseEntity<List<TipoOferta>>(tiposOferta, HttpStatus.OK);
    }	
	
	
}
