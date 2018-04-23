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
import com.software.estudialo.dao.PublicidadDao;
import com.software.estudialo.entities.Publicidad;

/**
 * @author LUIS
 *
 */
@Repository("publicidadDao")
public class PublicidadDaoImpl implements PublicidadDao { 
	
	private Logger logger = Logger.getLogger(CategoriaDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	

	@Override
	public List<Publicidad> listarPublicidad() {
		
		logger.debug("listarPublicidad -- Buscando la publicidad ");
		
		String sql = "SELECT pu.pu_id, pu.pu_imagen, pu.pu_url FROM publicidad pu;";
		List<Publicidad> listaPublicidad = jdbcTemplate.query(sql, new PublicidadRowMapper());
		logger.debug("listarPublicidad --  Buscando la publicidad  ");
		
		return listaPublicidad;
		
	}
	
	class PublicidadRowMapper implements RowMapper<Publicidad>{	    
    	
    	@Override
	    public Publicidad mapRow(ResultSet rs, int rowNum) throws SQLException {
    		Publicidad publicidad = new Publicidad();
    		publicidad.setId(rs.getInt("pu_id"));
    		publicidad.setImagen(rs.getString("pu_imagen"));
    		publicidad.setUrl(rs.getString("pu_url"));
			return publicidad;
	        
	    }
	}

	@Override
	public Boolean modificarPublicidad(int idBanner, String newFileName, String url) {
		
		logger.debug("--- DENTRO DE MODIFICAR IMAGEN publicidad --------");

		String SQL1 = "UPDATE publicidad SET pu_imagen = ?, pu_url = ? WHERE pu_id = ?;";

		int resultado1 = jdbcTemplate.update(SQL1, newFileName, url, idBanner);

		if (resultado1 > 0) {
			logger.debug("--- IMAGEN MODIFICADA ------");
			return true;

		} else {
			return false;
		}

		
	}
	
	

}
