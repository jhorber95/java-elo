/**
 * 
 */
package com.software.estudialo.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.software.estudialo.entities.Usuario;
import com.software.estudialo.entities.DataTableJson;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Respuesta;
import com.software.estudialo.service.UsuarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


// TODO: Auto-generated Javadoc
/**
 * The Class UsuarioRestController.
 *
 * @author LUIS
 */
@RestController
@CrossOrigin(origins = "*") 
@RequestMapping("/api")
@Api(value="usuarios del sistema", description="Operaciones pertenecientes a las usuarios")
public class UsuarioRestController {
	
	/** The Constant url. */
	private static final String url = "/usuario";
	
	/** The logger. */
	private Logger logger = Logger.getLogger(UsuarioRestController.class);

	/** The categoria service. */
	@Autowired
	UsuarioService usuarioService;
	
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	
	@PostMapping(url + "/perfil/cambiarPassword")
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or hasAuthority('ROLE_ESTUDIANTE') or hasAuthority('ROLE_INSTITUCION') or hasAuthority('ROLE_FREELANCER')")
	@ApiOperation(value = "Cambia el password" )
    public ResponseEntity<Respuesta> cambiarPassword(@RequestParam(value = "idUsuario") int idUsuario,
    		@RequestParam(value = "oldPassword") String oldPassword, @RequestParam(value = "newPassword") String newPassword) {
		logger.debug("cambiarPassword -- Cambiar password");
		usuarioService.cambiarPassword(idUsuario, oldPassword, newPassword);
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true); 
		respuesta.setMensaje(Respuesta.CLAVE_CAMBIADA_EXITOSAMENTE);
		respuesta.setBody(null);
        return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
    }	
	
	
	
	
	
	/**
	 * Listar datos de usuarios seg�n la estructura que requiere DataTable de JQuery .
	 *
	 * @param search            parametro a buscar en la consulta
	 * @param start            limite de inicio de los registros
	 * @param length            limite de fin hasta donde se consultaran los registros
	 * @param draw            n�mero de veces que se realiza la consulta
	 * @param posicion            posici�n de la columna con la cual se quiere ordenar los
	 *            registros
	 * @param direccion            orden en que se quieren ordenar lso registros ascendente o
	 *            descendente
	 * @return retorna la lista de usuarios segun los parametros enviados
	 */
//	@GetMapping(url + "/prueba")
//	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
//	@ApiOperation(value = "obtiene las lista de usuarios con paginacion para datatables" )
//	public ResponseEntity<JSONRespuesta> getUsuariosGet(@RequestParam(value = "search[value]", defaultValue = "") String search,
//			@RequestParam(value = "start", defaultValue = "1") int start,
//			@RequestParam(value = "length", defaultValue = "10") int length,
//			@RequestParam(value = "draw", defaultValue = "1") int draw,
//			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
//			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {
//		
//		logger.debug("getUsuarios --- listando los usuarios");
//		JSONRespuesta listUsuarios = new JSONRespuesta();
//		listUsuarios = usuarioService.listarUsuarios(search, start, length, draw, posicion, direccion);
//		return new ResponseEntity<JSONRespuesta>(listUsuarios, HttpStatus.OK);
//	}
	
	/**
	 * Listar datos de usuarios seg�n la estructura que requiere DataTable de JQuery .
	 *
	 * @param search            parametro a buscar en la consulta
	 * @param start            limite de inicio de los registros
	 * @param length            limite de fin hasta donde se consultaran los registros
	 * @param draw            n�mero de veces que se realiza la consulta
	 * @param posicion            posici�n de la columna con la cual se quiere ordenar los
	 *            registros
	 * @param direccion            orden en que se quieren ordenar lso registros ascendente o
	 *            descendente
	 * @return retorna la lista de usuarios segun los parametros enviados
	 */
	@PostMapping(url + "/dataTables")
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	@ApiOperation(value = "obtiene las lista de usuarios con paginacion para datatables" )
	public ResponseEntity<JSONRespuesta> getUsuariosPost(@RequestBody DataTableJson dataTableJson) {
		
		logger.debug("Valores de prueba===> search=" + dataTableJson.getSearch().getValue());
		logger.debug("Valores de prueba===> start=" + dataTableJson.getStart());
		logger.debug("Valores de prueba===> length=" + dataTableJson.getLength());
		logger.debug("Valores de prueba===> draw=" + dataTableJson.getDraw());
		logger.debug("Valores de prueba===> posicion=" + dataTableJson.getOrder().get(0).getPosicion());
		logger.debug("Valores de prueba===> direccion=" + dataTableJson.getOrder().get(0).getDireccion());
		
		String search = dataTableJson.getSearch().getValue();
		int start = dataTableJson.getStart();
		int length = dataTableJson.getLength();
		int draw = dataTableJson.getDraw();
		int posicion = dataTableJson.getOrder().get(0).getPosicion();
		
		
		logger.debug("getUsuarios --- listando los usuarios");
		JSONRespuesta listUsuarios = new JSONRespuesta();
		listUsuarios = usuarioService.listarUsuarios(search, start, length, draw, posicion, "asc");
		return new ResponseEntity<JSONRespuesta>(listUsuarios, HttpStatus.OK);
	}
	
	
	@PostMapping(url + "/obtenerPorUsername")
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or hasAuthority('ROLE_ESTUDIANTE') or hasAuthority('ROLE_INSTITUCION')")
	@ApiOperation(value = "obtiene la usuario con el username" )
    public ResponseEntity<Object> obtenerUsuarioPorUsername(@RequestParam(value = "username") String username) {
		logger.debug("obtenerUsuarioPorUsername -- Obtener usuario por username");
		Object objeto = usuarioService.obtenerUsuarioPorUsername(username);    
        return new ResponseEntity<Object>(objeto, HttpStatus.OK);
    }	
	
	
	/**
	 * Obtener usuario.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@GetMapping(url + "/{id}")
	@ApiOperation(value = "obtiene la usuario con el id" )
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable int id) {
		logger.debug("obtenerUsuario -- Obtener usuario");
		Usuario usuario = usuarioService.obtenerUsuario(id);    
        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }	
	
		
	/**
	 * Adicionar usuario.
	 *
	 * @param usuario the usuario
	 * @return the response entity
	 */
	@ApiOperation(value = "Adiciona usuario")
	@PostMapping(url)
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	public ResponseEntity<Respuesta> adicionarUsuario(@RequestBody Usuario usuario) {				
		usuarioService.agregarUsuario(usuario);
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true); 
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Sign Up usuario")
	@PostMapping(url + "/signup")
	public ResponseEntity<Respuesta> signUpUsuario(@RequestBody Usuario usuario) {				
		usuarioService.signUpUsuario(usuario);
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true); 
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		
//		Object object = usuarioService.obtenerUsuarioPorUsername(usuario.getEmail());
//		
//		
//		//genera token
			
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}

	/**
	 * Modificar usuario.
	 *
	 * @param id the id
	 * @param usuario the usuario
	 * @return the response entity
	 */
	@ApiOperation(value = "Modifica una usuario - proporciona el id y el objeto usuario")
	@PutMapping(url + "/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	public ResponseEntity<Respuesta> modificarUsuario(@PathVariable int id, @RequestBody Usuario usuario) {		
		usuarioService.modificarUsuario(id, usuario);		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "Modifica los datos del usuario - proporciona el objeto usuario")
	@PutMapping(url+ "/perfil/{id}")	
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or hasAuthority('ROLE_ESTUDIANTE')")
	public ResponseEntity<Respuesta> modificarUsuarioDatosPerfil(@PathVariable int id, @RequestBody Usuario usuario) {		
		usuarioService.modificarUsuarioDatosPerfil(id, usuario);		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);		
	}
	
	/**
	 * Eliminar usuario.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@ApiOperation(value = "Elimina una usuario (Cambio de estado a eliminado) - proporciona el id")
	@DeleteMapping(url + "/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	public ResponseEntity<Respuesta> eliminarUsuario(@PathVariable int id) {

		usuarioService.eliminarUsuario(id);
		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);	
		
	}

	
	

}
