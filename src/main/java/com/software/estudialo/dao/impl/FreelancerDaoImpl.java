/**
 * 
 */
package com.software.estudialo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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

import com.software.estudialo.dao.EstadoDao;
import com.software.estudialo.dao.FreelancerDao;
import com.software.estudialo.dao.GeneroDao;
import com.software.estudialo.dao.MunicipioDao;
import com.software.estudialo.dao.TipoIdentificacionDao;
import com.software.estudialo.dao.impl.CategoriaDaoImpl.CategoriaResultSetExtractor;
import com.software.estudialo.entities.Categoria;
import com.software.estudialo.entities.Estado;
import com.software.estudialo.entities.Freelancer;
import com.software.estudialo.entities.Genero;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Municipio;
import com.software.estudialo.entities.TipoIdentificacion;
import com.software.estudialo.entities.Usuario;
import com.software.estudialo.util.Constants;
import com.software.estudialo.util.Encriptar;

// TODO: Auto-generated Javadoc
/**
 * The Class FreelancerDaoImpl.
 *
 * @author LUIS
 */
@Repository("freelancerDao")
public class FreelancerDaoImpl implements FreelancerDao{ 
	
	/** The logger. */
	private Logger logger = Logger.getLogger(FreelancerDaoImpl.class);

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

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.FreelancerDao#obtenerFreelancer(int)
	 */
	@Override
	public Freelancer obtenerFreelancer(int id) {
		logger.debug("obtenerFreelancer -- Buscando freelancer por Id ");

		String SQL = "SELECT u.usu_id, u.usu_email, u.usu_estado, p.per_tipo_identificacion, p.per_nombres, p.per_apellidos, p.per_municipio, "
				+ "p.per_telefono, p.per_identificacion, p.per_genero "
				+ "FROM freelancer f "
				+ "INNER JOIN usuario u ON u.usu_id = f.free_usuario "
				+ "INNER JOIN persona p ON p.per_id = u.usu_persona "
				+ "INNER JOIN oferta_freelancer of ON of.ofr_freelancer = f.free_id "
				+ "WHERE f.free_id = ?;";

		Freelancer freelancerResponse = jdbcTemplate.query(SQL, new Object[] { id }, new FreelancerResultSetExtractor());
		
		logger.debug("obtenerFreelancer -- Saliendo freelancer por Id");
		return freelancerResponse;
	}
	
	

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.FreelancerDao#agregarFreelancer(int)
	 */
	@Override
	public Boolean agregarFreelancer(int id) {
		
		logger.debug("--- AGREGANDO NUEVA FREELANCER ------");

		try {

			String SQL1 = "INSERT INTO freelancer (free_usuario) VALUES (?);";

			int resultado1 = jdbcTemplate.update(SQL1, id);

			if (resultado1 > 0) {
				logger.debug("Se inserto el freelancer");
				
				String SQL2 = "INSERT INTO rol_usuario (rolu_rol, rolu_usuario) VALUES (?, ?);";

				int resultado2 = jdbcTemplate.update(SQL2, Constants.ID_ROL_FREELANCER, id);

				if (resultado2 > 0) {
					logger.debug("Se agrego el usuario como un frelancer!");
					return true;
					
				} else {
					return false;
				}
				
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
		
	}	
	
	
	
	@Override
	public Boolean agregarFreelancerCompleto(Usuario usuario) {
		
		logger.debug("agregarFreelancerCompleto --- AGREGANDO NUEVO USUARIO - PERSONA ------");

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

					logger.debug("Insertando en la tabla rol_usuario como estudiante");
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
							logger.debug("Insertando en la tabla rol_usuario como freelancer");

							String SQL5 = "INSERT INTO rol_usuario (rolu_rol, rolu_usuario) VALUES (?, ?);";
							int resultado5 = jdbcTemplate.update(SQL5, Constants.ID_ROL_FREELANCER, key2);
							
							if (resultado5 > 0) {
								
								logger.debug("---ASIGNADO CON ROL FREELANCER ------");

								logger.debug("Insertando en la tabla freelancer");
								String SQL6 = "INSERT INTO freelancer (free_usuario) VALUES (?);";
								
								int resultado6 = jdbcTemplate.update(SQL6, key2);

								if (resultado6 > 0) {
									
									logger.debug(" FREELANCER CREADO CORRECTAMENTE ---");
									
									return true;
								} else {
									logger.debug("No se pudo insertar en la tabla freelancer");
									return false;
								}							
								
							}else {
								logger.debug("No se pudo insertar como rol freelancer");
								return false;
							}
							
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
	
	

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.FreelancerDao#obtenerFreelancerPorOferta(int)
	 */
	@Override
	public Freelancer obtenerFreelancerPorOferta(int idOferta) {
		logger.debug("obtenerFreelancerPorOferta -- Buscando freelancer por oferta ");

		String SQL = "SELECT u.usu_id, u.usu_email, u.usu_estado, p.per_tipo_identificacion, p.per_nombres, p.per_apellidos, p.per_municipio, "
				+ "p.per_telefono, p.per_identificacion, p.per_genero "
				+ "FROM freelancer f "
				+ "INNER JOIN usuario u ON u.usu_id = f.free_usuario "
				+ "INNER JOIN persona p ON p.per_id = u.usu_persona "
				+ "INNER JOIN oferta_freelancer of ON of.ofr_freelancer = f.free_id "
				+ "WHERE of.ofr_oferta = ?;";

		Freelancer freelancerResponse = jdbcTemplate.query(SQL, new Object[] { idOferta }, new FreelancerResultSetExtractor());
		
		logger.debug("obtenerFreelancerPorOferta -- Saliendo freelancer por oferta ");
		return freelancerResponse;
	}
	
	
	/**
	 * The Class FreelancerResultSetExtractor.
	 */
	class FreelancerResultSetExtractor implements ResultSetExtractor<Freelancer>{

		
		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
		 */
		@Override
		public Freelancer extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			if (rs.next()) {				
				logger.debug("FreelancerResultSetExtractor -- Obteniendo freelancer");
				Freelancer freelancerRow = new Freelancer();
				// El id es el del usuario
				freelancerRow.setId(rs.getInt("usu_id"));
				freelancerRow.setNombres(rs.getString("per_nombres"));
				freelancerRow.setApellidos(rs.getString("per_apellidos"));
				freelancerRow.setTelefono(rs.getString("per_telefono"));
				freelancerRow.setIdentificacion(rs.getString("per_identificacion"));
				freelancerRow.setEmail(rs.getString("usu_email"));				
				
				Municipio municipio = municipioDao.obtenerMunicipio(rs.getInt("per_municipio"));
				Estado estado = estadoDao.obtenerEstado(rs.getInt("usu_estado"));
				Genero genero = generoDao.obtenerGenero(rs.getInt("per_genero"));
				TipoIdentificacion tipoIdentificacion = tipoIdentificacionDao.obtenerTipoIdentificacion(rs.getInt("per_tipo_identificacion"));
				
				freelancerRow.setGenero(genero);
				freelancerRow.setMunicipio(municipio);
				freelancerRow.setTipoIdentificacion(tipoIdentificacion);
				freelancerRow.setEstado(estado);
				return freelancerRow;
			}
			return null;
		}
		
	}


	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.FreelancerDao#buscarFreelancerPorIdUsuario(int)
	 */
	@Override
	public Boolean buscarFreelancerPorIdUsuario(int idUsuario) {
		
		logger.debug("------- Buscando si existe algun freelancer por el id usuario");
		
		String sql = "SELECT COUNT(*) as cant "
				+ "FROM freelancer f "
				+ "INNER JOIN usuario u ON u.usu_id = f.free_usuario "
				+ "WHERE u.usu_id = ?; ";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { idUsuario }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.FreelancerDao#obtenerIdFreelancerConIdUsuario(int)
	 */
	@Override
	public int obtenerIdFreelancerConIdUsuario(int idUsuario) {
		logger.debug("------- Buscando al id frelancer por el id usuario");
		
		String sql = "SELECT f.free_id "
				+ "FROM freelancer f "
				+ "INNER JOIN usuario u ON u.usu_id = f.free_usuario "
				+ "WHERE u.usu_id = ?; ";

		int idPublicador = jdbcTemplate.queryForObject(sql, new Object[]{idUsuario}, Integer.class);
						
		return idPublicador;		
	}



	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.FreelancerDao#listarFreelancers(java.lang.String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarFreelancers(String search, int start, int length, int draw, int posicion,
			String direccion) {
		
		JSONRespuesta respuesta = new JSONRespuesta();
		
		String[] campos = { "u.usu_id", "p.per_identificacion", "p.per_nombres", "p.per_apellidos", "u.usu_email" };

		int fin = start + length - 1;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql
				+ "FROM persona p "
				+ "INNER JOIN usuario u ON u.usu_persona = p.per_id "
				+ "INNER JOIN freelancer f ON f.free_usuario = u.usu_id "
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
				+ "INNER JOIN freelancer f ON f.free_usuario = u.usu_id "
				+ "WHERE u.usu_estado IN (1, 2) "
				+ "AND (p.per_identificacion LIKE ? OR p.per_nombres LIKE ? OR p.per_apellidos LIKE ? "
				+ "OR u.usu_email LIKE ? )) ";		
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";
		
		
		System.out.println("2. Numeros de usuarios filtrados:: " + sql);

		List<Freelancer> listaFree = jdbcTemplate.query(sql, 
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Freelancer>() {

					public Freelancer mapRow(ResultSet rs, int rowNum) throws SQLException {
						
						logger.debug("ID de USUARIO= " + rs.getInt("usu_id"));
						int idFreelancer = obtenerIdFreelancerConIdUsuario(rs.getInt("usu_id"));
						Freelancer freelancer = obtenerFreelancer(idFreelancer);
						return freelancer;				
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaFree);

		return respuesta;
		
	}



	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.FreelancerDao#obtenerIdFreelancerPorIdOferta(int)
	 */
	@Override
	public int obtenerIdFreelancerPorIdOferta(int idOferta) {
		logger.debug("------- Buscando al id frelancer por el id oferta");
		
		String sql = "SELECT f.free_id "
				+ "FROM freelancer f "
				+ "INNER JOIN oferta_freelancer of ON of.ofr_freelancer = f.free_id "
				+ "INNER JOIN oferta o ON o.ofe_id = of.ofr_oferta "
				+ "WHERE o.ofe_id = ?; ";

		int idPublicador = jdbcTemplate.queryForObject(sql, new Object[]{idOferta}, Integer.class);
						
		return idPublicador;
	}



	@Override
	public JSONRespuesta listarFreelancersAdmin(String search, int start, int length, int draw, int posicion,
			String direccion) {
		JSONRespuesta respuesta = new JSONRespuesta();
		
		String[] campos = { "u.usu_id", "p.per_identificacion", "p.per_nombres", "p.per_apellidos", "u.usu_email" };

		int fin = start + length;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql
				+ "FROM persona p "
				+ "INNER JOIN usuario u ON u.usu_persona = p.per_id "
				+ "INNER JOIN freelancer f ON f.free_usuario = u.usu_id "
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
				+ "INNER JOIN freelancer f ON f.free_usuario = u.usu_id "
				+ "WHERE u.usu_estado IN (1, 2) "
				+ "AND (p.per_identificacion LIKE ? OR p.per_nombres LIKE ? OR p.per_apellidos LIKE ? "
				+ "OR u.usu_email LIKE ? )) ";		
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";
		
		
		System.out.println("2. Numeros de usuarios filtrados:: " + sql);

		List<Freelancer> listaFree = jdbcTemplate.query(sql, 
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Freelancer>() {

					public Freelancer mapRow(ResultSet rs, int rowNum) throws SQLException {
						
						logger.debug("ID de USUARIO= " + rs.getInt("usu_id"));
						int idFreelancer = obtenerIdFreelancerConIdUsuario(rs.getInt("usu_id"));
						Freelancer freelancer = obtenerFreelancer(idFreelancer);
						return freelancer;				
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaFree);

		return respuesta;
	}



	

}
