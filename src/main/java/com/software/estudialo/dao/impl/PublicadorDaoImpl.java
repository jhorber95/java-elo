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
import com.software.estudialo.dao.GeneroDao;
import com.software.estudialo.dao.InstitucionDao;
import com.software.estudialo.dao.MunicipioDao;
import com.software.estudialo.dao.PublicadorDao;
import com.software.estudialo.dao.TipoEventoDao;
import com.software.estudialo.dao.TipoIdentificacionDao;
import com.software.estudialo.dao.impl.FreelancerDaoImpl.FreelancerResultSetExtractor;
import com.software.estudialo.entities.Estado;
import com.software.estudialo.entities.Freelancer;
import com.software.estudialo.entities.Genero;
import com.software.estudialo.entities.Institucion;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Municipio;
import com.software.estudialo.entities.Publicador;
import com.software.estudialo.entities.TipoEvento;
import com.software.estudialo.entities.TipoIdentificacion;
import com.software.estudialo.util.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class PublicadorDaoImpl.
 *
 * @author LUIS
 */
@Repository("publicadorDao")
public class PublicadorDaoImpl implements PublicadorDao{
	
	/** The logger. */
	private Logger logger = Logger.getLogger(PublicadorDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/** The municipio dao. */
	@Autowired
	MunicipioDao municipioDao;
	
	/** The estado dao. */
	@Autowired
	EstadoDao estadoDao;
	
	/** The genero dao. */
	@Autowired
	GeneroDao generoDao;
	
	/** The tipo identificacion dao. */
	@Autowired
	TipoIdentificacionDao tipoIdentificacionDao;
		
	/** The institucion dao. */
	@Autowired
	InstitucionDao institucionDao;

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.PublicadorDao#obtenerPublicador(int)
	 */
	@Override
	public Publicador obtenerPublicador(int id) {
		
		logger.debug("obtenerPublicador -- Buscando publicador por Id ");

		String SQL = "SELECT u.usu_id, u.usu_email, u.usu_estado, p.per_tipo_identificacion, p.per_nombres, p.per_apellidos, p.per_municipio, "
				+ "p.per_telefono, p.per_identificacion, p.per_genero, pu.pub_institucion "
				+ "FROM publicador pu "
				+ "INNER JOIN usuario u ON u.usu_id = pu.pub_usuario "
				+ "INNER JOIN persona p ON p.per_id = u.usu_persona "
				+ "WHERE pu.pub_id = ?;";		
		
		Publicador publicadorResponse = jdbcTemplate.query(SQL, new Object[] { id }, new PublicadorResultSetExtractor());
		
		logger.debug("obtenerPublicador -- Saliendo publicador por Id");
		return publicadorResponse;
		
	}

	
	/**
	 * The Class PublicadorResultSetExtractor.
	 */
	class PublicadorResultSetExtractor implements ResultSetExtractor<Publicador>{

				
		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
		 */
		@Override
		public Publicador extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			if (rs.next()) {				
				logger.debug("PublicadorResultSetExtractor -- Obteniendo publicador");
				Publicador publicadorRow = new Publicador();
				// El id es el del usuario
				publicadorRow.setId(rs.getInt("usu_id"));
				publicadorRow.setNombres(rs.getString("per_nombres"));
				publicadorRow.setApellidos(rs.getString("per_apellidos"));
				publicadorRow.setTelefono(rs.getString("per_telefono"));
				publicadorRow.setIdentificacion(rs.getString("per_identificacion"));
				publicadorRow.setEmail(rs.getString("usu_email"));				
				
				Municipio municipio = municipioDao.obtenerMunicipio(rs.getInt("per_municipio"));
				Estado estado = estadoDao.obtenerEstado(rs.getInt("usu_estado"));
				Genero genero = generoDao.obtenerGenero(rs.getInt("per_genero"));
				TipoIdentificacion tipoIdentificacion = tipoIdentificacionDao.obtenerTipoIdentificacion(rs.getInt("per_tipo_identificacion"));
				Institucion institucion = institucionDao.obtenerInstitucion(rs.getInt("pub_institucion"));				
				
				publicadorRow.setGenero(genero);
				publicadorRow.setMunicipio(municipio);
				publicadorRow.setTipoIdentificacion(tipoIdentificacion);
				publicadorRow.setEstado(estado);
				publicadorRow.setInstitucion(institucion);
				return publicadorRow;
			}
			return null;
		}
		
	}


	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.PublicadorDao#obtenerIdPublicadorConIdUsuario(int)
	 */
	@Override
	public int obtenerIdPublicadorConIdUsuario(int idUsuario) {
			
		logger.debug("------- Buscando al id publicador por el id usuario");
		
		String sql = "SELECT pu.pub_id "
				+ "FROM publicador pu "
				+ "INNER JOIN usuario u ON u.usu_id = pu.pub_usuario "
				+ "WHERE u.usu_id = ?; ";

		int idPublicador = jdbcTemplate.queryForObject(sql, new Object[]{idUsuario}, Integer.class);
						
		return idPublicador;			
	}


	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.PublicadorDao#buscarPublicadorPorIdUsuario(int)
	 */
	@Override
	public Boolean buscarPublicadorPorIdUsuario(int idUsuario) {

		logger.debug("------- Buscando si existe algun publicador por el id usuario");
		
		String sql = "SELECT COUNT(*) as cant "
				+ "FROM publicador pu "
				+ "INNER JOIN usuario u ON u.usu_id = pu.pub_usuario "
				+ "WHERE u.usu_id = ?; ";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { idUsuario }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
		
	}


	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.PublicadorDao#buscarPublicadorPorIdUsuarioInstitucion(int, int)
	 */
	@Override
	public Boolean buscarPublicadorPorIdUsuarioInstitucion(int idUsuario, int idInstitucion) {
		
		logger.debug("------- Buscando si existe algun freelancer por el id usuario a una institucion");
		
		String sql = "SELECT COUNT(*) as cant "
				+ "FROM publicador p "
				+ "INNER JOIN usuario u ON u.usu_id = p.pub_usuario "
				+ "INNER JOIN institucion i ON i.inst_id = p.pub_institucion "
				+ "WHERE u.usu_id = ? AND i.inst_id = ? ";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { idUsuario, idInstitucion }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}


	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.PublicadorDao#agregarPublicador(int, int)
	 */
	@Override
	public Boolean agregarPublicador(int idUsuario, int idInstitucion) {
		
		logger.debug("--- AGREGANDO NUEVA PUBLICADOR ------");

		try {

			String SQL1 = "INSERT INTO publicador (pub_usuario, pub_institucion) VALUES (?, ?);";

			int resultado1 = jdbcTemplate.update(SQL1, idUsuario, idInstitucion);

			if (resultado1 > 0) {
				logger.debug("Se inserto el publicador");
				
				Boolean existeRolPublicador = buscarUsuarioPublicador(idUsuario);
				
				if (!existeRolPublicador) {
					
					String SQL2 = "INSERT INTO rol_usuario (rolu_rol, rolu_usuario) VALUES (?, ?);";

					int resultado2 = jdbcTemplate.update(SQL2, Constants.ID_ROL_PUBLICADOR, idUsuario);

					if (resultado2 > 0) {
						logger.debug("Se agrego el usuario como un publiador!");
						return true;						
					} else {
						return false;
					}					
				}			
				
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;	
	}


	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.PublicadorDao#listarPublicadores(java.lang.String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarPublicadores(String search, int start, int length, int draw, int posicion,
			String direccion) {
		
		JSONRespuesta respuesta = new JSONRespuesta();
		
		String[] campos = { "u.usu_id", "p.per_identificacion", "p.per_nombres", "p.per_apellidos", "u.usu_email" };

		int fin = start + length - 1;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql
				+ "FROM persona p "
				+ "INNER JOIN usuario u ON u.usu_persona = p.per_id "
				+ "INNER JOIN publicador pu ON pu.pub_usuario = u.usu_id "
				+ "WHERE u.usu_estado IN (1, 2) ";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + "AND (p.per_identificacion LIKE ? OR p.per_nombres LIKE ? ";
			sql = sql + "OR p.per_apellidos LIKE ? OR u.usu_email LIKE ? ) ";

			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { "&" + search + "%", "%" + search + "%",
					"%" + search + "%", "%" + search + "%" }, Integer.class);
		}

		System.out.println("1. Numeros de usuarios filtrados: " + sql);
		
			
		sql = "SELECT usu_id, per_identificacion, per_nombres, per_apellidos, usu_email "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "u.usu_id, p.per_identificacion, p.per_nombres, p.per_apellidos, u.usu_email FROM persona p "
				+ "INNER JOIN usuario u ON u.usu_persona = p.per_id "
				+ "INNER JOIN publicador pu ON pu.pub_usuario = u.usu_id "
				+ "WHERE u.usu_estado IN (1, 2) "
				+ "AND (p.per_identificacion LIKE ? OR p.per_nombres LIKE ? OR p.per_apellidos LIKE ? "
				+ "OR u.usu_email LIKE ? )) ";		
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";
		
		
		System.out.println("2. Numeros de usuarios filtrados:: " + sql);

		List<Publicador> listaPub = jdbcTemplate.query(sql, 
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Publicador>() {

					public Publicador mapRow(ResultSet rs, int rowNum) throws SQLException {												
						int idPublicador = obtenerIdPublicadorConIdUsuario(rs.getInt("usu_id"));
						Publicador publicador = obtenerPublicador(idPublicador);
						return publicador;				
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaPub);

		return respuesta;
		
		
	}


	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.PublicadorDao#buscarUsuarioPublicador(int)
	 */
	@Override
	public Boolean buscarUsuarioPublicador(int idUsuario) {
		
		logger.debug("------- Buscando si el usuario ya tiene el rol publicador");
		
		String sql = "SELECT COUNT(*) as cant "
				+ "FROM publicador p "
				+ "INNER JOIN usuario u ON u.usu_id = p.pub_usuario "
				+ "INNER JOIN rol_usuario ru ON ru.rolu_usuario = u.usu_id "
				+ "INNER JOIN rol r ON r.rol_id = ru.rolu_rol "
				+ "WHERE u.usu_id = ? AND r.rol_id = ?";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { idUsuario, Constants.ID_ROL_PUBLICADOR }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
		
	}
	
	
	
	
}
