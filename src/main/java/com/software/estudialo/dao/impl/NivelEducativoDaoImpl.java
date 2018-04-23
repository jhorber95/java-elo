/**
 * 
 */
package com.software.estudialo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.software.estudialo.dao.NivelEducativoDao;
import com.software.estudialo.entities.NivelEducativo;
/**
 * @author LUIS
 *
 */
@Repository("nivelEducativoDao")
public class NivelEducativoDaoImpl implements NivelEducativoDao{
	
	/** The logger. */
	private Logger logger = Logger.getLogger(NivelEducativoDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	

	@Override
	public NivelEducativo obtenerNivelEducativo(int id) {
		logger.debug("obtenerNivelEducativo -- Buscando NivelEducativo por Id ");

		String SQL = "SELECT ne.ned_id, ne.ned_nombre, ne.ned_descripcion FROM nivel_educativo ne WHERE ne.ned_id = ?;";

		NivelEducativo nivelEducativoResponse = jdbcTemplate.query(SQL, new Object[] { id },
				new NivelEducativoResultSetExtractor());
		
		System.out.println("obtenerNivelEducativo -- Saliendo NivelEducativo por Id");
		return nivelEducativoResponse;
	}
	
	class NivelEducativoResultSetExtractor implements ResultSetExtractor<NivelEducativo>{

		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
		 */
		@Override
		public NivelEducativo extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			if (rs.next()) {				
				logger.debug("NivelEducativoResultSetExtractor -- Obteniendo NivelEducativo");
				NivelEducativo nivelEducativoRow = new NivelEducativo();
				nivelEducativoRow.setId(rs.getInt("ned_id"));
				nivelEducativoRow.setNombre(rs.getString("ned_nombre"));
				nivelEducativoRow.setDescripcion(rs.getString("ned_descripcion"));
				return nivelEducativoRow;
			}
			return null;
		}
		
	}
	

}
