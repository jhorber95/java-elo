package com.software.estudialo.service.impl;

import java.nio.charset.StandardCharsets;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.software.estudialo.entities.Mail;
import com.software.estudialo.service.MailService;


@Service("mailService")
public class MailServiceImpl implements MailService {
	
	 @Autowired
	 private JavaMailSender emailSender;

	 @Autowired
	 private SpringTemplateEngine templateEngine;

	@Override
	public void sendEmail(Mail mail) {
		 try {
	            MimeMessage message = emailSender.createMimeMessage();
	            
	            MimeMessageHelper helper = new MimeMessageHelper(message,
	                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
	                    StandardCharsets.UTF_8.name());

	            Context context = new Context();
	            context.setVariables(mail.getModel());
	            
	            String html = templateEngine.process("email-template_v2", context);

	            helper.setTo(mail.getTo());
	            helper.setText(html, true);
	            helper.setSubject(mail.getSubject());
	            helper.setFrom("info@estudialo.co");

	            emailSender.send(message);
	        } catch (Exception e){
	            throw new RuntimeException(e);
	        }
		
	}

}
