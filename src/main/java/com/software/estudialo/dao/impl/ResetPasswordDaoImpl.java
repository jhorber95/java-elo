package com.software.estudialo.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.mail.MailException;

import org.springframework.stereotype.Repository;

import com.software.estudialo.dao.ResetPasswordDao;
import com.software.estudialo.entities.Mail;
import com.software.estudialo.entities.PasswordResetToken;
import com.software.estudialo.entities.Usuario;
import com.software.estudialo.service.MailService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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
		String sql = "INSERT INTO password_tokens (\"pato_idUser\", pato_token, pato_expire) VALUES ( ?,?,?)"; 
		String token = UUID.randomUUID().toString();
		String expire = "30";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int result = jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstm.setInt(1, usuario.getId());
				pstm.setString(2, token );
				pstm.setString(3, expire);
				return pstm;
			}
		}, keyHolder);
		
		if(result > 0) {
			String url = "http://localhost/api/password-reset/?user="+ usuario.getId() + "&token=" +token;
			
			Mail mail = new Mail();
			 
			mail.setFrom("info@estudialo.co");
			mail.setTo(usuario.getEmail());
			mail.setSubject("Recuperación de contraseña");
			
			Map<String, Object> model = new HashMap<>();
			
			model.put("token", token);
			model.put("user", usuario);
			model.put("signature", "https://estudialo.co");
			model.put("resetUrl", url + "/reset-password?token=" + token);
			mail.setModel(model);
		       
		    logger.debug("---- Send reset password E-mail");
		    
			emailService.sendEmail(mail);
		        
		        
//			SimpleMailMessage mail = new SimpleMailMessage();
//			
//			mail.setTo(usuario.getEmail());
//			mail.setFrom("info@estidialo.co");
//			mail.setSubject("Recuperación de contraseña");
//			mail.setText(
//						"Hola " + usuario.getNombres() + " " + usuario.getApellidos()
//						+" \n. Parar recuperar tu contraseña has click en el siguiente enlace:" + url);
//			
//			
//			javaMailSender.send(mail);
			
		}
       
		
	}

}
