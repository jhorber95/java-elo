/**
 * 
 */
package com.software.estudialo.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.software.estudialo.entities.Categoria;
import com.software.estudialo.entities.Oferta;
import com.software.estudialo.entities.Publicidad;
import com.software.estudialo.entities.Respuesta;
import com.software.estudialo.entities.RespuestaGeneral;
import com.software.estudialo.service.PublicidadService;
import com.software.estudialo.service.impl.StorageService;
import com.software.estudialo.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author LUIS
 *
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Api(value="publicidad del sistema", description="Operaciones pertenecientes al los publicidad")
public class PublicidadRestController {
	
	private static final String url = "/publicidad";
	
	private Logger logger = Logger.getLogger(PublicidadRestController.class);

	@Autowired
	PublicidadService publicidadService;
	
	@Autowired
	StorageService storageService;
	
	
	@GetMapping(url)	
	@ApiOperation(value = "Lista las publidades (Banners)")
    public ResponseEntity<RespuestaGeneral> listarCategorias() {
		logger.debug("listar publicidad -- Listando Publicidad");
		
		List<Publicidad> listaPublicidad = publicidadService.listarPublicidad();
		RespuestaGeneral rg = new RespuestaGeneral();
		rg.setExito(true);
		rg.setCodigo(200);
		rg.setData(listaPublicidad);
        return new ResponseEntity<RespuestaGeneral>(rg, HttpStatus.OK);
    }	
	
	
	
	@GetMapping(url + "/banner/{filename:.+}")
	@ApiOperation(value = "Obtiene una imagen de publicidad (Banner)")
	@ResponseBody
	public ResponseEntity<Resource> obtenerPublicidadImagen(@PathVariable String filename) {
		Resource file = storageService.loadFile(filename, Constants.PUBLICIDAD_LOCATION);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	
	 
	@PostMapping("/api/publicidad/banner")
	//@PostMapping(url + "/banner")
	@ApiOperation(value = "Sube o edita una imagen de publicidad (Banner)")
	public ResponseEntity<Respuesta> subirImagenBanner(@RequestParam("idBanner") int idBanner,
			@RequestParam("url") String url, @RequestParam("file") MultipartFile file) {

		storageService.cambiarImagenBanner(file, Constants.PUBLICIDAD_LOCATION, idBanner, url);

		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Constants.IMAGEN_SUBIDA_CORRECTAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
