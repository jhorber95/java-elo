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
import com.software.estudialo.dao.JornadaDao;
import com.software.estudialo.entities.Jornada;


/**
 * @author LUIS
 *
 */
@Repository("jornadaDao")
public class JornadaDaoImpl implements JornadaDao{
	
	/** The logger. */
	private Logger logger = Logger.getLogger(JornadaDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Jornada obtenerJornada(int id) {
		logger.debug("obtenerJornada -- Buscando jornada por Id ");

		String SQL = "SELECT j.jor_id, j.jor_nombre, j.jor_descripcion FROM jornada j WHERE j.jor_id = ?;";

		Jornada jornadaResponse = jdbcTemplate.query(SQL, new Object[] { id },
				new JornadaResultSetExtractor());
		
		System.out.println("obtenerJornada -- Buscando jornada por Id");
		return jornadaResponse;
	}
	
	
	class JornadaResultSetExtractor implements ResultSetExtractor<Jornada>{

		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
		 */
		@Override
		public Jornada extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			if (rs.next()) {				
				logger.debug("JornadaResultSetExtractor -- Obteniendo Jornada");
				Jornada jornadaRow = new Jornada();
				jornadaRow.setId(rs.getInt("jor_id"));
				jornadaRow.setNombre(rs.getString("jor_nombre"));
				jornadaRow.setDescripcion(rs.getString("jor_descripcion"));
				return jornadaRow;
			}
			return null;
		}
		
	}
	

}
