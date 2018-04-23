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

import com.software.estudialo.dao.ModalidadDao;
import com.software.estudialo.dao.impl.ModalidadDaoImpl.ModalidadResultSetExtractor;
import com.software.estudialo.entities.Modalidad;
import com.software.estudialo.entities.Modalidad;

/**
 * @author LUIS
 *
 */
@Repository("modalidadDao")
public class ModalidadDaoImpl implements ModalidadDao{
	
	/** The logger. */
	private Logger logger = Logger.getLogger(ModalidadDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	@Override
	public Modalidad obtenerModalidad(int id) {
		logger.debug("obtenerModalidad -- Buscando modalidad por Id ");

		String SQL = "SELECT m.mod_id, m.mod_nombre, m.mod_descripcion FROM modalidad m WHERE m.mod_id = ?;";

		Modalidad modalidadResponse = jdbcTemplate.query(SQL, new Object[] { id },
				new ModalidadResultSetExtractor());
		
		System.out.println("obtenerModalidad -- Buscando modalidad por Id");
		return modalidadResponse;
	}
	
	
	class ModalidadResultSetExtractor implements ResultSetExtractor<Modalidad>{

		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
		 */
		@Override
		public Modalidad extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			if (rs.next()) {				
				logger.debug("ModalidadResultSetExtractor -- Obteniendo Modalidad");
				Modalidad modalidadRow = new Modalidad();
				modalidadRow.setId(rs.getInt("mod_id"));
				modalidadRow.setNombre(rs.getString("mod_nombre"));
				modalidadRow.setDescripcion(rs.getString("mod_descripcion"));
				return modalidadRow;
			}
			return null;
		}
		
	}
	
	

}
