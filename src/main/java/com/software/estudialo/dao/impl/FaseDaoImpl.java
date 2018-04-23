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

import com.software.estudialo.dao.FaseDao;
import com.software.estudialo.dao.FaseItemDao;
import com.software.estudialo.dao.impl.MunicipioDaoImpl.MunicipioRowMapper;
import com.software.estudialo.entities.Departamento;
import com.software.estudialo.entities.Fase;
import com.software.estudialo.entities.FaseItem;
import com.software.estudialo.entities.Municipio;

/**
 * @author LUIS
 *
 */
@Repository("faseDao")
public class FaseDaoImpl implements FaseDao{

	/** The logger. */
	private Logger logger = Logger.getLogger(FaseDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	FaseItemDao faseItemDao;
	
	@Override
	public Fase obtenerFase(int id) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<Fase> obtenerFasesTest(int idTest) {
		logger.debug("obtenerFasesTest -- Listando las fases del test");		
		String sql = "select fas_id, fas_nombre, fas_numero from fase where fas_test = ? order by fas_numero";
		List<Fase> listaFases = jdbcTemplate.query(sql, new Object[] { idTest }, new FasesRowMapper());
		return listaFases;
	}
	
	
	class FasesRowMapper implements RowMapper<Fase>{
	        	
    	@Override
	    public Fase mapRow(ResultSet rs, int rowNum) throws SQLException {
    		Fase fase = new Fase();
    		fase.setId(rs.getInt("fas_id"));
    		fase.setNombre(rs.getString("fas_nombre"));
    		fase.setNumero(rs.getInt("fas_numero"));
    		
    		List<FaseItem> items = faseItemDao.obtenerFaseItems(rs.getInt("fas_id"));
    		fase.setItems(items);
			return fase;        
	    }
	}
	
	

}
