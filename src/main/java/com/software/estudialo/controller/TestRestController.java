/**
 * 
 */
package com.software.estudialo.controller;

import java.util.ArrayList;
import java.util.List;

import com.software.estudialo.entities.*;
import com.software.estudialo.service.TestHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.software.estudialo.service.TestService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author LUIS
 *
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Api(value="test vocacional", description="Operaciones pertenecientes al test vocacional")
public class TestRestController {
		
	/** The Constant url. */
	private static final String url = "/test";
	
	/** The logger. */
	private Logger logger = LoggerFactory.getLogger(TestRestController.class);
	
	
	@Autowired
	TestService testService;

	private final TestHistoryService testHistoryService;

	public TestRestController(TestHistoryService testHistoryService) {
		this.testHistoryService = testHistoryService;
	}


	@GetMapping(url + "/vocacional")
	@ApiOperation(value = "obtiene el test vocacional " )
    public ResponseEntity<RespuestaGeneral> obtenerTestVocacional() {
		logger.debug("obtenerTestVocacional -- Obtener Test Vocacional");
		Test test = testService.obtenerTestVocacional();   
		
		List<Test> tests = new ArrayList<>();
		tests.add(test);
		RespuestaGeneral rg = new RespuestaGeneral();
		rg.setExito(true);
		rg.setCodigo(200);
		rg.setData(tests);
        return new ResponseEntity<RespuestaGeneral>(rg, HttpStatus.OK);
    }	
	
	@ApiOperation(value = "Calificar Test Vocacional")
	@PostMapping(url + "/vocacional")
	public ResponseEntity<RespuestaGeneral> calificarTestVocacional(@RequestBody Test test) {				
		
		ResultadoVocacional rv = testService.calificarTestVocacional(test);
				
		List<ResultadoVocacional> rss = new ArrayList<>();
		rss.add(rv);	
		RespuestaGeneral rg = new RespuestaGeneral();
		rg.setExito(true);
		rg.setCodigo(200);
		rg.setData(rss);
		return new ResponseEntity<RespuestaGeneral>(rg, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Obtener ofertas segun los resultados del Test Vocacional -- proporcionan el Objeto ResultadoVocacional ")
	@PostMapping(url + "/vocacional/ofertas")
	public ResponseEntity<RespuestaGeneral> obtenerOfertasResultadTestVocacional(@RequestBody ResultadoVocacional resultado) {				
		logger.debug("obtenerOfertasResultadTestVocacional -- Obtener Ofertas");
		List<Oferta> ofertas = testService.obtenerOfertasResultadTestVocacional(resultado);		
				
		RespuestaGeneral rg = new RespuestaGeneral();
		rg.setExito(true);
		rg.setCodigo(200);
		rg.setData(ofertas);
        return new ResponseEntity<RespuestaGeneral>(rg, HttpStatus.OK);
        
	}

	@PostMapping(url + "/vocacional/save-user-test")
	public ResponseEntity<RespuestaGeneral> saveUserTest(@RequestBody UserTest userTest) {
		logger.debug("save user test, {}", userTest);

		List<TestHistory> reponse = testHistoryService.setupUserData(userTest);
		if (reponse.size()  == 0) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			RespuestaGeneral rg = new RespuestaGeneral();
			rg.setExito(true);
			rg.setCodigo(200);
			rg.setData(reponse);
			return new ResponseEntity<>(rg, HttpStatus.OK);
		}
	}
	
	
	

}
