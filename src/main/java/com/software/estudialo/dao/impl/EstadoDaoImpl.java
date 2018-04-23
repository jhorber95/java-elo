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

import com.software.estudialo.dao.EstadoDao;
import com.software.estudialo.dao.impl.CategoriaDaoImpl.CategoriaRowMapper;
import com.software.estudialo.entities.Categoria;
import com.software.estudialo.entities.Estado;

// TODO: Auto-generated Javadoc
/**
 * The Class EstadoDaoImpl.
 *
 * @author LUIS
 */
@Repository("estadoDao")
public class EstadoDaoImpl implements EstadoDao{
	
	/** The logger. */
	private Logger logger = Logger.getLogger(EstadoDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.EstadoDao#listarEstados()
	 */
	@Override
	public List<Estado> listarEstados() {
		logger.debug("listarEstadosPorEntidad -- Buscando las estados ");
		
		String sql = "SELECT est.est_id, est.est_nombre, est.est_descripcion FROM estado est";
		List<Estado> listaEstados = jdbcTemplate.query(sql, new EstadoRowMapper());
		logger.debug("listarEstadosPorEntidad -- Saliendo de busqueda de los estados");		
		return listaEstados;
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.EstadoDao#listarEstadosPorEntidad(java.lang.String)
	 */
	@Override
	public List<Estado> listarEstadosPorEntidad(String entidad) {
		logger.debug("listarEstadosPorEntidad -- Buscando las estados por entidad ");
		
		String sql = "SELECT est.est_id, est.est_nombre, est.est_descripcion FROM estado est "
				+ "INNER JOIN estado_entidad ee ON ee.ee_estado = est.est_id "
				+ "INNER JOIN entidad e ON e.ent_id = ee.ee_entidad "
				+ "WHERE e.ent_nombre = ?;";
		List<Estado> listaEstados = jdbcTemplate.query(sql, new Object[]{entidad}, new EstadoRowMapper());
		logger.debug("listarEstadosPorEntidad -- Saliendo de busqueda de los estados por entidad ");		
		return listaEstados;
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.EstadoDao#obtenerEstado(int)
	 */
	@Override
	public Estado obtenerEstado(int id) {
		logger.debug("obtenerEstado -- Buscando estado por Id ");
		String SQL = "SELECT e.est_id, e.est_nombre, e.est_descripcion FROM estado e WHERE e.est_id = ?;";
		Estado estado = jdbcTemplate.query(SQL, new Object[] { id }, new EstadoResultSetExtractor());
		return estado;
	}
	
	
	/**
	 * The Class EstadoRowMapper.
	 */
	class EstadoRowMapper implements RowMapper<Estado>{
	    
    	
    	/* (non-Javadoc)
	     * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	     */
	    @Override
	    public Estado mapRow(ResultSet rs, int rowNum) throws SQLException {
    		logger.debug("EstadoRowMapper -- Obteniendo estado");
			Estado estado = new Estado();
			estado.setId(rs.getInt("est_id"));
			estado.setNombre(rs.getString("est_nombre"));
			estado.setDescripcion(rs.getString("est_descripcion"));
			return estado;
	        
	    }
	}
	
	
	
	/**
	 * The Class EstadoResultSetExtractor.
	 */
	class EstadoResultSetExtractor implements ResultSetExtractor<Estado>{

	
		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
		 */
		@Override
		public Estado extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			if (rs.next()) {				
				logger.debug("EstadoResultSetExtractor -- Obteniendo estado");
				Estado estadoRow = new Estado();
				estadoRow.setId(rs.getInt("est_id"));
				estadoRow.setNombre(rs.getString("est_nombre"));
				estadoRow.setDescripcion(rs.getString("est_descripcion"));
				return estadoRow;			
			}
			return null;
		}
		
	}
	

}
