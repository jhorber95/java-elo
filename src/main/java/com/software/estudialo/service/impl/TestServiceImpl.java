/**
 * 
 */
package com.software.estudialo.service.impl;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.software.estudialo.dao.InstitucionDao;
import com.software.estudialo.dao.InteligenciaDao;
import com.software.estudialo.dao.OfertaDao;
import com.software.estudialo.dao.TestDao;
import com.software.estudialo.entities.Fase;
import com.software.estudialo.entities.FaseItem;
import com.software.estudialo.entities.Institucion;
import com.software.estudialo.entities.Inteligencia;
import com.software.estudialo.entities.Oferta;
import com.software.estudialo.entities.ResultadoVocacional;
import com.software.estudialo.entities.Test;
import com.software.estudialo.exception.DAOException;
import com.software.estudialo.exception.ObjectAlreadyExistException;
import com.software.estudialo.exception.ObjectNotFoundException;
import com.software.estudialo.exception.ValueNotPermittedException;
import com.software.estudialo.service.TestService;

/**
 * @author LUIS
 *
 */
@Service("testService")
public class TestServiceImpl implements TestService {
	
	/** The logger. */
	private Logger logger = Logger.getLogger(InstitucionServiceImpl.class);
	
	/** The categoriao dao. */
	@Autowired
	TestDao testDao;	
	
	@Autowired
	OfertaDao ofertaDao;
	
	@Autowired
	InteligenciaDao inteligenciaDao;
	

	@Override
	public Test obtenerTestVocacional() {
		logger.debug("obtenerTestVocacional -- obtener test vocacional");
		Test test = new Test();
		test = testDao.obtenerTestVocacional();
		return test;
	}
	
	
	@Override
	public List<Oferta> obtenerOfertasResultadTestVocacional(ResultadoVocacional resultadoVocacional) {
		
		logger.debug("obtenerOfertasResultadTestVocacional -- ");
		List<Oferta> ofertas = ofertaDao.obtenerOfertasResultadTestVocacional(resultadoVocacional);
		return ofertas;
	}


	@Override
	public ResultadoVocacional calificarTestVocacional(Test test) {
		
		logger.debug("calificarTestVocacional -- ");

		logger.debug("Iniciando Validaciones de calificacion de test");

		if (test.getId() <= 0 || test.getNombre() == null) {
			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			//Validar si existen maximo 3 fase items seleccionados por fase
			boolean seleccionCorrecta = seleccionCorrecta(test);
						
			if (!seleccionCorrecta) {
				throw new ObjectNotFoundException("No se escogieron al menos 2 o maximo 4 imagenes de cada fase");
			} else {				
				//contar items con true de los fase items para cada fase
				ResultadoVocacional resultado = calificar(test);				
				return resultado;
			}
		}				
	}
	
	
	
	
	private boolean seleccionCorrecta(Test test){
		
		for (Fase fase : test.getFases()) {
			//recorriendo cada fase
			int seleccionados = 0;
			
			for (FaseItem faseItem : fase.getItems()) {
				//recorriendo los items								
				if (faseItem.isSeleccionado()) {
					seleccionados = seleccionados + 1;
				}				
			}
			
			// minimo 2 maximo 4 imagenes
			if (seleccionados < 2 || seleccionados > 4) {
				return false;
			}
			
		}		
		
		return true;		
	}
	
	
	private ResultadoVocacional calificar(Test test){
		
		//Inteligencias 
		int inteligencia1 = 0;
		int inteligencia2 = 0;
		int inteligencia3 = 0;
		int inteligencia4 = 0;
		int inteligencia5 = 0;
		int inteligencia6 = 0;
		int inteligencia7 = 0;
		int inteligencia8 = 0;
		
		
		for (Fase fase : test.getFases()) {
			//recorriendo cada fase
						
			for (FaseItem faseItem : fase.getItems()) {
				//recorriendo los items								
				if (faseItem.isSeleccionado()) {
					
					switch (faseItem.getInteligencia().getId()) {
					case 1:
						inteligencia1 = inteligencia1 + 1;
						break;
					case 2:
						inteligencia2 = inteligencia2 + 1;
						break;
					case 3:
						inteligencia3 = inteligencia3 + 1;
						break;
					case 4:
						inteligencia4 = inteligencia4 + 1;
						break;
					case 5:
						inteligencia5 = inteligencia5 + 1;
						break;
					case 6:
						inteligencia6 = inteligencia6 + 1;
						break;
					case 7:
						inteligencia7 = inteligencia7 + 1;
						break;
					case 8:
						inteligencia8 = inteligencia8 + 1;
						break;
					default:
						break;
					}
					
				}				
			}
			
		}
		
		
		Map<String, Integer> unsortMap = new HashMap<String, Integer>();
		unsortMap.put("1", inteligencia1);
		unsortMap.put("2", inteligencia2);
		unsortMap.put("3", inteligencia3);
		unsortMap.put("4", inteligencia4);
		unsortMap.put("5", inteligencia5);
		unsortMap.put("6", inteligencia6);
		unsortMap.put("7", inteligencia7);
		unsortMap.put("8", inteligencia8);
		
		Map<String, Integer> sortedMap = sortByValue(unsortMap);	
		printMap(sortedMap);	
		
		Iterator iterator = sortedMap.keySet().iterator();
		
		ResultadoVocacional rv = new ResultadoVocacional();
		
		int i = 0;
		
		while(iterator.hasNext()){
			
			if (i > 3) {
				break;
			}

			i = i + 1;
			Object key = iterator.next();			
			System.out.println("Llave " + i + ": " + key);
			
			if (i == 1) {
				Inteligencia primero = inteligenciaDao.obtenerInteligencia(Integer.valueOf((String) key));
				rv.setPrimero(primero);
			}
			
			if (i == 2) {
				Inteligencia segundo = inteligenciaDao.obtenerInteligencia(Integer.valueOf((String) key));
				rv.setSegundo(segundo);
			}
			
			if (i == 3) {
				Inteligencia tercero = inteligenciaDao.obtenerInteligencia(Integer.valueOf((String) key));
				rv.setTercero(tercero);
			}
			
		}
		
		
		return rv;
		
	}
	
	
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> unsortMap) {

	    List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(unsortMap.entrySet());

	    Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
	        public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
	            //return (o1.getValue()).compareTo(o2.getValue());
	        	return (o2.getValue()).compareTo(o1.getValue());
	        }
	    });

	    Map<K, V> result = new LinkedHashMap<K, V>();
	    for (Map.Entry<K, V> entry : list) {
	        result.put(entry.getKey(), entry.getValue());
	    }

	    return result;

	}
		
	
	public static <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey()
                    + " Value : " + entry.getValue());
        }
    }


	
	

}
