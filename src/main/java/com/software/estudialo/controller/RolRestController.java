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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.software.estudialo.entities.Categoria;
import com.software.estudialo.entities.Respuesta;
import com.software.estudialo.entities.Rol;
import com.software.estudialo.entities.Usuario;
import com.software.estudialo.service.RolService;
import com.software.estudialo.service.UsuarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author LUIS
 *
 */
@RestController
@CrossOrigin(origins = "*") 
@RequestMapping("/api")
@Api(value="roles del sistema", description="Operaciones pertenecientes a las roles")
public class RolRestController {
	
	/** The Constant url. */
	private static final String url = "/rol";
	
	/** The logger. */
	private Logger logger = Logger.getLogger(RolRestController.class);

	
	@Autowired
	RolService rolService;
	
	@GetMapping(url)	
	@ApiOperation(value = "Lista los roles del sistema")
    public ResponseEntity<List<Rol>> listarRoles() {
		logger.debug("listarRoles -- Listando roles");
        List<Rol> roles = rolService.obtenerRoles();         
        return new ResponseEntity<List<Rol>>(roles, HttpStatus.OK);
    }
	
	
	@ApiOperation(value = "Adiciona un rol a un usuario")
	@PostMapping(url + "/usuario")
	public ResponseEntity<Respuesta> adicionarRolUsuario(@RequestParam(value = "idUsuario") int idUsuario, 
			@RequestParam(value = "idRol") int idRol ) {				
		rolService.agregarRolUsuario(idUsuario, idRol);
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true); 
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}
	

}
