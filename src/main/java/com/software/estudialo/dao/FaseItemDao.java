/**
 * 
 */
package com.software.estudialo.dao;

import java.util.List;

import com.software.estudialo.entities.FaseItem;

/**
 * @author LUIS
 *
 */
public interface FaseItemDao {
	
	public FaseItem obtenerFaseItem(int id);
	
	public List<FaseItem> obtenerFaseItems(int idFase);

}
