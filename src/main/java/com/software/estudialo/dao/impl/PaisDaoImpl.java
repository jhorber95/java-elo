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

import com.software.estudialo.dao.PaisDao;
import com.software.estudialo.entities.Pais;

// TODO: Auto-generated Javadoc
/**
 * The Class PaisDaoImpl.
 *
 * @author LUIS
 */
@Repository("paisDao")
public class PaisDaoImpl implements PaisDao{

	/** The logger. */
	private Logger logger = Logger.getLogger(PaisDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.PaisDao#obtenerPais(int)
	 */
	@Override
	public Pais obtenerPais(int id) {
		logger.debug("obtenerPais -- Buscando pais por Id ");

		String SQL = "SELECT p.pai_id, p.pai_nombre FROM pais p WHERE p.pai_id = ?;";

		Pais paisResponse = jdbcTemplate.query(SQL, new Object[] { id }, new PaisResultSetExtractor());
		
		System.out.println("obtenerPais -- Saliendo pais por Id");
		return paisResponse;
	}

	
	/**
	 * The Class PaisResultSetExtractor.
	 */
	class PaisResultSetExtractor implements ResultSetExtractor<Pais>{

	
		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
		 */
		@Override
		public Pais extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			if (rs.next()) {				
				logger.debug("PaisResultSetExtractor -- Obteniendo pais");
				Pais paisRow = new Pais();
				paisRow.setid(rs.getInt("pai_id"));
				paisRow.setNombre(rs.getString("pai_nombre"));				
				return paisRow;
			}
			return null;
		}
		
	}
	
	
	
}
