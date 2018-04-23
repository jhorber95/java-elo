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

import com.software.estudialo.dao.TipoEventoDao;
import com.software.estudialo.dao.impl.TipoIdentificacionDaoImpl.tipoIdentificacionResultSetExtractor;
import com.software.estudialo.entities.TipoEvento;
import com.software.estudialo.entities.TipoIdentificacion;

// TODO: Auto-generated Javadoc
/**
 * The Class TipoEventoDaoImpl.
 *
 * @author LUIS
 */
@Repository("tipoEventoDao")
public class TipoEventoDaoImpl implements TipoEventoDao{
	
	/** The logger. */
	private Logger logger = Logger.getLogger(TipoEventoDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.TipoEventoDao#obtenerTipoEvento(int)
	 */
	@Override
	public TipoEvento obtenerTipoEvento(int id) {
		
		logger.debug("obtenerTipoEvento -- Buscando tipo evento por Id ");

		String SQL = "SELECT te.tev_id, te.tev_nombre, te.tev_descripcion FROM tipo_evento te WHERE te.tev_id = ?;";

		TipoEvento tipoEventoResponse = jdbcTemplate.query(SQL, new Object[] { id }, new TipoEventoResultSetExtractor());
		
		logger.debug("obtenerTipoEvento -- Saliendo tipo evento por Id");
		return tipoEventoResponse;		
	}
	
	
	/**
	 * The Class TipoEventoResultSetExtractor.
	 */
	class TipoEventoResultSetExtractor implements ResultSetExtractor<TipoEvento>{

		
		
		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
		 */
		@Override
		public TipoEvento extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			if (rs.next()) {				
				logger.debug("TipoEventoResultSetExtractor -- Obteniendo tipo evento");
				TipoEvento tipoEventoRow = new TipoEvento();
				tipoEventoRow.setId(rs.getInt("tev_id"));
				tipoEventoRow.setNombre(rs.getString("tev_nombre"));
				tipoEventoRow.setDescripcion(rs.getString("tev_descripcion"));
				return tipoEventoRow;
			}
			return null;
		}
		
	}
	
	

}
