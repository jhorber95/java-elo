/**
 * 
 */
package com.software.estudialo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.software.estudialo.dao.DenunciaDao;
import com.software.estudialo.dao.EstadoDao;
import com.software.estudialo.dao.UsuarioDao;
import com.software.estudialo.dao.impl.DenunciaDaoImpl.DenunciaResultSetExtractor;
import com.software.estudialo.entities.Denuncia;
import com.software.estudialo.entities.Estado;
import com.software.estudialo.entities.Institucion;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Usuario;
import com.software.estudialo.util.Constants;
import com.software.estudialo.util.Encriptar;

/**
 * @author LUIS
 *
 */
@Repository("denunciaDao")
public class DenunciaDaoImpl implements DenunciaDao{
	
	/** The logger. */
	private Logger logger = Logger.getLogger(DenunciaDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/** The estado dao. */
	@Autowired
	EstadoDao estadoDao;
	
	@Autowired
	UsuarioDao usuarioDao;
	
	
	@Override
	public Denuncia obtenerDenuncia(int id) {
		logger.debug("obtenerDenuncia -- Buscando denuncia por Id ");

		String SQL = "SELECT d.den_id, d.den_mensaje, d.den_usuario, d.den_fecha, d.den_estado "
				+ "FROM denuncia d WHERE d.den_id = ?;";

		Denuncia denunciaResponse = jdbcTemplate.query(SQL, new Object[] { id }, new DenunciaResultSetExtractor());		
		logger.debug("obtenerDenuncia -- Saliendo denuncia por Id");
		return denunciaResponse;
	}
	

	@Override
	public Boolean agregarDenuncia(Denuncia denuncia) {
		logger.debug("--- AGREGANDO NUEVA DENUNCIA ------");

		try {

			String SQL1 = "INSERT INTO denuncia (den_mensaje, den_usuario, den_fecha, den_estado) VALUES (?, ?, ?, ?);";		
					
			Calendar cal = Calendar.getInstance();
			int year  = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int day   = cal.get(Calendar.DAY_OF_MONTH);
			cal.clear();
			cal.set(year, month, day);
			java.sql.Date today = new java.sql.Date(cal.getTimeInMillis());	
			
			
			int resultado1 = jdbcTemplate.update(SQL1, denuncia.getMensaje(), denuncia.getUsuario().getId(), today, Constants.ID_ESTADO_NO_REVISADO_DENUNCIA);

			if (resultado1 > 0) {
				logger.debug("Se inserto el denuncia");
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean modificarDenuncia(int id, Denuncia denuncia) {
		logger.debug("--- DENTRO DE MODIFICAR denuncia --------");

		String SQL1 = "UPDATE denuncia SET den_estado = ? WHERE den_id = ?;";
		
		int resultado1 = jdbcTemplate.update(SQL1, denuncia.getEstado().getId(), denuncia.getId());

		if (resultado1 > 0) {
			logger.debug("--- denuncia MODIFICADa -----");
			return true;
		} else {
			logger.debug("No se pudo MODIFICAR el denuncia");
			return false;
		}
	}

	@Override
	public Boolean buscarDenuncia(int idDenuncia) {
		logger.debug("------- BUSCANDO EXISTENCIA DE DENUNCIA ------");

		String sql = "SELECT COUNT(*) as cant FROM denuncia den WHERE den.den_id = ? AND den.den_estado IN (10, 11)";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { idDenuncia }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public JSONRespuesta listarDenuncias(String search, int start, int length, int draw, int posicion,
			String direccion) {
		
		JSONRespuesta respuesta = new JSONRespuesta();
		
		String[] campos = { "den.den_id", "den.den_mensaje", "u.usu_email", "den.den_fecha", "e.est_nombre" };

		int fin = start + length;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql
				+ "FROM denuncia den "
				+ "INNER JOIN usuario u ON den.den_usuario = u.usu_id "
				+ "INNER JOIN estado e ON den.den_estado = e.est_id "
				+ "WHERE den.den_estado IN (11) ";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + "AND (den.den_mensaje LIKE ? OR u.usu_email LIKE ? ";
			sql = sql + "OR e.est_nombre LIKE ? ) ";

			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { "%" + search + "%", "%" + search + "%",
					"%" + search + "%" }, Integer.class);
		}

		System.out.println("1. Numeros de denuncias filtrados: " + sql);
		
			
		sql = "SELECT den_id, den_mensaje, usu_email, den_fecha, est_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "den.den_id, den.den_mensaje, u.usu_email, den.den_fecha, e.est_nombre FROM denuncia den "
				+ "INNER JOIN usuario u ON den.den_usuario = u.usu_id "
				+ "INNER JOIN estado e ON den.den_estado = e.est_id "
				+ "WHERE den.den_estado IN (11) "
				+ "AND (den.den_mensaje LIKE ? OR u.usu_email LIKE ? OR e.est_nombre LIKE ? )) ";		
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";
		
		
		System.out.println("2. Numeros de denuncias filtrados:: " + sql);

		List<Denuncia> listaDenuncia = jdbcTemplate.query(sql, 
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Denuncia>() {

					public Denuncia mapRow(ResultSet rs, int rowNum) throws SQLException {
						Denuncia denuncia = obtenerDenuncia(rs.getInt("den_id"));
						return denuncia;				
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaDenuncia);

		return respuesta;
		
		
	}

	
	
	class DenunciaResultSetExtractor implements ResultSetExtractor<Denuncia>{		
		
		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
		 */
		@Override
		public Denuncia extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			if (rs.next()) {				
				logger.debug("DenunciaResultSetExtractor -- Obteniendo denuncia");
				Denuncia denuncia = new Denuncia();
				denuncia.setId(rs.getInt("den_id"));
				denuncia.setMensaje(rs.getString("den_mensaje"));
				denuncia.setFecha(rs.getDate("den_fecha"));								
				Usuario usuario = usuarioDao.obtenerUsuario(rs.getInt("den_usuario"));
				Estado estado = estadoDao.obtenerEstado(rs.getInt("den_estado"));	
				
				denuncia.setUsuario(usuario);
				denuncia.setEstado(estado);
				   		
	    		return denuncia;
			}
			return null;
		}		
		
	}

	
	

}
