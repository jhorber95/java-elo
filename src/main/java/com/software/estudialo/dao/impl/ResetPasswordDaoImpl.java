package com.software.estudialo.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Repository;

import com.software.estudialo.dao.ResetPasswordDao;
import com.software.estudialo.entities.Mail;
import com.software.estudialo.entities.PasswordResetToken;
import com.software.estudialo.entities.Usuario;
import com.software.estudialo.exception.ObjectAlreadyExistException;
import com.software.estudialo.service.MailService;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Repository("resetPasswordDao")
public class ResetPasswordDaoImpl implements ResetPasswordDao {
	
	/** The logger. */
	private Logger logger = Logger.getLogger(ResetPasswordDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired 
	private MailService emailService;

	@Override
	public void createPasswordToken(Usuario usuario) throws MailException {
		logger.debug("--- Insert  user  Token");
		
		// Se eliminan los tokens que puedan tener el usuario.
		String deleteToken = "DELETE FROM password_tokens WHERE \"pato_idUser\" = ? ";
		
		int resultado1 = jdbcTemplate.update(deleteToken, usuario.getId());
		
		if(resultado1 >0) {
			logger.debug("--- Eliminando token anteriores ------");
		} 
		
		String sql = "INSERT INTO password_tokens (\"pato_idUser\", pato_token, pato_expire) VALUES ( ?,?,?)"; 
		String token = UUID.randomUUID().toString();
		
		java.util.Date date = new java.util.Date(System.currentTimeMillis());
		java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());	
		// String expire = "30";
		
		int result = jdbcTemplate.update(sql,  usuario.getId(), token, timestamp); 
		
		if(result > 0) {
			String url = "http://localhost:4200/login/recuperar-cuenta/"+ usuario.getId() + "/" + token;
			//String url = "http://estudialo.co/login/recuperar-cuenta/"+ usuario.getId() + "/" + token;
			
			Mail mail = new Mail();
			 
			mail.setFrom("info@estudialo.co");
			mail.setTo(usuario.getEmail());
			mail.setSubject("Recuperación de contraseña");
			
			Map<String, Object> model = new HashMap<>();
			
			model.put("token", token);
			model.put("user", usuario);
			model.put("signature", "https://estudialo.co");
			model.put("resetUrl", url);
			mail.setModel(model);
		       
		    logger.debug("---- Send reset password E-mail");
		    
			emailService.sendEmail(mail);
		        			
		}
	}

	@Override
	public boolean existeToken(int idUsuario, String token) {
		logger.debug("--- DAO-  Verificando token ------");
		String sql = "SELECT COUNT(*) FROM password_tokens WHERE \"pato_idUser\" = ? AND pato_token = ? ";
		
		int resultado = jdbcTemplate.queryForObject(sql, new Object[] { idUsuario, token }, Integer.class  );
		
		if(resultado > 0 ) {
			logger.debug("--- token encontrado ------");
			System.out.println("Token num: " + resultado);
			return true;
		}else {
			throw new ObjectAlreadyExistException("No existe ningún token asignado ese usuario.");
		}
	}
}
