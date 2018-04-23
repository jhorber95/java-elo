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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.software.estudialo.dao.TipoOfertaDao;
import com.software.estudialo.entities.TipoOferta;

// TODO: Auto-generated Javadoc
/**
 * The Class TipoOfertaDaoImpl.
 *
 * @author LUIS
 */
@Repository("tipoOfertaDao")
public class TipoOfertaDaoImpl implements TipoOfertaDao{
	
	/** The logger. */
	private Logger logger = Logger.getLogger(TipoOfertaDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.TipoOfertaDao#obtenerTiposOferta()
	 */
	@Override
	public List<TipoOferta> obtenerTiposOferta() {
		logger.debug("obtenerTipoOferta -- Buscando las tipos oferta ");
		
		String sql = "SELECT t.tio_id, t.tio_nombre, t.tio_descripcion FROM tipo_oferta t;";
		List<TipoOferta> listaTiposOferta = jdbcTemplate.query(sql, new TipoOfertaRowMapper());
		logger.debug("obtenerTipoOferta -- Saliendo de busqueda de los tipo oferta ");
		
		return listaTiposOferta;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.TipoOfertaDao#obtenerTipoOferta(int)
	 */
	@Override
	public TipoOferta obtenerTipoOferta(int id) {
		logger.debug("obtenerTipoOferta -- Buscando tipo oferta por Id ");

		String SQL = "SELECT t.tio_id, t.tio_nombre, t.tio_descripcion FROM tipo_oferta t WHERE t.tio_id = ?;";

		TipoOferta tipoOfertaResponse = jdbcTemplate.query(SQL, new Object[] { id },
				new TipoOfertaResultSetExtractor());
		
		logger.debug("obtenerTipoOferta -- Saliendo tipo oferta por Id");
		return tipoOfertaResponse;
	}
	
	
	
	/**
	 * The Class TipoOfertaRowMapper.
	 */
	class TipoOfertaRowMapper implements RowMapper<TipoOferta>{
	    
    	/* (non-Javadoc)
    	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
    	 */
    	@Override
	    public TipoOferta mapRow(ResultSet rs, int rowNum) throws SQLException {
    		logger.debug("TipoOfertaRowMapper --- tipo oferta row mapper");
    		TipoOferta tipoOferta = new TipoOferta();
    		tipoOferta.setId(rs.getInt("tio_id"));
    		tipoOferta.setNombre(rs.getString("tio_nombre"));
    		tipoOferta.setDescripcion(rs.getString("tio_descripcion"));
			return tipoOferta;	        
	    }
	}
	
	
	/**
	 * The Class TipoOfertaResultSetExtractor.
	 */
	class TipoOfertaResultSetExtractor implements ResultSetExtractor<TipoOferta>{

		
		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
		 */
		@Override
		public TipoOferta extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			if (rs.next()) {				
				logger.debug("TipoOfertaResultSetExtractor -- Obteniendo tipo oferta");
				TipoOferta tipoOferta = new TipoOferta();
	    		tipoOferta.setId(rs.getInt("tio_id"));
	    		tipoOferta.setNombre(rs.getString("tio_nombre"));
	    		tipoOferta.setDescripcion(rs.getString("tio_descripcion"));
				return tipoOferta;
			}
			return null;
		}
		
	}


	
	

}
