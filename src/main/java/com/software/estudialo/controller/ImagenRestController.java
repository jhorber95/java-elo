/**
 * 
 */
package com.software.estudialo.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.software.estudialo.entities.Respuesta;
import com.software.estudialo.service.impl.StorageService;
import com.software.estudialo.util.Constants;

import io.swagger.annotations.Api;

/**
 * @author LUIS
 *
 */
@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Api(value = "imagenes", description = "Operaciones pertenecientes a las imagenes")
public class ImagenRestController {

	/** The Constant url. */
	private static final String url = "/imagen";

	/** The logger. */
	private Logger logger = Logger.getLogger(CategoriaRestController.class);

	@Autowired
	StorageService storageService;
	
	/** --------------------------  Usuario **/

	@GetMapping(url + "/usuario/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> obtenerUsuarioImagen(@PathVariable String filename) {
		Resource file = storageService.loadFile(filename, Constants.USUARIO_LOCATION);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@PostMapping(url + "/usuario")
	public ResponseEntity<Respuesta> subirImagenUsuario(@RequestParam("idUsuario") int idUsuario,
			@RequestParam("file") MultipartFile file) {

		storageService.cambiarImagenUsuario(file, Constants.USUARIO_LOCATION, idUsuario);

		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Constants.IMAGEN_SUBIDA_CORRECTAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}
	
	
	/** --------------------------  Institucion **/
	
	
	@GetMapping(url + "/institucion/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> obtenerInstitucionImagen(@PathVariable String filename) {
		Resource file = storageService.loadFile(filename, Constants.INSTITUCION_LOCATION);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	
	
	@PostMapping(url + "/institucion")
	public ResponseEntity<Respuesta> subirImagenInstitucion(@RequestParam("idInstitucion") int idInstitucion, 
			@RequestParam("file") MultipartFile file) {

		storageService.cambiarImagenInstitucion(file, Constants.INSTITUCION_LOCATION, idInstitucion);

		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Constants.IMAGEN_SUBIDA_CORRECTAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}
	
	
	/** --------------------------  Eventos **/
	@GetMapping(url + "/evento/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> obtenerEventoImagen(@PathVariable String filename) {
		Resource file = storageService.loadFile(filename, Constants.EVENTO_LOCATION);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	
	
	@PostMapping(url + "/evento")
	public ResponseEntity<Respuesta> subirImagenEvento(@RequestParam("idEvento") int idEvento, 
			@RequestParam("file") MultipartFile file) {

		storageService.cambiarImagenEvento(file, Constants.EVENTO_LOCATION, idEvento);

		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Constants.IMAGEN_SUBIDA_CORRECTAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}
	
	
	/** --------------------------  Ofertas **/
	@GetMapping(url + "/oferta/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> obtenerOfertaImagen(@PathVariable String filename) {
		Resource file = storageService.loadFile(filename, Constants.OFERTA_LOCATION);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	
	
	@PostMapping(url + "/oferta")
	public ResponseEntity<Respuesta> subirImagenOferta(@RequestParam("idOferta") int idOferta, 
			@RequestParam("file") MultipartFile file) {

		storageService.cambiarImagenOferta(file, Constants.OFERTA_LOCATION, idOferta);

		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Constants.IMAGEN_SUBIDA_CORRECTAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}
	
	
	/** --------------------------  Test Vocacional **/
	@GetMapping(url + "/test/vocacional/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> obtenertTestImagen(@PathVariable String filename) {
		Resource file = storageService.loadFile(filename, Constants.TEST_LOCATION);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}


	@PostMapping(url + "/noticia")
	public ResponseEntity<Respuesta> uploadImgNoticia(@RequestParam("file") MultipartFile file) {

		String img = storageService.uploadNewsImage(file, Constants.NEWS_LOCATION);

		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Constants.IMAGEN_SUBIDA_CORRECTAMENTE);
		respuesta.setBody(img);
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	/** --------------------------  Test Vocacional **/
	@GetMapping(url + "/news/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getNewsImage(@PathVariable String filename) {
		logger.debug("Request to get file: "+ filename);
		Resource file = storageService.loadFile(filename, Constants.NEWS_LOCATION);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}




}
