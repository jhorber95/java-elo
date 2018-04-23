/**
 * 
 */
package com.software.estudialo.dao;

import java.util.List;

import com.software.estudialo.entities.Fase;

/**
 * @author LUIS
 *
 */
public interface FaseDao {
	
	public List<Fase> obtenerFasesTest(int idTest);
	
	public Fase obtenerFase(int id);

}
