package com.software.estudialo;

import javax.annotation.Resource;

import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import com.software.estudialo.service.impl.StorageService;

@SpringBootApplication
public class EstudialoBackendApplication implements CommandLineRunner {
	
	@Resource
	StorageService storageService;
	

	public static void main(String[] args) {
		SpringApplication.run(EstudialoBackendApplication.class, args);
	}


	@Override
	public void run(String... arg0) throws Exception {
		
		//storageService.init();
		
	}
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
	 
	   return (container -> {
	        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/");

	        container.addErrorPages( error404Page);
	   });
	}

}
