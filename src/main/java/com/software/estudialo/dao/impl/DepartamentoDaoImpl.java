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
import com.software.estudialo.dao.PaisDao;
import com.software.estudialo.entities.Departamento;
import com.software.estudialo.entities.Pais;

// TODO: Auto-generated Javadoc
/**
 * The Class DepartamentoDaoImpl.
 *
 * @author LUIS
 */
@Repository("departamentoDao")
public class DepartamentoDaoImpl implements DepartamentoDao{
	
	/** The logger. */
	private Logger logger = Logger.getLogger(DepartamentoDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/** The pais dao. */
	@Autowired
	PaisDao paisDao;

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.DepartamentoDao#obtenerDepartamentos()
	 */
	@Override
	public List<Departamento> obtenerDepartamentos() {
		logger.debug("obtenerDepartamentos -- Listando los departamentos");		
		String sql = "SELECT d.dep_id, d.dep_nombre, d.dep_pais FROM departamento d ORDER by d.dep_nombre";
		List<Departamento> listaDepartamentos = jdbcTemplate.query(sql, new DepartamentoRowMapper());
		return listaDepartamentos;		
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.DepartamentoDao#obtenerDepartamento(int)
	 */
	@Override
	public Departamento obtenerDepartamento(int id) {

		logger.debug("obtenerDepartamento -- Buscando departamento por Id ");

		String SQL = "SELECT d.dep_id, d.dep_nombre, d.dep_pais FROM departamento d WHERE d.dep_id = ?;";

		Departamento departamentoResponse = jdbcTemplate.query(SQL, new Object[] { id }, new DepartamentoResultSetExtractor());
		
		System.out.println("obtenerDepartamento -- Saliendo departamento por Id");
		return departamentoResponse;
	}

	
	/**
	 * The Class DepartamentoRowMapper.
	 */
	class DepartamentoRowMapper implements RowMapper<Departamento>{    
    	
    	/* (non-Javadoc)
	     * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	     */
	    @Override
	    public Departamento mapRow(ResultSet rs, int rowNum) throws SQLException {
    		Departamento departamento = new Departamento();
    		departamento.setId(rs.getInt("dep_id"));
    		departamento.setNombre(rs.getString("dep_nombre"));
    		Pais pais = paisDao.obtenerPais(rs.getInt("dep_pais"));
    		departamento.setPais(pais);
			return departamento;	        
	    }
	}
	
	/**
	 * The Class DepartamentoResultSetExtractor.
	 */
	class DepartamentoResultSetExtractor implements ResultSetExtractor<Departamento>{

		
		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
		 */
		@Override
		public Departamento extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			if (rs.next()) {				
				logger.debug("DepartamentoResultSetExtractor -- Obteniendo departamento");
				Departamento departamentoRow = new Departamento();
				departamentoRow.setId(rs.getInt("dep_id"));
				departamentoRow.setNombre(rs.getString("dep_nombre"));
				Pais pais = paisDao.obtenerPais(rs.getInt("dep_pais"));
	    		departamentoRow.setPais(pais);
				return departamentoRow;
			}
			return null;
		}
		
	}
	
	
}
