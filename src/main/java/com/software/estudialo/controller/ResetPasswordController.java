package com.software.estudialo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.software.estudialo.entities.Respuesta;
import com.software.estudialo.service.UsuarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*") 
@RequestMapping("/api")
@Api(value="Reset user password", description="Reset password by email")
public class ResetPasswordController {
	@Autowired
	UsuarioService usuarioService;
	
	@PostMapping("/reset-password")
	@ApiOperation(value = "Generate token" )
	public ResponseEntity<Respuesta> resetPassword(@RequestParam("email") String email){
		
		Object user = usuarioService.obtenerUsuarioPorUsername(email);
		
		
		
		
		return null;
	}

}
