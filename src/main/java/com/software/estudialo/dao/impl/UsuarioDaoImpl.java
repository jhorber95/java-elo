/**
 * 
 */
package com.software.estudialo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
//import com.sevensoftware.domotica.util.Encriptar;
import com.software.estudialo.dao.EstadoDao;
import com.software.estudialo.dao.GeneroDao;
import com.software.estudialo.dao.InteresDao;
import com.software.estudialo.dao.MunicipioDao;
import com.software.estudialo.dao.NivelEducativoDao;
import com.software.estudialo.dao.RolDao;
import com.software.estudialo.dao.TipoIdentificacionDao;
import com.software.estudialo.dao.UsuarioDao;
import com.software.estudialo.dao.impl.UsuarioDaoImpl.UsuarioResultSetExtractor;
import com.software.estudialo.entities.Estado;
import com.software.estudialo.entities.Genero;
import com.software.estudialo.entities.Interes;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Municipio;
import com.software.estudialo.entities.NivelEducativo;
import com.software.estudialo.entities.Rol;
import com.software.estudialo.entities.TipoIdentificacion;
import com.software.estudialo.entities.Usuario;
import com.software.estudialo.exception.DAOException;
import com.software.estudialo.util.Constants;
import com.software.estudialo.util.Encriptar;
import com.software.estudialo.util.SHAHashingExample;

// TODO: Auto-generated Javadoc
/**
 * The Class UsuarioDaoImpl.
 *
 * @author LUIS
 */
@Repository("usuarioDao")
public class UsuarioDaoImpl implements UsuarioDao {

	/** The logger. */
	private Logger logger = Logger.getLogger(UsuarioDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/** The tipo usuario dao. */
	@Autowired
	TipoIdentificacionDao tipoIdentificacionDao;

	/** The municipio dao. */
	@Autowired
	MunicipioDao municipioDao;

	/** The genero dao. */
	@Autowired
	GeneroDao generoDao;

	/** The estado dao. */
	@Autowired
	EstadoDao estadoDao;
	
	@Autowired
	RolDao rolDao;
	
	@Autowired
	NivelEducativoDao nivelEducativoDao;
	
	@Autowired
	InteresDao interesDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.dao.UsuarioDao#obtenerUsuario(int)
	 */
	@Override
	public Usuario obtenerUsuario(int id) {
		logger.debug("obtenerUsuario -- Buscando usuario por Id ");

		String SQL = "SELECT p.per_tipo_identificacion, p.per_identificacion, p.per_nombres, p.per_apellidos, p.per_municipio, p.per_telefono, p.per_genero, "
				+ "u.usu_id, u.usu_email, u.usu_estado, u.usu_imagen, u.usu_nivel_educativo FROM persona p INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE u.usu_id = ?;";

		Usuario usuarioResponse = jdbcTemplate.query(SQL, new Object[] { id }, new UsuarioResultSetExtractor());

		System.out.println("obtenerUsuario -- Saliendo usuario por Id");
		return usuarioResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.software.estudialo.dao.UsuarioDao#listarUsuarios(java.lang.String,
	 * int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarUsuarios(String search, int start, int length, int draw, int posicion,
			String direccion) {

		JSONRespuesta respuesta = new JSONRespuesta();

		String[] campos = { "u.usu_id", "p.per_identificacion", "p.per_nombres", "p.per_apellidos", "u.usu_email", "e.est_nombre" };
		//int fin = start + length - 1;
		int fin = start + length;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql + "FROM persona p "
				+ "INNER JOIN usuario u ON u.usu_persona = p.per_id "
				+ "INNER JOIN estado e ON u.usu_estado = e.est_id "
				+ "WHERE u.usu_estado IN (1, 2) ";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + "AND (p.per_identificacion ILIKE ? OR p.per_nombres ILIKE ? ";
			sql = sql + "OR p.per_apellidos ILIKE ? OR u.usu_email ILIKE ? OR e.est_nombre ILIKE ? ) ";

			filtrados = jdbcTemplate.queryForObject(sql,
					new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%" },
					Integer.class);
		}

		System.out.println("1. Numeros de usuarios filtrados: " + sql);

		sql = "SELECT usu_id, per_identificacion, per_nombres, per_apellidos, usu_email, est_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "u.usu_id, p.per_identificacion, p.per_nombres, p.per_apellidos, u.usu_email, e.est_nombre FROM persona p "
				+ "LEFT JOIN usuario u ON (u.usu_persona = p.per_id) " 
				+ "INNER JOIN estado e ON u.usu_estado = e.est_id "
				+ "WHERE u.usu_estado IN (1, 2) "
				+ "AND (p.per_identificacion ILIKE ? OR p.per_nombres ILIKE ? OR p.per_apellidos ILIKE ? "
				+ "OR u.usu_email ILIKE ? OR e.est_nombre ILIKE ? )) ";
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";

		System.out.println("2. Numeros de usuarios filtrados:: " + sql);

		List<Usuario> listaUsu = jdbcTemplate.query(sql, new Object[] { "%" + search + "%", "%" + search + "%",
				"%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin }, new RowMapper<Usuario>() {

					public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
						Usuario usuario = obtenerUsuario(rs.getInt("usu_id"));
						usuario.setPassword(null);
						return usuario;
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaUsu);

		return respuesta;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.dao.UsuarioDao#agregarUsuario(com.software.
	 * estudialo.entities.Usuario)
	 */
	@Override
	public boolean agregarUsuario(Usuario usuario) {

		logger.debug("--- AGREGANDO NUEVO USUARIO - PERSONA ------");

		try {

			final String SQL1 = "INSERT INTO persona (per_identificacion, per_tipo_identificacion, per_nombres, per_apellidos, "
					+ "per_municipio, per_telefono, per_genero) " + "VALUES (?, ?, ?, ?, ?, ?, ?)";

			KeyHolder keyHolder = new GeneratedKeyHolder();
			int resultado = jdbcTemplate.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement pstm = con.prepareStatement(SQL1, Statement.RETURN_GENERATED_KEYS);
					pstm.setString(1, usuario.getIdentificacion());
					pstm.setInt(2, usuario.getTipoIdentificacion().getId());
					pstm.setString(3, WordUtils.capitalizeFully(usuario.getNombres()));
					pstm.setString(4, WordUtils.capitalizeFully(usuario.getApellidos()));
					pstm.setInt(5, usuario.getMunicipio().getId());
					pstm.setString(6, usuario.getTelefono());
					pstm.setInt(7, usuario.getGenero().getId());
					return pstm;
				}
			}, keyHolder);

			if (resultado > 0) {
				logger.debug("--- PERSONA AGREGADA ----");
				int key = 0;

				if (keyHolder.getKeys().size() == 1) {
					key = keyHolder.getKey().intValue();
				} else if (keyHolder.getKeys().size() > 1) {
					logger.debug("------- Se encuentra mas de una llave ----");
					key = Integer.parseInt(String.valueOf(keyHolder.getKeys().get("per_id")));
				} else {
					logger.debug("------- No se pudo extraer la llave ----");
					return false;
				}

				final String id = Integer.toString(key);
				logger.debug("Llave primaria de persona -----> " + key);
				// logger.debug("------Encriptando Password-----");
				// String encriptedPassword =
				// Encriptar.toBCryptPasswordEncoder(usuario.getPassword());
				// logger.debug("**** CLAVE USUARIO ENCRYPTADA **** = " +
				// encriptedPassword);

				logger.debug("--ANTES DE INSERTAR EL USUARIO -----");
				String SQL2 = "INSERT INTO usuario (usu_email, usu_password, usu_persona, usu_estado, usu_imagen) VALUES(?, ?, ?, ?, ?);";

				String encryptada = Encriptar.toSHA256PasswordEncoder(usuario.getPassword());
				
				KeyHolder keyHolder2 = new GeneratedKeyHolder();

				int resultado2 = jdbcTemplate.update(new PreparedStatementCreator() {

					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						PreparedStatement pstm = con.prepareStatement(SQL2, Statement.RETURN_GENERATED_KEYS);
						pstm.setString(1, usuario.getEmail());
						pstm.setString(2, encryptada);
						pstm.setInt(3, Integer.parseInt(id));
						pstm.setInt(4, Constants.ID_ESTADO_ACTIVO_ENTIDADES_PRIMARIAS);
						// pstm.setString(5, Constants.DEFECTO_USUARIO_IMAGE);
						pstm.setString(5, Constants.DEFECTO_IMAGE);
						return pstm;
					}
				}, keyHolder2);

				// (SQL2, usuario.getEmail(), usuario.getPassword(),
				// Integer.parseInt(id), usuario.getEstado().getId());
				// int resultado2 = jdbcTemplate.update(SQL2,
				// usuario.getEmail(), encriptedPassword, Integer.parseInt(id),
				// usuario.getEstado().getId());

				if (resultado2 > 0) {
					logger.debug("---NUEVO USUARIO INSERTADO ------");

					int key2 = 0;

					if (keyHolder2.getKeys().size() == 1) {
						key2 = keyHolder2.getKey().intValue();
					} else if (keyHolder2.getKeys().size() > 1) {
						logger.debug("------- Se encuentra mas de una llave ----");
						key2 = Integer.parseInt(String.valueOf(keyHolder2.getKeys().get("usu_id")));
					} else {
						logger.debug("------- No se pudo extraer la llave ----");
						return false;
					}

					// final String id2 = Integer.toString(key);
					logger.debug("Llave primaria de usuario es -----> " + key2);

					// Usuario usuarioObj = obtenerUsuario(key2);

					logger.debug("Insertando en la tabla rol_usuario");
					String SQL3 = "INSERT INTO rol_usuario (rolu_rol, rolu_usuario) VALUES (?, ?);";

					// INSCRIBIR COMO ESTUDIANTE
					int resultado3 = jdbcTemplate.update(SQL3, Constants.ID_ROL_ESTUDIANTE, key2);

					if (resultado3 > 0) {
						logger.debug("---ASIGNADO CON ROL ESTUDIANTE ------");

						logger.debug("Insertando en la tabla estudiante");
						String SQL4 = "INSERT INTO estudiante (estu_usuario) VALUES (?);";

						// INSCRIBIR COMO ESTUDIANTE
						int resultado4 = jdbcTemplate.update(SQL4, key2);

						if (resultado4 > 0) {
							logger.debug("---TODO PERFECTO ------");
							return true;
						} else {
							logger.debug("No se pudo insertar en la tabla estudiantes");
							return false;
						}

					} else {
						logger.debug("No se pudo insertar como rol estudiante");
						return false;
					}

				} else {
					return false;
				}
			}
			return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.dao.UsuarioDao#modificarUsuario(int,
	 * com.software.estudialo.entities.Usuario)
	 */
	@Override
	public boolean modificarUsuario(int id, Usuario usuario) {

		logger.debug("--- DENTRO DE MODIFICAR USUARIO --------");

		String SQL1 = "UPDATE persona SET per_identificacion = ?, per_tipo_identificacion = ?, per_nombres = ?, per_apellidos = ?, "
				+ "per_municipio = ?, per_telefono = ?, per_genero = ? "
				+ "FROM usuario WHERE usuario.usu_id = ? AND usuario.usu_persona = persona.per_id;";

		int resultado1 = jdbcTemplate.update(SQL1, usuario.getIdentificacion(), usuario.getTipoIdentificacion().getId(),
				WordUtils.capitalizeFully(usuario.getNombres()), WordUtils.capitalizeFully(usuario.getApellidos()), usuario.getMunicipio().getId(), usuario.getTelefono(),
				usuario.getGenero().getId(), usuario.getId());

		if (resultado1 > 0) {
			logger.debug("--- PERSONA MODIFICADA ------");

			String SQL2 = "UPDATE usuario SET usu_email = ?, usu_estado = ? WHERE usu_id = ?;";

			int resultado2 = jdbcTemplate.update(SQL2, usuario.getEmail(), usuario.getEstado().getId(),
					usuario.getId());

			if (resultado2 > 0) {
				logger.debug("--- USUARIO MODIFICADO ------");
				return true;

			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.dao.UsuarioDao#eliminarUsuario(int)
	 */
	@Override
	public boolean eliminarUsuario(int id) {

		logger.debug("--- DENTRO DE ELIMINAR USUARIO --------");

		int idEliminado = 7;

		String SQL1 = "UPDATE usuario SET usu_estado = ? WHERE usuario.usu_id = ?;";

		int resultado1 = jdbcTemplate.update(SQL1, idEliminado, id);

		if (resultado1 > 0) {
			logger.debug("--- USUARIO CAMBIADO DE ESTADO A ELIMINADO  ------");
			return true;
		} else {
			return false;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.dao.UsuarioDao#buscarUsuario(int)
	 */
	@Override
	public Boolean buscarUsuario(int id) {

		logger.debug("------- Buscando usuario por id");

		String sql = "SELECT COUNT(*) from usuario u " + "WHERE u.usu_estado IN (1, 2) AND u.usu_id = ?;";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { id }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.dao.UsuarioDao#buscarUsuario(com.software.
	 * estudialo.entities.Usuario)
	 */
	@Override
	public Boolean buscarUsuario(Usuario usuario) {

		logger.debug("------- Buscando usuario ");

		String sql = "SELECT COUNT(*) from usuario u " + "INNER JOIN persona p ON p.per_id = u.usu_persona "
				+ "WHERE u.usu_estado IN (1, 2) AND p.per_identificacion = ? OR p.per_telefono = ? OR u.usu_email = ?;";

		int count = jdbcTemplate.queryForObject(sql,
				new Object[] { usuario.getIdentificacion(), usuario.getTelefono(), usuario.getEmail() }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * The Class UsuarioResultSetExtractor.
	 */
	class UsuarioResultSetExtractor implements ResultSetExtractor<Usuario> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql
		 * .ResultSet)
		 */
		@Override
		public Usuario extractData(ResultSet rs) throws SQLException, DataAccessException {

			if (rs.next()) {
				logger.debug("UsuarioResultSetExtractor -- Obteniendo usuario");
				Usuario usuarioRow = new Usuario();
				usuarioRow.setId(rs.getInt("usu_id"));
				usuarioRow.setNombres(rs.getString("per_nombres"));
				usuarioRow.setApellidos(rs.getString("per_apellidos"));
				usuarioRow.setTelefono(rs.getString("per_telefono"));
				usuarioRow.setIdentificacion(rs.getString("per_identificacion"));
				usuarioRow.setEmail(rs.getString("usu_email"));
				usuarioRow.setUrlImagen(rs.getString("usu_imagen"));

				Municipio municipio = municipioDao.obtenerMunicipio(rs.getInt("per_municipio"));
				Estado estado = estadoDao.obtenerEstado(rs.getInt("usu_estado"));
				TipoIdentificacion tipoIdentificacion = tipoIdentificacionDao
						.obtenerTipoIdentificacion(rs.getInt("per_tipo_identificacion"));
				Genero genero = generoDao.obtenerGenero(rs.getInt("per_genero"));
				
				NivelEducativo nivelEducativo = nivelEducativoDao.obtenerNivelEducativo(rs.getInt("usu_nivel_educativo"));
				List<Interes> intereses = interesDao.obtenerInteresesUsuario(rs.getInt("usu_id")); 

				usuarioRow.setMunicipio(municipio);
				usuarioRow.setEstado(estado);
				usuarioRow.setTipoIdentificacion(tipoIdentificacion);
				usuarioRow.setGenero(genero);
				usuarioRow.setNivelEducativo(nivelEducativo);
				usuarioRow.setIntereses(intereses);				
				
				return usuarioRow;
			}
			return null;
		}

	}
	
	

	@Override
	public boolean modificarImagenUsuario(int idUsuario, String newFileName) {

		logger.debug("--- DENTRO DE MODIFICAR IMAGEN USUARIO --------");

		String SQL1 = "UPDATE usuario SET usu_imagen = ? WHERE usu_id = ?;";

		int resultado1 = jdbcTemplate.update(SQL1, newFileName, idUsuario);

		if (resultado1 > 0) {
			logger.debug("--- IMAGEN MODIFICADA ------");
			return true;

		} else {
			return false;
		}

	}
	
	
	class UsuarioCompleteResultSetExtractor implements ResultSetExtractor<Usuario> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql
		 * .ResultSet)
		 */
		@Override
		public Usuario extractData(ResultSet rs) throws SQLException, DataAccessException {

			if (rs.next()) {
				logger.debug("UsuarioResultSetExtractor -- Obteniendo usuario");
				Usuario usuarioRow = new Usuario();
				usuarioRow.setId(rs.getInt("usu_id"));
				usuarioRow.setNombres(rs.getString("per_nombres"));
				usuarioRow.setApellidos(rs.getString("per_apellidos"));
				usuarioRow.setTelefono(rs.getString("per_telefono"));
				usuarioRow.setIdentificacion(rs.getString("per_identificacion"));
				usuarioRow.setEmail(rs.getString("usu_email"));
				usuarioRow.setPassword(rs.getString("usu_password"));
				usuarioRow.setUrlImagen(rs.getString("usu_imagen"));

				Municipio municipio = municipioDao.obtenerMunicipio(rs.getInt("per_municipio"));
				Estado estado = estadoDao.obtenerEstado(rs.getInt("usu_estado"));
				TipoIdentificacion tipoIdentificacion = tipoIdentificacionDao
						.obtenerTipoIdentificacion(rs.getInt("per_tipo_identificacion"));
				Genero genero = generoDao.obtenerGenero(rs.getInt("per_genero"));
				
				NivelEducativo nivelEducativo = nivelEducativoDao.obtenerNivelEducativo(rs.getInt("usu_nivel_educativo"));
				List<Interes> intereses = interesDao.obtenerInteresesUsuario(rs.getInt("usu_id")); 

				usuarioRow.setMunicipio(municipio);
				usuarioRow.setEstado(estado);
				usuarioRow.setTipoIdentificacion(tipoIdentificacion);
				usuarioRow.setGenero(genero);
				usuarioRow.setNivelEducativo(nivelEducativo);
				usuarioRow.setIntereses(intereses);	
				
				return usuarioRow;
			}
			return null;
		}

	}
	
	

	@Override
	public Usuario buscarUsuarioPorUsername(String username) {
		
		logger.debug("buscarUsuarioPorUsername -- Buscando usuario por email ");

		String SQL = "SELECT p.per_tipo_identificacion, p.per_identificacion, p.per_nombres, p.per_apellidos, p.per_municipio, p.per_telefono, p.per_genero, "
				+ "u.usu_id, u.usu_email, u.usu_password, u.usu_estado, u.usu_imagen, u.usu_nivel_educativo FROM persona p INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE u.usu_email = ?;";

		Usuario usuarioResponse = jdbcTemplate.query(SQL, new Object[] { username }, new UsuarioCompleteResultSetExtractor());

		System.out.println("obtenerUsuario -- Saliendo usuario por Id");
		return usuarioResponse;
		
		
	}

	@Override
	public Usuario obtenerUsuarioPorUsernameCompletoSinPassword(String username) {
		logger.debug("obtenerUsuarioPorUsernameCompletoSinPassword -- Buscando usuario por username (email) pero sin password, pero con roles ");

		String SQL = "SELECT p.per_tipo_identificacion, p.per_identificacion, p.per_nombres, p.per_apellidos, p.per_municipio, p.per_telefono, p.per_genero, "
				+ "u.usu_id, u.usu_email, u.usu_password, u.usu_estado, u.usu_imagen, u.usu_nivel_educativo FROM persona p INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE u.usu_email = ?;";

		Usuario usuarioResponse = jdbcTemplate.query(SQL, new Object[] { username }, new UsuarioCompleteSinPasswordConRolesResultSetExtractor());

		System.out.println("obtenerUsuario -- Saliendo usuario por Id");
		return usuarioResponse;
	}
	
	
	class UsuarioCompleteSinPasswordConRolesResultSetExtractor implements ResultSetExtractor<Usuario> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql
		 * .ResultSet)
		 */
		@Override
		public Usuario extractData(ResultSet rs) throws SQLException, DataAccessException {

			if (rs.next()) {
				logger.debug("UsuarioResultSetExtractor -- Obteniendo usuario");
				Usuario usuarioRow = new Usuario();
				usuarioRow.setId(rs.getInt("usu_id"));
				usuarioRow.setNombres(rs.getString("per_nombres"));
				usuarioRow.setApellidos(rs.getString("per_apellidos"));
				usuarioRow.setTelefono(rs.getString("per_telefono"));
				usuarioRow.setIdentificacion(rs.getString("per_identificacion"));
				usuarioRow.setEmail(rs.getString("usu_email"));
				usuarioRow.setPassword(null);
				usuarioRow.setUrlImagen(rs.getString("usu_imagen"));

				Municipio municipio = municipioDao.obtenerMunicipio(rs.getInt("per_municipio"));
				Estado estado = estadoDao.obtenerEstado(rs.getInt("usu_estado"));
				TipoIdentificacion tipoIdentificacion = tipoIdentificacionDao
						.obtenerTipoIdentificacion(rs.getInt("per_tipo_identificacion"));
				Genero genero = generoDao.obtenerGenero(rs.getInt("per_genero"));
				
				NivelEducativo nivelEducativo = nivelEducativoDao.obtenerNivelEducativo(rs.getInt("usu_nivel_educativo"));
				List<Interes> intereses = interesDao.obtenerInteresesUsuario(rs.getInt("usu_id"));

				usuarioRow.setMunicipio(municipio);
				usuarioRow.setEstado(estado);
				usuarioRow.setTipoIdentificacion(tipoIdentificacion);
				usuarioRow.setGenero(genero);
				usuarioRow.setNivelEducativo(nivelEducativo);
				usuarioRow.setIntereses(intereses);	
				
				
				//Buscando roles por username
				Set<Rol> rolesT = rolDao.buscarRolesPorUsername(rs.getString("usu_email"));
				List<Rol> roles = new ArrayList<Rol>(rolesT);
				usuarioRow.setRoles(roles);
				
				return usuarioRow;
			}
			return null;
		}

	}



	@Override
	public Boolean buscarExisteUsuarioPorUsername(String username) {
		logger.debug("------- Buscando usuario ");

		String sql = "SELECT COUNT(*) "
				+ "FROM usuario u "
				+ "WHERE u.usu_estado IN (1, 2) AND u.usu_email = ?;";

		int count = jdbcTemplate.queryForObject(sql,
				new Object[] { username }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean buscarUsuarioParaSignUp(Usuario usuario) {
		logger.debug("------- Buscando usuario para sign up ");

		String sql = "SELECT COUNT(*) from usuario u "
				+ "INNER JOIN persona p ON p.per_id = u.usu_persona "
				+ "WHERE u.usu_estado IN (1, 2) AND (u.usu_email = ? OR (p.per_nombres = ? AND p.per_apellidos = ?));";

		int count = jdbcTemplate.queryForObject(sql,
				new Object[] { usuario.getEmail(), usuario.getNombres(), usuario.getApellidos() }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean signUpUsuario(Usuario usuario) {
		
		logger.debug("--- SIGNING UP un USUARIO - PERSONA ------");

		try {

			final String SQL1 = "INSERT INTO persona (per_nombres, per_apellidos) VALUES (?, ?)";

			KeyHolder keyHolder = new GeneratedKeyHolder();
			int resultado = jdbcTemplate.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement pstm = con.prepareStatement(SQL1, Statement.RETURN_GENERATED_KEYS);
					pstm.setString(1, WordUtils.capitalizeFully(usuario.getNombres()));
					pstm.setString(2, WordUtils.capitalizeFully(usuario.getApellidos()));					
					return pstm;
				}
			}, keyHolder);

			if (resultado > 0) {
				logger.debug("--- PERSONA AGREGADA ----");
				int key = 0;

				if (keyHolder.getKeys().size() == 1) {
					key = keyHolder.getKey().intValue();
				} else if (keyHolder.getKeys().size() > 1) {
					logger.debug("------- Se encuentra mas de una llave ----");
					key = Integer.parseInt(String.valueOf(keyHolder.getKeys().get("per_id")));
				} else {
					logger.debug("------- No se pudo extraer la llave ----");
					return false;
				}

				final String id = Integer.toString(key);
				logger.debug("Llave primaria de persona -----> " + key);
				// logger.debug("------Encriptando Password-----");
				// String encriptedPassword =
				// Encriptar.toBCryptPasswordEncoder(usuario.getPassword());
				// logger.debug("**** CLAVE USUARIO ENCRYPTADA **** = " +
				// encriptedPassword);

				logger.debug("--ANTES DE INSERTAR EL USUARIO -----");
				String SQL2 = "INSERT INTO usuario (usu_email, usu_password, usu_persona, usu_estado, usu_imagen) VALUES(?, ?, ?, ?, ?);";

				String encryptada = Encriptar.toSHA256PasswordEncoder(usuario.getPassword());
				
				KeyHolder keyHolder2 = new GeneratedKeyHolder();

				int resultado2 = jdbcTemplate.update(new PreparedStatementCreator() {

					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						PreparedStatement pstm = con.prepareStatement(SQL2, Statement.RETURN_GENERATED_KEYS);
						pstm.setString(1, usuario.getEmail());
						pstm.setString(2, encryptada);
						pstm.setInt(3, Integer.parseInt(id));
						pstm.setInt(4, Constants.ID_ESTADO_ACTIVO_ENTIDADES_PRIMARIAS);
						pstm.setString(5, Constants.DEFECTO_IMAGE);
						return pstm;
					}
				}, keyHolder2);

				if (resultado2 > 0) {
					logger.debug("---NUEVO USUARIO INSERTADO ------");

					int key2 = 0;

					if (keyHolder2.getKeys().size() == 1) {
						key2 = keyHolder2.getKey().intValue();
					} else if (keyHolder2.getKeys().size() > 1) {
						logger.debug("------- Se encuentra mas de una llave ----");
						key2 = Integer.parseInt(String.valueOf(keyHolder2.getKeys().get("usu_id")));
					} else {
						logger.debug("------- No se pudo extraer la llave ----");
						return false;
					}

					// final String id2 = Integer.toString(key);
					logger.debug("Llave primaria de usuario es -----> " + key2);

					// Usuario usuarioObj = obtenerUsuario(key2);

					logger.debug("Insertando en la tabla rol_usuario");
					String SQL3 = "INSERT INTO rol_usuario (rolu_rol, rolu_usuario) VALUES (?, ?);";

					// INSCRIBIR COMO ESTUDIANTE
					int resultado3 = jdbcTemplate.update(SQL3, Constants.ID_ROL_ESTUDIANTE, key2);

					if (resultado3 > 0) {
						logger.debug("---ASIGNADO CON ROL ESTUDIANTE ------");

						logger.debug("Insertando en la tabla estudiante");
						String SQL4 = "INSERT INTO estudiante (estu_usuario) VALUES (?);";

						// INSCRIBIR COMO ESTUDIANTE
						int resultado4 = jdbcTemplate.update(SQL4, key2);

						if (resultado4 > 0) {
							logger.debug("---TODO PERFECTO ------");
							return true;
						} else {
							logger.debug("No se pudo insertar en la tabla estudiantes");
							return false;
						}

					} else {
						logger.debug("No se pudo insertar como rol estudiante");
						return false;
					}

				} else {
					return false;
				}
			}
			return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
		
	}
	

	@Override
	public boolean modificarUsuarioDatosPerfil(int id, Usuario usuario) {
		
		logger.debug("--- DENTRO DE MODIFICAR USUARIO NO ADMIN --------");

		String SQL1 = "UPDATE persona SET per_identificacion = ?, per_tipo_identificacion = ?, per_nombres = ?, per_apellidos = ?, "
				+ "per_municipio = ?, per_telefono = ?, per_genero = ? "
				+ "FROM usuario WHERE usuario.usu_id = ? AND usuario.usu_persona = persona.per_id;";

		int resultado1 = jdbcTemplate.update(SQL1, usuario.getIdentificacion(), usuario.getTipoIdentificacion().getId(),
				WordUtils.capitalizeFully(usuario.getNombres()), WordUtils.capitalizeFully(usuario.getApellidos()), usuario.getMunicipio().getId(), usuario.getTelefono(),
				usuario.getGenero().getId(), usuario.getId());

		if (resultado1 > 0) {
			logger.debug("--- PERSONA MODIFICADA ------");			

			String SQL2 = "UPDATE usuario SET usu_email = ?, usu_nivel_educativo = ? WHERE usu_id = ?;";

			int resultado2 = jdbcTemplate.update(SQL2, usuario.getEmail(), usuario.getNivelEducativo().getId(), usuario.getId());

			if (resultado2 > 0) {
				logger.debug("--- USUARIO MODIFICADO ------");
				interesDao.actualizarInteresesUsuario(usuario.getIntereses(), usuario.getId());
				
				return true;

			} else {
				return false;
			}
		} else {
			return false;
		}
		
	}

	@Override
	public Boolean buscarExisteEmail(String email, int idUsuario) {
		
		logger.debug("------- Buscando usuario ");

		String sql = "SELECT COUNT(*) "
				+ "FROM usuario u "
				+ "WHERE u.usu_estado IN (1, 2) AND u.usu_email = ? AND u.usu_id != ?;";

		int count = jdbcTemplate.queryForObject(sql,
				new Object[] { email, idUsuario }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public Boolean existenciaUsuarioPorUsername(String username) {
		logger.debug("------- Buscando existenciaUsuarioPorUsername ");

		String sql = "SELECT COUNT(*) from usuario u "
				+ "WHERE u.usu_estado IN (1, 2) AND u.usu_email = ?;";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { username }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean usuarioActivo(String username) {
		logger.debug("------- Buscando usuarioActivo  ");

		String sql = "SELECT COUNT(*) from usuario u WHERE u.usu_email = ? AND u.usu_estado = ?;";

		int count = jdbcTemplate.queryForObject(sql,
				new Object[] { username, Constants.ID_ESTADO_ACTIVO_ENTIDADES_PRIMARIAS }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean cambiarPassword(int idUsuario, String newPassword) {
		logger.debug("--- Cambiar password --------");

		String SQL1 = "UPDATE usuario SET usu_password = ? WHERE usu_id = ?";

		int resultado1 = jdbcTemplate.update(SQL1, newPassword, idUsuario);

		if (resultado1 > 0) {
			logger.debug("--- cambiada Password ------");
			return true;
		} else {
			return false;
		}
	}
	
	
	@Override
	public Boolean validateOldPassword(int idUsuario, String oldPassword) {
		logger.debug("--- Validar old password --------");

		String SQL1 = "SELECT COUNT(*) from usuario u WHERE u.usu_id = ? AND u.usu_password = ?;";

		int count = jdbcTemplate.queryForObject(SQL1, new Object[] { idUsuario, oldPassword }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	
	

}
