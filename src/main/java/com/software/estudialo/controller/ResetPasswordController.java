package com.software.estudialo.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.software.estudialo.entities.Respuesta;
import com.software.estudialo.service.ResetPasswordService;
import com.software.estudialo.service.UsuarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*") 
@RequestMapping("/api")
@Api(value="Reset user password", description="Reset password by email")
public class ResetPasswordController {
	
	private Logger logger = Logger.getLogger(ResetPasswordController.class);
	
	private static final String url = "/recuperacion-password";
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	ResetPasswordService resetPassword;
	
	@PostMapping( url + "/generate-token")
	@ApiOperation(value = "Generar token para la recuperación de la contraseña" )
	public ResponseEntity<Respuesta> resetPassword(@RequestParam("email") String email){
		
		//Object user = usuarioService.obtenerUsuarioPorUsername(email);
		
		logger.debug("--- Entrando  a controlador de resetPassword");
		resetPassword.resetPassword(email);
		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true); 
		respuesta.setMensaje(Respuesta.TOKEN_RESET_PASSWORD);
		respuesta.setBody(null);
        return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}
	
	@PostMapping(url + "/check-token")
	@ApiOperation(value = "Se verifica si el token del usuario existe")
	public ResponseEntity<Respuesta> updatePassword(@RequestParam("idUser") int idUser, @RequestParam("token") String token){
		logger.debug(" --- Controller - Entrando a verificar token");
		
		resetPassword.existeToken(idUser, token);
		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true); 
		respuesta.setMensaje(Respuesta.TOKEN_RESET_PASSWORD_EXIST);
		respuesta.setBody(null);
        return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}
	
	@PostMapping(url + "/password-recovery-token")
	@ApiOperation(value="Cambiar contraseña mediante token enviado al correo")
	public ResponseEntity<Respuesta> cambiarPasswordByToken(@RequestParam("idUser") int idUser, @RequestParam("password") String password, @RequestParam("token") String token){
		logger.debug(" --- Entrando a actualizar contraña mediante token");
		
		usuarioService.resetPassword(idUser, password,token );
		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true); 
		respuesta.setMensaje(Respuesta.TOKEN_RESET_PASSWORD_EXIST);
		respuesta.setBody(null);
        return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		
	}

}
