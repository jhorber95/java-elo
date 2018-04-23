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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.software.estudialo.entities.Publicador;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Oferta;
import com.software.estudialo.entities.Respuesta;
import com.software.estudialo.entities.RespuestaGeneral;
import com.software.estudialo.service.PublicadorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// TODO: Auto-generated Javadoc
/**
 * The Class PublicadorRestController.
 *
 * @author LUIS
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Api(value="publicadors del sistema", description="Operaciones pertenecientes a los publicadores")
public class PublicadorRestController {
	
/** The Constant url. */
private static final String url = "/publicador";
	
	/** The logger. */
	private Logger logger = Logger.getLogger(PublicadorRestController.class);

	/** The categoria service. */
	@Autowired
	PublicadorService publicadorService;
	
	
	/**
	 * Listar datos de publicadores seg�n la estructura que requiere DataTable de JQuery .
	 *
	 * @param search            parametro a buscar en la consulta
	 * @param start            limite de inicio de los registros
	 * @param length            limite de fin hasta donde se consultaran los registros
	 * @param draw            n�mero de veces que se realiza la consulta
	 * @param posicion            posici�n de la columna con la cual se quiere ordenar los
	 *            registros
	 * @param direccion            orden en que se quieren ordenar lso registros ascendente o
	 *            descendente
	 * @return retorna la lista de publicadores segun los parametros enviados
	 */
	@GetMapping(url)
	@ApiOperation(value = "obtiene las lista de publicadores con paginacion para datatables" )
	public ResponseEntity<JSONRespuesta> getPublicadors(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {
		
		logger.debug("getPublicadores --- listando los publicadores");
		JSONRespuesta listPublicadores = new JSONRespuesta();
		listPublicadores = publicadorService.listarPublicadores(search, start, length, draw, posicion, direccion);
		return new ResponseEntity<JSONRespuesta>(listPublicadores, HttpStatus.OK);
	}
	
	/**
	 * Obtener publicador.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@GetMapping(url + "/{id}")
	@ApiOperation(value = "obtiene el publicador con el id" )
    public ResponseEntity<RespuestaGeneral> obtenerPublicador(@PathVariable int id) {
		logger.debug("obtenerPublicador -- Obtener publicador");
		Publicador publicador = publicadorService.obtenerPublicador(id);  
		List<Publicador> publicadores = new ArrayList<>();
		publicadores.add(publicador);
		RespuestaGeneral rg = new RespuestaGeneral();
		rg.setExito(true);
		rg.setCodigo(200);
		rg.setData(publicadores);
        return new ResponseEntity<RespuestaGeneral>(rg, HttpStatus.OK);
    }	
	
		
	/**
	 * Adicionar publicador.
	 *
	 * @param idUsuario the id usuario
	 * @param idInstitucion the id institucion
	 * @return the response entity
	 */
	@ApiOperation(value = "Adiciona publicador - Debe proporcionar el id de usuario que quiere ser publicador y el id de la institucion para la que publica ")
	@GetMapping(url + "/afiliar/{idUsuario}/{idInstitucion}")
	public ResponseEntity<Respuesta> adicionarPublicador(@PathVariable int idUsuario, @PathVariable int idInstitucion) {				
		publicadorService.agregarPublicador(idUsuario, idInstitucion);
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true); 
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}
	
	
	

}
