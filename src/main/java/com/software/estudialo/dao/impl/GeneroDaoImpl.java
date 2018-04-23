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

import com.software.estudialo.dao.GeneroDao;
import com.software.estudialo.entities.Genero;

// TODO: Auto-generated Javadoc
/**
 * The Class GeneroDaoImpl.
 *
 * @author LUIS
 */
@Repository("generoDao")
public class GeneroDaoImpl implements GeneroDao{
	
	/** The logger. */
	private Logger logger = Logger.getLogger(GeneroDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.GeneroDao#obtenerGenero(int)
	 */
	@Override
	public Genero obtenerGenero(int id) {
		logger.debug("obtenerGenero -- Buscando genero por Id ");

		String SQL = "SELECT g.gen_id, g.gen_nombre, g.gen_acronimo FROM genero g WHERE g.gen_id = ?;";

		Genero generoResponse = jdbcTemplate.query(SQL, new Object[] { id }, new GeneroResultSetExtractor());
		
		System.out.println("obtenerGenero -- Saliendo genero por Id");
		return generoResponse;
	}
	
	
	/**
	 * The Class GeneroResultSetExtractor.
	 */
	class GeneroResultSetExtractor implements ResultSetExtractor<Genero>{

		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
		 */
		@Override
		public Genero extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			if (rs.next()) {				
				logger.debug("GeneroResultSetExtractor -- Obteniendo genero");
				Genero generoRow = new Genero();
				generoRow.setId(rs.getInt("gen_id"));
				generoRow.setNombre(rs.getString("gen_nombre"));
				generoRow.setAcronimo(rs.getString("gen_acronimo"));
				return generoRow;
			}
			return null;
		}
		
	}
	

}
