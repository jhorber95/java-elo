/**
 * 
 */
package com.software.estudialo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.software.estudialo.dao.FaseDao;
import com.software.estudialo.dao.TestDao;
import com.software.estudialo.dao.impl.InstitucionDaoImpl.InstitucionResultSetExtractor;
import com.software.estudialo.entities.Estado;
import com.software.estudialo.entities.Fase;
import com.software.estudialo.entities.Institucion;
import com.software.estudialo.entities.Oferta;
import com.software.estudialo.entities.ResultadoVocacional;
import com.software.estudialo.entities.Test;
import com.software.estudialo.entities.TipoInstitucion;

/**
 * @author LUIS
 *
 */
@Repository("testDao")
public class TestDaoImpl implements TestDao{
	
	/** The logger. */
	private Logger logger = Logger.getLogger(TestDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	FaseDao faseDao;
	

	@Override
	public Test obtenerTestVocacional() {
		logger.debug("obtenerTestVocacional -- Buscando test vocacional ");

		String SQL = "SELECT t.tes_id, t.tes_nombre FROM test t WHERE t.tes_id = 1;";

		Test testResponse = jdbcTemplate.query(SQL, new TestResultSetExtractor());
		
		System.out.println("obtenerTestVocacional -- Saliendo test vocacional ");
		return testResponse;
	}
	
	
	
	
	class TestResultSetExtractor implements ResultSetExtractor<Test>{
		
		@Override
		public Test extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			if (rs.next()) {				
				logger.debug("TestResultSetExtractor -- Obteniendo test vocacional");
				Test testRow = new Test();
				testRow.setId(rs.getInt("tes_id"));
				testRow.setNombre(rs.getString("tes_nombre"));
				
				List<Fase> fases = faseDao.obtenerFasesTest(rs.getInt("tes_id"));
				testRow.setFases(fases);
				return testRow;
			}
			return null;
		}
		
	}


	

	
	
	
	
	
}
