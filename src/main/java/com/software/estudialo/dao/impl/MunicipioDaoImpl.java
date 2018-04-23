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

import com.software.estudialo.dao.DepartamentoDao;
import com.software.estudialo.dao.MunicipioDao;
import com.software.estudialo.entities.Municipio;
import com.software.estudialo.entities.Departamento;

// TODO: Auto-generated Javadoc
/**
 * The Class MunicipioDaoImpl.
 *
 * @author LUIS
 */
@Repository("municipioDao")
public class MunicipioDaoImpl implements MunicipioDao {
	
	/** The logger. */
	private Logger logger = Logger.getLogger(MunicipioDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	/** The departamento dao. */
	@Autowired
	DepartamentoDao departamentoDao;
	
	

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.MunicipioDao#obtenerMunicipios(int)
	 */
	@Override
	public List<Municipio> obtenerMunicipios(int idDepartamento) {
		logger.debug("obtenerMunicipios -- Listando los municipios");		
		String sql = "select mun_id, mun_nombre, mun_departamento from municipio where mun_departamento = ? order by mun_nombre";
		List<Municipio> listaMunicipios = jdbcTemplate.query(sql, new Object[] { idDepartamento }, new MunicipioRowMapper());
		return listaMunicipios;		
	}


	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.MunicipioDao#obtenerMunicipio(int)
	 */
	@Override
	public Municipio obtenerMunicipio(int id) {
		logger.debug("obtenerMunicipio -- Buscando municipio por Id ");

		String SQL = "select mun_id, mun_nombre, mun_departamento from municipio where mun_id = ?";

		Municipio municipioResponse = jdbcTemplate.query(SQL, new Object[] { id },
				new MunicipioResultSetExtractor());
		
		System.out.println("obtenerMunicipio -- Saliendo municipio por Id");
		return municipioResponse;
	}
	
	
	/**
	 * The Class MunicipioRowMapper.
	 */
	class MunicipioRowMapper implements RowMapper<Municipio>{
	    
    	/* (non-Javadoc)
    	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
    	 */
    	@Override
	    public Municipio mapRow(ResultSet rs, int rowNum) throws SQLException {
    		Municipio municipio = new Municipio();
    		municipio.setId(rs.getInt("mun_id"));
    		municipio.setNombre(rs.getString("mun_nombre"));
    		Departamento departamento = departamentoDao.obtenerDepartamento(rs.getInt("mun_departamento"));
    		municipio.setDepartamento(departamento);
			return municipio;	        
	    }
	}
	
	
	/**
	 * The Class MunicipioResultSetExtractor.
	 */
	class MunicipioResultSetExtractor implements ResultSetExtractor<Municipio>{

		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
		 */
		@Override
		public Municipio extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			if (rs.next()) {				
				logger.debug("MunicipioResultSetExtractor -- Obteniendo Municipio");
				Municipio municipio = new Municipio();
	    		municipio.setId(rs.getInt("mun_id"));
	    		municipio.setNombre(rs.getString("mun_nombre"));
	    		Departamento departamento = departamentoDao.obtenerDepartamento(rs.getInt("mun_departamento"));
	    		municipio.setDepartamento(departamento);
				return municipio;
			}
			return null;
		}
		
	}
	
	
	
	

}
