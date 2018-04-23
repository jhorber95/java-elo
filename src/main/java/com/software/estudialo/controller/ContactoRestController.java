/**
 * 
 */
package com.software.estudialo.controller;

import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Respuesta;
import com.software.estudialo.exception.ObjectAlreadyExistException;
import com.software.estudialo.exception.ObjectNotFoundException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;




/**
 * @author LUIS
 *
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Api(value = "contacto del sistema", description = "Operaciones pertenecientes al contacto")
public class ContactoRestController {

	/** The logger. */
	private Logger logger = Logger.getLogger(ContactoRestController.class);

	@Autowired
	public JavaMailSender emailSender;

		
	@PostMapping("/contacto")
	@ApiOperation(value = "Envia correo de contacto al admin" ) 
	public ResponseEntity<Respuesta> sendContactEmail(@RequestParam(value = "nombres", defaultValue = "") String nombres,
			@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "celular", defaultValue = "No Tengo Celular") String celular,
			@RequestParam(value = "asunto", defaultValue = "") String asunto,
			@RequestParam(value = "mensaje", defaultValue = "") String mensaje) {
		
		logger.debug("sendContactEmail --- controller de email");
		Respuesta respuesta = new Respuesta();
		try {			
								
			if(nombres.equals("")  || email.equals("") || asunto.equals("") || mensaje.equals("")){
				throw new ObjectNotFoundException("Porfavor, llene los datos completos...");
			}
			
			
			String mensajeCompleto = "Hola, mi nombre es " + WordUtils.capitalizeFully(nombres) + ", mi correo es " 
					+ email.toLowerCase() + " y mi numero es: [" + celular + "]. Mi mensaje es el siguiente: " + WordUtils.capitalize(mensaje) + ". Gracias!";
			
			sendEmail(asunto, mensajeCompleto);
			respuesta.setExito(true);
			respuesta.setMensaje("El correo fue enviado!");
		} catch (Exception e) {
			e.printStackTrace();
			respuesta.setExito(false);
			respuesta.setMensaje("El correo no se envio!");
		}		
		
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}
	
	
	private void sendEmail(String subject, String mensajeCompleto) throws Exception {
		logger.debug("Enviando correo.....");
		
		MimeMessage message = emailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setTo("estudialoapp@gmail.com");		
		
		helper.setText(mensajeCompleto);

		helper.setSubject(subject);

		emailSender.send(message);
		logger.debug("Se envio el correo correo.....");

	}

}
