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
import com.software.estudialo.dao.TipoOfreceDao;
import com.software.estudialo.entities.TipoOfrece;


// TODO: Auto-generated Javadoc
/**
 * The Class TipoOfreceDaoImpl.
 *
 * @author LUIS
 */
@Repository("tipoOfreceDao")
public class TipoOfreceDaoImpl implements TipoOfreceDao {
	
	/** The logger. */
	private Logger logger = Logger.getLogger(TipoOfreceDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.TipoOfreceDao#obtenerTiposOfrece()
	 */
	@Override
	public List<TipoOfrece> obtenerTiposOfrece() {
		
		logger.debug("obtenerTiposOfrece -- Buscando las tipos ofrece ");		
		String sql = "SELECT tof.tof_id, tof.tof_nombre, tof.tof_descripcion FROM tipo_ofrece tof;";
		List<TipoOfrece> listaTiposOfrece = jdbcTemplate.query(sql, new TipoOfreceRowMapper());
		logger.debug("obtenerTiposOfrece -- Saliendo de busqueda de los tipo ofrece ");		
		return listaTiposOfrece;
	}
	

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.TipoOfreceDao#obtenerTipoOfrece(int)
	 */
	@Override
	public TipoOfrece obtenerTipoOfrece(int id) {
		logger.debug("obtenerTipoOfrece -- Buscando tipo ofrece por Id ");

		String SQL = "SELECT tof.tof_id, tof.tof_nombre, tof.tof_descripcion FROM tipo_ofrece tof WHERE tof.tof_id = ?;";

		TipoOfrece categoriaResponse = jdbcTemplate.query(SQL, new Object[] { id },
				new TipoOfreceResultSetExtractor());
		
		logger.debug("obtenerCategoria -- Saliendo categoria por Id");
		return categoriaResponse;
	}
	
	
	/**
	 * The Class TipoOfreceRowMapper.
	 */
	class TipoOfreceRowMapper implements RowMapper<TipoOfrece>{
	    
    	
    	/* (non-Javadoc)
	     * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	     */
	    @Override
	    public TipoOfrece mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	logger.debug("TipoOfreceRowMapper --- tipo ofrece row mapper");
    		TipoOfrece tipoOfrece = new TipoOfrece();
    		tipoOfrece.setId(rs.getInt("tof_id"));
    		tipoOfrece.setNombre(rs.getString("tof_nombre"));
    		tipoOfrece.setDescripcion(rs.getString("tof_descripcion"));
			return tipoOfrece;	        
	    }
	}

	
	
	/**
	 * The Class TipoOfreceResultSetExtractor.
	 */
	class TipoOfreceResultSetExtractor implements ResultSetExtractor<TipoOfrece>{

		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
		 */
		@Override
		public TipoOfrece extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			if (rs.next()) {				
				logger.debug("TipoOfreceResultSetExtractor -- Obteniendo TipoOfrece");
				TipoOfrece tipoOfrece = new TipoOfrece();
	    		tipoOfrece.setId(rs.getInt("tof_id"));
	    		tipoOfrece.setNombre(rs.getString("tof_nombre"));
	    		tipoOfrece.setDescripcion(rs.getString("tof_descripcion"));
				return tipoOfrece;
			}
			return null;
		}
		
	}


}
