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
import com.software.estudialo.dao.TipoIdentificacionDao;
import com.software.estudialo.entities.TipoIdentificacion;

// TODO: Auto-generated Javadoc
/**
 * The Class TipoIdentificacionDaoImpl.
 *
 * @author LUIS
 */
@Repository("tipoIdentificacionDao")
public class TipoIdentificacionDaoImpl implements TipoIdentificacionDao {

	/** The logger. */
	private Logger logger = Logger.getLogger(TipoIdentificacionDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	
	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.TipoIdentificacionDao#obtenerTipoIdentificacion(int)
	 */
	@Override
	public TipoIdentificacion obtenerTipoIdentificacion(int id) {
		
		logger.debug("obtenerTipoIdentificacion -- Buscando tipo identificacion por Id ");

		String SQL = "SELECT ti.tii_id, ti.tii_nombre, ti.tii_acronimo FROM tipo_identificacion ti WHERE ti.tii_id = ?;";

		TipoIdentificacion tipoIdentificacionResponse = jdbcTemplate.query(SQL, new Object[] { id }, new tipoIdentificacionResultSetExtractor());
		
		logger.debug("obtenerTipoIdentificacion -- Saliendo tipo identificacion por Id");
		return tipoIdentificacionResponse;
	}
	
	
	/**
	 * The Class tipoIdentificacionResultSetExtractor.
	 */
	class tipoIdentificacionResultSetExtractor implements ResultSetExtractor<TipoIdentificacion>{

	
		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
		 */
		@Override
		public TipoIdentificacion extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			if (rs.next()) {				
				logger.debug("tipoIdentificacionResultSetExtractor -- Obteniendo tipo identificacion");
				TipoIdentificacion tipoIdentificacionRow = new TipoIdentificacion();
				tipoIdentificacionRow.setId(rs.getInt("tii_id"));
				tipoIdentificacionRow.setNombre(rs.getString("tii_nombre"));
				tipoIdentificacionRow.setAcronimo(rs.getString("tii_acronimo"));
				return tipoIdentificacionRow;
			}
			return null;
		}
		
	}

}
