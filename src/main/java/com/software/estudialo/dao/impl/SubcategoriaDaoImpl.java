/**
 * 
 */
package com.software.estudialo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.software.estudialo.dao.SubcategoriaDao;
import com.software.estudialo.dao.impl.CategoriaDaoImpl.CategoriaRowMapper;
import com.software.estudialo.entities.Categoria;
import com.software.estudialo.entities.Subcategoria;

/**
 * @author LUIS
 *
 */
@Repository("subcategoriaDao")
public class SubcategoriaDaoImpl implements SubcategoriaDao {
	
	/** The logger. */
	private Logger logger = Logger.getLogger(SubcategoriaDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;

//	@Override
//	public List<Subcategoria> obtenerSubcategorias(int idOferta) {
//		logger.debug("obtenerSubcategorias -- Buscando las subcategorias ");
//		
//		String sql = "SELECT sub.sca_id, sub.sca_nombre "
//				+ "FROM subcategoria sub "
//				+ "INNER JOIN oferta_subcategoria osu ON osu.osu_subcategoria = sub.sca_id "
//				+ "INNER JOIN oferta o ON o.ofe_id = osu.osu_oferta "
//				+ "WHERE o.ofe_id = ?;";
//		List<Subcategoria> listaSubcategorias = jdbcTemplate.query(sql, new Object[] {idOferta}, new SubcategoriaRowMapper());
//		logger.debug("obtenerSubcategorias -- Saliendo de busqueda de las subcategorias ");
//		
//		return listaSubcategorias;
//	}
	
	
	@Override
	public List<Subcategoria> obtenerSubcategorias(int idOferta) {
		logger.debug("obtenerSubcategorias -- Buscando las subcategorias ");
		
		String sql = "SELECT sub.sca_id, sub.sca_nombre "
				+ "FROM subcategoria sub "
				+ "INNER JOIN categoria cat ON sub.sca_categoria = cat.cat_id "
				+ "INNER JOIN oferta o ON cat.cat_id = o.ofe_categoria "
				+ "WHERE o.ofe_id = ?;";
		List<Subcategoria> listaSubcategorias = jdbcTemplate.query(sql, new Object[] {idOferta}, new SubcategoriaRowMapper());
		logger.debug("obtenerSubcategorias -- Saliendo de busqueda de las subcategorias ");
		
		return listaSubcategorias;
	}
	
	class SubcategoriaRowMapper implements RowMapper<Subcategoria>{
	    
    	/* (non-Javadoc)
    	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
    	 */
    	@Override
	    public Subcategoria mapRow(ResultSet rs, int rowNum) throws SQLException {
    		Subcategoria subcategoria = new Subcategoria();
    		subcategoria.setId(rs.getInt("sca_id"));
    		subcategoria.setNombre(rs.getString("sca_nombre"));
			return subcategoria;
	        
	    }
	}
	
	

}
