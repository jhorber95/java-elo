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
import com.software.estudialo.dao.InteligenciaDao;
import com.software.estudialo.dao.impl.CategoriaDaoImpl.CategoriaRowMapper;
import com.software.estudialo.entities.Categoria;
import com.software.estudialo.entities.Inteligencia;

/**
 * @author LUIS
 *
 */
@Repository("inteligenciaDao")
public class InteligenciaDaoImpl implements InteligenciaDao{

	/** The logger. */
	private Logger logger = Logger.getLogger(InteligenciaDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	
	@Override
	public Inteligencia obtenerInteligencia(int id) {
		logger.debug("obtenerInteligencia -- Buscando inteligencia por Id ");

		String SQL = "SELECT i.int_id, i.int_nombre, i.int_descripcion FROM inteligencia i WHERE i.int_id = ?;";

		Inteligencia inteligenciaResponse = jdbcTemplate.query(SQL, new Object[] { id },
				new InteligenciaResultSetExtractor());
		
		System.out.println("obtenerInteligencia -- Saliendo inteligencia por Id");
		return inteligenciaResponse;
	}
	
	
	class InteligenciaResultSetExtractor implements ResultSetExtractor<Inteligencia>{

		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
		 */
		@Override
		public Inteligencia extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			if (rs.next()) {				
				logger.debug("InteligenciaResultSetExtractor -- Obteniendo inteligencia");
				Inteligencia inteligenciaRow = new Inteligencia();
				inteligenciaRow.setId(rs.getInt("int_id"));
				inteligenciaRow.setNombre(rs.getString("int_nombre"));
				inteligenciaRow.setDescripcion(rs.getString("int_descripcion"));
				return inteligenciaRow;
			}
			return null;
		}
		
	}


	@Override
	public List<Inteligencia> obtenerInteligenciasOferta(int idOferta) {
		logger.debug("obtenerInteligenciasOferta -- Buscando las inteligencias  ");
		
		String sql = "SELECT DISTINCT i.int_id, i.int_nombre, i.int_descripcion "
				+ "FROM inteligencia i "
				+ "INNER JOIN subcategoria_inteligencia sui ON i.int_id = sui.sin_inteligencia "
				+ "INNER JOIN subcategoria su ON sui.sin_subcategoria = su.sca_id "
				+ "INNER JOIN categoria c ON su.sca_categoria = c.cat_id "
				+ "INNER JOIN oferta o ON c.cat_id = o.ofe_categoria "
				+ "WHERE o.ofe_id = ?;";
		
		List<Inteligencia> listaInteligencias = jdbcTemplate.query(sql, new Object[] { idOferta }, new InteligenciaRowMapper());
		logger.debug("obtenerInteligenciasOferta -- Saliendo de busqueda de las inteligencias ");
		
		return listaInteligencias;
	}
	

	class InteligenciaRowMapper implements RowMapper<Inteligencia>{

    	@Override
	    public Inteligencia mapRow(ResultSet rs, int rowNum) throws SQLException {
    		Inteligencia inteligencia = new Inteligencia();
    		inteligencia.setId(rs.getInt("int_id"));
    		inteligencia.setNombre(rs.getString("int_nombre"));
    		inteligencia.setDescripcion(rs.getString("int_descripcion"));
			return inteligencia;
	        
	    }
	}
	
	
	
	
	

}
