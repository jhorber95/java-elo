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
import com.software.estudialo.dao.TipoInstitucionDao;
import com.software.estudialo.entities.TipoInstitucion;

// TODO: Auto-generated Javadoc
/**
 * The Class TipoInstitucionDaoImpl.
 *
 * @author LUIS
 */
@Repository("tipoInstitucionDao")
public class TipoInstitucionDaoImpl implements TipoInstitucionDao {
	
	/** The logger. */
	private Logger logger = Logger.getLogger(TipoInstitucionDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.TipoInstitucionDao#obtenerTipoInstitucion(int)
	 */
	@Override
	public TipoInstitucion obtenerTipoInstitucion(int id) {
		logger.debug("obtenerTipoInstitucion -- Buscando tipo institucion por Id ");

		String SQL = "SELECT ti.tins_id, ti.tins_nombre, ti.tins_descripcion FROM tipo_institucion ti WHERE ti.tins_id = ?;";

		TipoInstitucion tipoInstitucionResponse = jdbcTemplate.query(SQL, new Object[] { id },
				new TipoInstitucionResultSetExtractor());
		
		System.out.println("obtenerTipoInstitucion -- Saliendo tipo institucion por Id");
		return tipoInstitucionResponse;	
	}

	
	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.TipoInstitucionDao#obtenerTiposInstitucion()
	 */
	@Override
	public List<TipoInstitucion> obtenerTiposInstitucion() {
		logger.debug("obtenerTiposInstitucion -- Buscando las tipos institucion ");		
		String sql = "SELECT ti.tins_id, ti.tins_nombre, ti.tins_descripcion FROM tipo_institucion ti;";
		List<TipoInstitucion> listaTiposInstitucion = jdbcTemplate.query(sql, new TipoInstitucionRowMapper());
		logger.debug("obtenerTiposInstitucion -- Saliendo de busqueda de los tipos institucion ");		
		return listaTiposInstitucion;
	}
	
	
	/**
	 * The Class TipoInstitucionRowMapper.
	 */
	class TipoInstitucionRowMapper implements RowMapper<TipoInstitucion>{
	        	
    	/* (non-Javadoc)
	     * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	     */
	    @Override
	    public TipoInstitucion mapRow(ResultSet rs, int rowNum) throws SQLException {
    		logger.debug("TipoInstitucionRowMapper -- Obteniendo tipos institucion");
    		TipoInstitucion tipoInstitucion = new TipoInstitucion();
    		tipoInstitucion.setId(rs.getInt("tins_id"));
    		tipoInstitucion.setNombre(rs.getString("tins_nombre"));
    		tipoInstitucion.setDescripcion(rs.getString("tins_descripcion"));
			return tipoInstitucion;
	        
	    }
	}
	
	
	/**
	 * The Class TipoInstitucionResultSetExtractor.
	 */
	class TipoInstitucionResultSetExtractor implements ResultSetExtractor<TipoInstitucion>{

		
		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
		 */
		@Override
		public TipoInstitucion extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			if (rs.next()) {				
				logger.debug("TipoInstitucionResultSetExtractor -- Obteniendo tipos institucion");
				TipoInstitucion tipoInstitucion = new TipoInstitucion();
	    		tipoInstitucion.setId(rs.getInt("tins_id"));
	    		tipoInstitucion.setNombre(rs.getString("tins_nombre"));
	    		tipoInstitucion.setDescripcion(rs.getString("tins_descripcion"));
				return tipoInstitucion;
			}
			return null;
		}
		
	}
	
	
	
	
	
	

}
