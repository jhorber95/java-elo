/**
 * 
 */
package com.software.estudialo.service;

import java.util.List;

import com.software.estudialo.entities.Oferta;
import com.software.estudialo.entities.ResultadoVocacional;
import com.software.estudialo.entities.Test;

/**
 * @author LUIS
 *
 */
public interface TestService {
	
	public Test obtenerTestVocacional();
	
	public ResultadoVocacional calificarTestVocacional(Test test); 
	
	public List<Oferta> obtenerOfertasResultadTestVocacional(ResultadoVocacional resultadoVocacional); 

}
