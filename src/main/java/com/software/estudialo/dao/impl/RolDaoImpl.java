/**
 * 
 */
package com.software.estudialo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.software.estudialo.dao.RolDao;
import com.software.estudialo.dao.impl.CategoriaDaoImpl.CategoriaRowMapper;
import com.software.estudialo.entities.Categoria;
import com.software.estudialo.entities.Rol;
import com.software.estudialo.util.Constants;

/**
 * @author LUIS
 *
 */
@Repository("rolDao")
public class RolDaoImpl implements RolDao{
	
	/** The logger. */
	private Logger logger = Logger.getLogger(RolDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Set<Rol> buscarRolesPorUsername(String nombre) {
		logger.debug("roles del usuario ");

		String SQL = "SELECT r.rol_id, r.rol_nombre FROM rol r "
				+ "INNER JOIN rol_usuario ru ON ru.rolu_rol = r.rol_id "
				+ "INNER JOIN usuario u ON u.usu_id = ru.rolu_usuario "
				+ "WHERE u.usu_email = ?;";

		List<Rol> rolesUsuario = jdbcTemplate.query(SQL, new Object[] { nombre }, new RowMapper<Rol>(){

			@Override
			public Rol mapRow(ResultSet rs, int rowNum) throws SQLException {
				Rol rolRow = new Rol();
				rolRow.setId(rs.getInt("rol_id"));
				rolRow.setNombre(rs.getString("rol_nombre"));
				return rolRow;
			}			
		});
				
		Set<Rol> roles = new HashSet<Rol>(rolesUsuario);
		
		return roles;
	}

	

	@Override
	public List<Rol> obtenerRoles() {
		logger.debug("obtenerRoles -- Buscando los roles ");
		
		String sql = "SELECT r.rol_id, r.rol_nombre FROM rol r;";
		List<Rol> listaRoles = jdbcTemplate.query(sql, new RolRowMapper());
		logger.debug("obtenerRoles -- Saliendo de busqueda de las roles ");		
		return listaRoles;
	}

	@Override
	public Boolean agregarRolUsuario(int idUsuario, int idRol) {
		
		logger.debug("--- DENTRO DE adicionar rol a USUARIO --------");

		String SQL1 = "INSERT INTO rol_usuario (rolu_rol, rolu_usuario) VALUES (?, ?);";

		int resultado1 = jdbcTemplate.update(SQL1, idRol, idUsuario);

		if (resultado1 > 0) {
			logger.debug("---ASIGNADO CON EL  ROL ");

			logger.debug("Insertando en la tabla DEL ROL");
			String SQL2 = null;
			
			if (idRol == Constants.ID_ROL_ADMINISTRADOR) {
				SQL2 = "INSERT INTO administrador (adm_usuario) VALUES (?);";
			}else if (idRol == Constants.ID_ROL_FREELANCER) {
				SQL2 = "INSERT INTO freelancer (free_usuario) VALUES (?);";
			}
			
			// INSCRIBIR EN LA TABLA RESPECTIVA
			int resultado2 = jdbcTemplate.update(SQL2, idUsuario);

			if (resultado2 > 0) {
				logger.debug("--- INSCRITO EN LA TABLA DEL ROL MODIFICADO ------");
				return true;

			} else {
				return false;
			}
		} else {
			return false;
		}
		
	}

	@Override
	public Boolean buscarUsuarioRolExiste(int idUsuario, int idRol) {
		
		logger.debug("------- Buscando rol de usuario ");

		String sql = "SELECT COUNT(*) "
				+ "FROM rol_usuario ru "
				+ "WHERE ru.rolu_usuario = ? AND ru.rolu_rol = ?;";

		int count = jdbcTemplate.queryForObject(sql,
				new Object[] { idUsuario, idRol }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
		
	}

	
	class RolRowMapper implements RowMapper<Rol>{
	    
    	/* (non-Javadoc)
    	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
    	 */
    	@Override
	    public Rol mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	Rol rol = new Rol();
	    	rol.setId(rs.getInt("rol_id"));
	    	rol.setNombre(rs.getString("rol_nombre"));
			return rol;   
	    }
	}
	
	

}
