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
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	ResetPasswordService resetPassword;
	
	@PostMapping("/reset-password")
	@ApiOperation(value = "Generate token" )
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

}
