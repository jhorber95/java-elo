/**
 * 
 */
package com.software.estudialo.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.software.estudialo.dao.impl.CategoriaDaoImpl;
import com.software.estudialo.entities.Categoria;
import com.software.estudialo.service.CategoriaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// TODO: Auto-generated Javadoc
/**
 * The Class CategoriaRestController.
 *
 * @author LUIS
 * @since 25-06-2018
 *
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Api(value="categorias del sistema", description="Operaciones pertenecientes al los categorias")
public class CategoriaRestController {
	
	/** The logger. */
	private Logger logger = Logger.getLogger(CategoriaRestController.class);

	/** The categoria service. */
	@Autowired
	CategoriaService categoriaService;
	
	/**
	 * Listar categorias.
 * @since 25-06-2018
	 *
	 * @return the response entity
	 */
	@GetMapping("/categoria")	
	@ApiOperation(value = "Lista las categorias")
    public ResponseEntity<List<Categoria>> listarCategorias() {
		logger.debug("listarCategorias -- Listando Categorias");
        List<Categoria> categorias = categoriaService.obtenerCategorias();        
        return new ResponseEntity<List<Categoria>>(categorias, HttpStatus.OK);
    }	

}
