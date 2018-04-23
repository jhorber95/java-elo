/**
 * 
 */
package com.software.estudialo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.software.estudialo.dao.FaseItemDao;
import com.software.estudialo.dao.InteligenciaDao;
import com.software.estudialo.dao.impl.FaseDaoImpl.FasesRowMapper;
import com.software.estudialo.entities.Fase;
import com.software.estudialo.entities.FaseItem;
import com.software.estudialo.entities.Inteligencia;

/**
 * @author LUIS
 *
 */
@Repository("faseItemDao")
public class FaseItemDaoImpl implements FaseItemDao{
	
	/** The logger. */
	private Logger logger = Logger.getLogger(InteligenciaDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	InteligenciaDao inteligenciaDao;
	
	@Override
	public FaseItem obtenerFaseItem(int id) {
		return null;
	}

	@Override
	public List<FaseItem> obtenerFaseItems(int idFase) {
		logger.debug("obtenerFaseItems -- Listando los items de la fase");		
		String sql = "select fit_id, fit_imagen, fit_inteligencia from fase_item where fit_fase = ? ";
		List<FaseItem> listaFaseItems = jdbcTemplate.query(sql, new Object[] { idFase }, new FaseItemsRowMapper());
		return listaFaseItems;
	}
	
	class FaseItemsRowMapper implements RowMapper<FaseItem>{
    	
    	@Override
	    public FaseItem mapRow(ResultSet rs, int rowNum) throws SQLException {
    		FaseItem faseItem = new FaseItem();
    		faseItem.setId(rs.getInt("fit_id"));
    		faseItem.setImagen(rs.getString("fit_imagen"));
    		faseItem.setSeleccionado(false);
    		Inteligencia inteligencia = inteligenciaDao.obtenerInteligencia(rs.getInt("fit_inteligencia"));
    		faseItem.setInteligencia(inteligencia);
			return faseItem;        
	    }
	}
	

}
