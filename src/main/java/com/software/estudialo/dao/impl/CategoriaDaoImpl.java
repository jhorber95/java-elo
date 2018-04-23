/**
 * 
 */
package com.software.estudialo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.software.estudialo.dao.CategoriaDao;
import com.software.estudialo.entities.Categoria;

// TODO: Auto-generated Javadoc
/**
 * The Class CategoriaDaoImpl.
 *
 * @author LUIS
 */
@Repository("categoriaDao")
public class CategoriaDaoImpl implements CategoriaDao {

	/** The logger. */
	private Logger logger = Logger.getLogger(CategoriaDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.software.estudialo.dao.CategoriaDao#agregarCategoria(com.software.
	 * estudialo.entities.Categoria)
	 */
	@Override
	public Boolean agregarCategoria(Categoria categoria) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.software.estudialo.dao.CategoriaDao#modificarCategoria(com.software.
	 * estudialo.entities.Categoria)
	 */
	@Override
	public Boolean modificarCategoria(Categoria categoria) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.dao.CategoriaDao#obtenerCategoria(int)
	 */
	@Override
	public Categoria obtenerCategoria(int id) {

		logger.debug("obtenerCategoria -- Buscando categoria por Id ");

		String SQL = "SELECT c.cat_id, c.cat_nombre, c.cat_descripcion FROM categoria c WHERE c.cat_id = ?;";

		Categoria categoriaResponse = jdbcTemplate.query(SQL, new Object[] { id },
				new CategoriaResultSetExtractor());
		
		System.out.println("obtenerCategoria -- Saliendo categoria por Id");
		return categoriaResponse;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.dao.CategoriaDao#obtenerCategorias()
	 */
	@Override
	public List<Categoria> obtenerCategorias() {
		
		logger.debug("obtenerCategorias -- Buscando las categorias ");
		
		String sql = "SELECT cat.cat_id, cat.cat_nombre, cat.cat_descripcion FROM categoria cat;";
		List<Categoria> listaCategorias = jdbcTemplate.query(sql, new CategoriaRowMapper());
		logger.debug("obtenerCategorias -- Saliendo de busqueda de las categorias ");
		
		return listaCategorias;

	}

	/**
	 * The Class CategoriaRowMapper.
	 */
	class CategoriaRowMapper implements RowMapper<Categoria>{
	    
    	/* (non-Javadoc)
    	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
    	 */
    	@Override
	    public Categoria mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	Categoria categoria = new Categoria();
	    	categoria.setId(rs.getInt("cat_id"));
	    	categoria.setNombre(rs.getString("cat_nombre"));
	    	categoria.setDescripcion(rs.getString("cat_descripcion"));
			return categoria;
	        
	    }
	}
	
	/**
	 * The Class CategoriaResultSetExtractor.
	 */
	class CategoriaResultSetExtractor implements ResultSetExtractor<Categoria>{

		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
		 */
		@Override
		public Categoria extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			if (rs.next()) {				
				logger.debug("CategoriaResultSetExtractor -- Obteniendo Categoria");
				Categoria categoriaRow = new Categoria();
				categoriaRow.setId(rs.getInt("cat_id"));
				categoriaRow.setNombre(rs.getString("cat_nombre"));
				categoriaRow.setDescripcion(rs.getString("cat_descripcion"));
				return categoriaRow;
			}
			return null;
		}
		
	}
	
	
	

}
