/**
 * 
 */
package com.software.estudialo.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.software.estudialo.dao.InteresDao;
import com.software.estudialo.dao.impl.CategoriaDaoImpl.CategoriaRowMapper;
import com.software.estudialo.dao.impl.InteresDaoImpl.InteresResultSetExtractor;
import com.software.estudialo.dao.impl.MunicipioDaoImpl.MunicipioRowMapper;
import com.software.estudialo.entities.Categoria;
import com.software.estudialo.entities.Interes;
import com.software.estudialo.entities.Interes;
import com.software.estudialo.entities.Municipio;
import com.software.estudialo.entities.Interes;

/**
 * @author LUIS
 *
 */
@Repository("interesDao")
public class InteresDaoImpl implements InteresDao {

	/** The logger. */
	private Logger logger = Logger.getLogger(InteresDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Interes obtenerInteres(int id) {
		logger.debug("obtenerInteres -- Buscando Interes por Id ");

		String SQL = "SELECT i.inte_id, i.inte_nombre, i.inte_descripcion FROM interes i WHERE i.inte_id = ?;";

		Interes interesResponse = jdbcTemplate.query(SQL, new Object[] { id }, new InteresResultSetExtractor());

		System.out.println("obtenerInteres -- Saliendo Interes por Id");
		return interesResponse;
	}

	@Override
	public List<Interes> obtenerIntereses() {

		logger.debug("obtenerIntereses -- Buscando las intereses ");

		String sql = "SELECT i.inte_id, i.inte_nombre, i.inte_descripcion FROM interes i;";
		List<Interes> listaIntereses = jdbcTemplate.query(sql, new InteresRowMapper());
		logger.debug("obtenerIntereses -- Saliendo de busqueda de las intereses ");

		return listaIntereses;

	}

	class InteresRowMapper implements RowMapper<Interes> {

		@Override
		public Interes mapRow(ResultSet rs, int rowNum) throws SQLException {
			Interes interes = obtenerInteres(rs.getInt("inte_id"));
			return interes;
		}
	}

	class InteresResultSetExtractor implements ResultSetExtractor<Interes> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql
		 * .ResultSet)
		 */
		@Override
		public Interes extractData(ResultSet rs) throws SQLException, DataAccessException {

			if (rs.next()) {
				logger.debug("InteresResultSetExtractor -- Obteniendo Interes");
				Interes interesRow = new Interes();
				interesRow.setId(rs.getInt("inte_id"));
				interesRow.setNombre(rs.getString("inte_nombre"));
				interesRow.setDescripcion(rs.getString("inte_descripcion"));
				return interesRow;
			}
			return null;
		}

	}

	@Override
	public List<Interes> obtenerInteresesUsuario(int id) {
		logger.debug("obtenerInteresesUsuario -- Listando los intereses de usuario");
		String sql = "select uin.uin_id, uin.uin_usuario, uin.uin_interes FROM usuario_interes uin WHERE uin.uin_usuario = ?;";
		List<Interes> listaIntereses = jdbcTemplate.query(sql, new Object[] { id }, new InteresUsuarioRowMapper());
		return listaIntereses;
	}

	class InteresUsuarioRowMapper implements RowMapper<Interes> {

		@Override
		public Interes mapRow(ResultSet rs, int rowNum) throws SQLException {
			Interes interes = obtenerInteres(rs.getInt("uin_interes"));
			return interes;
		}
	}

	@Override
	public void actualizarInteresesUsuario(List<Interes> intereses, int idUsuario) {

		logger.debug("Borrando intereses antiguos de usuarios");
		// Borramos los intereses asignados al usuario
		String SQL1 = "DELETE  FROM usuario_interes WHERE uin_usuario = ?;";

		int resultado1 = jdbcTemplate.update(SQL1, idUsuario);

		logger.debug("asignando nuevos intereses de usuarios");

		// insert batch example

		String SQL2 = "INSERT INTO usuario_interes (uin_usuario, uin_interes) VALUES (?, ?)";

		jdbcTemplate.batchUpdate(SQL2, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Interes interes = intereses.get(i);
				ps.setInt(1, idUsuario);
				ps.setInt(2, interes.getId());
			}

			@Override
			public int getBatchSize() {
				return intereses.size();
			}

		});

	}

}
